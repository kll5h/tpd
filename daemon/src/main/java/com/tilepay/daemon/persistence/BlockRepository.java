package com.tilepay.daemon.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tilepay.domain.entity.Block;

public interface BlockRepository extends JpaRepository<Block, Long> {

    Block findTopByOrderByIndexDesc();

    //TODO: 27.11.2014 Andrei Sljusar: if (!rs.next()) {
    //   db.executeUpdate("INSERT INTO blocks(block_index,block_hash,block_time) VALUES('" + blockHeight.toString() + "','" + block.getHashAsString() + "','" + block.getTimeSeconds() + "')");
    //}
    Block findByHash(String hash);

    //@Query("from Block b where b.blockPk.index >= :index order by b.blockPk.index asc")
    List<Block> findByIndexGreaterThanEqualOrderByIndexAsc(/*@Param("index")*/ Integer index);
}
