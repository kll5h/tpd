package com.tilepay.domain.entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Send extends Message {

    public static int messageId = 0;
}
