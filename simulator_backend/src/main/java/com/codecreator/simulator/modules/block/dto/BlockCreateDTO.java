package com.codecreator.simulator.modules.block.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockCreateDTO {
    @NotBlank
    private String projectId;

    private String parentId;

    @NotBlank
    private String type;

    private String name;

    private Integer position;

    private String config;
}
