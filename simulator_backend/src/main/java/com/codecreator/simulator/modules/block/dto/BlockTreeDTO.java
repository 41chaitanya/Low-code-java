package com.codecreator.simulator.modules.block.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockTreeDTO {
    private String id;
    private String type;
    private String name;
    private String config;
    private Integer position;
    private List<BlockTreeDTO> children = new ArrayList<>();
}
