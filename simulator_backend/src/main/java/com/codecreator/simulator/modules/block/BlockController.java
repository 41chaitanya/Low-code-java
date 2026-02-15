package com.codecreator.simulator.modules.block;

import com.codecreator.simulator.modules.block.dto.BlockCreateDTO;
import com.codecreator.simulator.modules.block.dto.BlockResponseDTO;
import com.codecreator.simulator.modules.block.dto.BlockTreeDTO;
import com.codecreator.simulator.modules.block.dto.BlockUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blocks")
public class BlockController {
    private final BlockService blockService;

    public BlockController(BlockService blockService) {
        this.blockService = blockService;
    }

    @PostMapping
    public ResponseEntity<BlockResponseDTO> createBlock(@Valid @RequestBody BlockCreateDTO dto) {
        BlockResponseDTO created = blockService.createBlock(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlockResponseDTO> updateBlock(
            @PathVariable String id,
            @Valid @RequestBody BlockUpdateDTO dto) {
        BlockResponseDTO updated = blockService.updateBlock(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlock(@PathVariable String id) {
        blockService.deleteBlock(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tree/{projectId}")
    public ResponseEntity<List<BlockTreeDTO>> getProjectBlockTree(@PathVariable String projectId) {
        List<BlockTreeDTO> tree = blockService.getProjectBlockTree(projectId);
        return ResponseEntity.ok(tree);
    }
}
