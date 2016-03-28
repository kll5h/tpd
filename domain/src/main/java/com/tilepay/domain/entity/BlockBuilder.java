package com.tilepay.domain.entity;

public class BlockBuilder implements Builder<Block> {

    private Integer index;
    private String hash;
    private Long time;

    public BlockBuilder setIndex(Integer index) {
        this.index = index;
        return this;
    }

    public BlockBuilder setHash(String hash) {
        this.hash = hash;
        return this;
    }

    public BlockBuilder setTime(Long time) {
        this.time = time;
        return this;
    }

    public static BlockBuilder aBlock() {
        return new BlockBuilder();
    }

    @Override
    public Block build() {
        Block object = new Block();
        object.setIndex(index);
        object.setHash(hash);
        object.setTime(time);
        return object;
    }
}
