package com.example.MGN_Schema.controller.dto.request;

import com.example.MGN_Schema.controller.dto.response.CashiAccAmt;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateMgniRequest {
        private String id;
        private LocalDateTime time;
        private String type;
        private String cmNo;
        private String kacType;
        private String bankNo;
        private String ccy;
        private String pvType;
        private String bicaccNo;
        private String iTYPE;
        private String pReason;
        private BigDecimal amt;
        private String ctName;
        private String ctTel;
        private String status;
        private String uTime;
        private List<CashiAccAmt> accAmt;
}
