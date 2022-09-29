package com.example.MGN_Schema.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCashiRequest {
    private String mgniId;
    private String accNo;
    private String ccy;
    private BigDecimal amt;
}
