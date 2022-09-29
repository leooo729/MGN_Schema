package com.example.MGN_Schema.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchCashiRequest {
    private String mgniId;
    private String accNo;
    private String ccy;
}
