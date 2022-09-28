package com.example.MGN_Schema.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CASHI")
@IdClass(CashiRelationPK.class)
public class Cashi {
    @Id
    @Column(name = "CASHI_MGNI_ID")
    private String mgniId;
    @Id
    @Column(name = "CASHI_ACC_NO")
    private String accNo;
    @Id
    @Column(name = "CASHI_CCY")
    private String ccy;
    @Column(name = "CASHI_AMT")
    private BigDecimal amt;
}
