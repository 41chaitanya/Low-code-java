package com.codecreator.simulator.modules.block;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockRepository extends JpaRepository<Block, String> {
    List<Block> findByProjectId(String projectId);

    List<Block> findByParentIdOrderByPositionAsc(String parentId);

    List<Block> findByProjectIdOrderByPositionAsc(String projectId);
}
