package com.codecreator.simulator.modules.block;

import com.codecreator.simulator.modules.block.dto.BlockCreateDTO;
import com.codecreator.simulator.modules.block.dto.BlockResponseDTO;
import com.codecreator.simulator.modules.block.dto.BlockTreeDTO;
import com.codecreator.simulator.modules.block.dto.BlockUpdateDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BlockServiceImpl implements BlockService {
    private final BlockRepository blockRepository;

    public BlockServiceImpl(BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }

    @Override
    @Transactional
    public BlockResponseDTO createBlock(BlockCreateDTO dto) {
        Block block = new Block();
        block.setId(UUID.randomUUID().toString());
        block.setProjectId(dto.getProjectId());
        block.setParentId(dto.getParentId());
        block.setType(dto.getType());
        block.setName(dto.getName());
        block.setPosition(dto.getPosition());
        block.setConfig(dto.getConfig());

        Block saved = blockRepository.save(block);
        return toResponseDTO(saved);
    }

    @Override
    @Transactional
    public BlockResponseDTO updateBlock(String id, BlockUpdateDTO dto) {
        Block block = blockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Block not found"));

        if (dto.getName() != null) {
            block.setName(dto.getName());
        }
        if (dto.getPosition() != null) {
            block.setPosition(dto.getPosition());
        }
        if (dto.getConfig() != null) {
            block.setConfig(dto.getConfig());
        }

        Block updated = blockRepository.save(block);
        return toResponseDTO(updated);
    }

    @Override
    @Transactional
    public void deleteBlock(String id) {
        deleteBlockRecursively(id);
    }

    private void deleteBlockRecursively(String id) {
        List<Block> children = blockRepository.findByParentIdOrderByPositionAsc(id);
        for (Block child : children) {
            deleteBlockRecursively(child.getId());
        }
        blockRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlockTreeDTO> getProjectBlockTree(String projectId) {
        List<Block> allBlocks = blockRepository.findByProjectId(projectId);

        Map<String, BlockTreeDTO> blockMap = allBlocks.stream()
                .collect(Collectors.toMap(
                        Block::getId,
                        this::toTreeDTO));

        List<BlockTreeDTO> rootBlocks = new ArrayList<>();

        for (Block block : allBlocks) {
            BlockTreeDTO treeDTO = blockMap.get(block.getId());

            if (block.getParentId() == null || block.getParentId().isEmpty()) {
                rootBlocks.add(treeDTO);
            } else {
                BlockTreeDTO parent = blockMap.get(block.getParentId());
                if (parent != null) {
                    parent.getChildren().add(treeDTO);
                }
            }
        }

        sortChildrenByPosition(rootBlocks);

        return rootBlocks;
    }

    private void sortChildrenByPosition(List<BlockTreeDTO> blocks) {
        blocks.sort(Comparator.comparing(BlockTreeDTO::getPosition,
                Comparator.nullsLast(Comparator.naturalOrder())));

        for (BlockTreeDTO block : blocks) {
            if (!block.getChildren().isEmpty()) {
                sortChildrenByPosition(block.getChildren());
            }
        }
    }

    private BlockResponseDTO toResponseDTO(Block block) {
        return new BlockResponseDTO(
                block.getId(),
                block.getProjectId(),
                block.getParentId(),
                block.getType(),
                block.getName(),
                block.getPosition(),
                block.getConfig(),
                block.getCreatedAt(),
                block.getUpdatedAt());
    }

    private BlockTreeDTO toTreeDTO(Block block) {
        BlockTreeDTO dto = new BlockTreeDTO();
        dto.setId(block.getId());
        dto.setType(block.getType());
        dto.setName(block.getName());
        dto.setConfig(block.getConfig());
        dto.setPosition(block.getPosition());
        dto.setChildren(new ArrayList<>());
        return dto;
    }
}
