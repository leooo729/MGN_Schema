package com.example.MGN_Schema.service;

import com.example.MGN_Schema.controller.dto.request.CreateMgniRequest;
import com.example.MGN_Schema.controller.dto.request.UpdateMgniRequest;
import com.example.MGN_Schema.controller.dto.response.CashiAccAmt;
import com.example.MGN_Schema.controller.dto.response.MgniDetailResponse;
import com.example.MGN_Schema.controller.dto.response.StatusResponse;
import com.example.MGN_Schema.model.CashiRepository;
import com.example.MGN_Schema.model.MgniRepository;
import com.example.MGN_Schema.model.entity.Cashi;
import com.example.MGN_Schema.model.entity.Mgni;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferService {

    final private MgniRepository mgniRepository;
    final private CashiRepository cashiRepository;


    public MgniDetailResponse getAllMgni() {
MgniDetailResponse response=new MgniDetailResponse();
response.setMgniList(mgniRepository.findAll());
return response;
    }

    public StatusResponse createMgni(CreateMgniRequest request) {
        Mgni mgni = new Mgni();
        mgni.setId("MGI" + DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now()));
        //mgni.setTime(LocalDateTime.now());
        mgni.setType("1");
        mgni.setCmNo("001");
        mgni.setKacType("1");
        mgni.setBankNo(request.getBankNo());
        mgni.setCcy("TWD");
        mgni.setPvType("1");
        mgni.setBicaccNo("實體帳號");
        //mgni.setMgniITYPE();
//        mgni.setPReason();
        mgni.setAmt(request.getAmt());
        mgni.setCtName(request.getCtName());
        mgni.setCtTel(request.getCtTel());
        mgni.setStatus("1");
        //mgni.setUTime(LocalDateTime.now());
        mgniRepository.save(mgni);

        for(CashiAccAmt cashiAccAmt:request.getAccAmt()){
            Cashi cashi=new Cashi();
            cashi.setMgniId(mgni.getId());
            cashi.setAccNo(cashiAccAmt.getAcc());
            cashi.setCcy("TWD");
            cashi.setAmt(cashiAccAmt.getAmt());
            cashiRepository.save(cashi);
        }
        return new StatusResponse("儲存成功");
    }

    public StatusResponse updateMgni(String id, UpdateMgniRequest request) {
        Mgni mgni = mgniRepository.getMgniById(id);
        mgni.setStatus(request.getStatus());
        mgniRepository.save(mgni);
        return new StatusResponse("更新成功");
    }

//    private BigDecimal countTotalAmt(CashiAccAmt cashiAccAmt){
//
//    }

}
