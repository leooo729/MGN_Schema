package com.example.MGN_Schema.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CashiRelationPK implements Serializable {
    private String mgniId;
    private String accNo;
    private String ccy;
}
