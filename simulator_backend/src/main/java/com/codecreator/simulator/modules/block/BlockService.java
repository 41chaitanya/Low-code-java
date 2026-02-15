package com.codecreator.simulator.modules.block;

import com.codecreator.simulator.modules.block.dto.BlockCreateDTO;
import com.codecreator.simulator.modules.block.dto.BlockResponseDTO;
import com.codecreator.simulator.modules.block.dto.BlockTreeDTO;
import com.codecreator.simulator.modules.block.dto.BlockUpdateDTO;

import java.util.List;

public interface BlockService {
    BlockResponseDTO createBlock(BlockCreateDTO dto);

    BlockResponseDTO updateBlock(String id, BlockUpdateDTO dto);

    void deleteBlock(String id);

    List<BlockTreeDTO> getProjectBlockTree(String projectId);
}
