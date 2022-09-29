package com.example.MGN_Schema.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchMgniRequest {
    private String id;
    private String kacType;
    private String ccy;
}
