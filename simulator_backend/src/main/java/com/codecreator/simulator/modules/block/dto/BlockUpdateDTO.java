package com.codecreator.simulator.modules.block.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockUpdateDTO {
    private String name;
    private Integer position;
    private String config;
}
