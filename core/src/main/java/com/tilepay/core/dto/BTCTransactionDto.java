package com.tilepay.core.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

public class BTCTransactionDto {

    @NotBlank
    private List<InputDto> inputs = new ArrayList<>();

    @NotBlank
    private List<OutputDto> outputs = new ArrayList<>();

    private transactionType type;

    private String hash;

    private Date updateTime;

    private Integer block;

    public String fee;

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getBlock() {
        return block;
    }

    public void setBlock(Integer block) {
        this.block = block;
    }

    public void addOutput(OutputDto output) {
        outputs.add(output);
    }

    public enum transactionType {
        RECEIVING, SENDING, ISSUING, FEE
    }

    public List<InputDto> getInputs() {
        return inputs;
    }

    public void setInputs(List<InputDto> inputs) {
        this.inputs = inputs;
    }

    public List<OutputDto> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<OutputDto> outputs) {
        this.outputs = outputs;
    }

    public transactionType getType() {
        return type;
    }

    public void setType(transactionType type) {
        this.type = type;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getMessage() {
        String message = "";
        switch (type) {
        case RECEIVING:
            message = "Received ";
            break;
        case SENDING:
            message = "Sent ";
            break;
        case ISSUING:
            message = "Issued ";
            break;
        case FEE:
            message = "Fee of ";
            break;
        default:
            break;
        }
        return message;
    }

    public void sortOutputs() {
        getOutputs().sort((o1, o2) -> {
            if (o1.getAsset() == null && o2.getAsset() == null)
                return 0;
            if (o1.getAsset() == null)
                return 1;
            if (o2.getAsset() == null)
                return -1;
            return o2.getAsset().getName().compareTo(o1.getAsset().getName());
        });
    }

}
