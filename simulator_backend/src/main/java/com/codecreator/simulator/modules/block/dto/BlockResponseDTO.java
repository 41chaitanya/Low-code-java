package com.codecreator.simulator.modules.block.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockResponseDTO {
    private String id;
    private String projectId;
    private String parentId;
    private String type;
    private String name;
    private Integer position;
    private String config;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
