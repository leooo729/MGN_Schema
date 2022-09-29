package com.example.MGN_Schema.controller.dto.request;

import com.example.MGN_Schema.controller.dto.response.CashiAccAmt;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepositMoneyRequest {
//        private String id;
//        private LocalDateTime time;
//        @NotEmpty
//        @Pattern(regexp = "^$|(1)",message = "請輸入代碼：1(入金)")
//        private String type;
        @NotEmpty
        private String cmNo;
        @NotEmpty
        @Pattern(regexp = "^$|[12]",message = "請輸入代碼：1 or 2")
        private String kacType;
        @NotEmpty
        private String bankNo;
        @NotEmpty
        @Pattern(regexp = "^$|(TWD|USD)",message = "請輸入 TWD or USD")
        private String ccy;
        @NotEmpty
        @Pattern(regexp = "^$|[12]",message = "請輸入代碼：1 or 2")
        private String pvType;
        private String bicaccNo;
        @NotEmpty
        private List<CashiAccAmt> accAmt;
        //        @NotEmpty
//        @Pattern(regexp = "^$|(1234)",message = "請輸入代碼：1~4")
        //private String iTYPE;
        //private String pReason;
        //private BigDecimal amt;
        @NotEmpty
        private String ctName;
        @NotEmpty
        private String ctTel;
        //private String status;
        //private String uTime;
}
