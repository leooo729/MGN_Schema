package com.example.MGN_Schema.controller;

import com.example.MGN_Schema.controller.dto.request.*;
import com.example.MGN_Schema.controller.dto.response.MgniDetailResponse;
import com.example.MGN_Schema.controller.dto.response.StatusResponse;
import com.example.MGN_Schema.model.entity.Cashi;
import com.example.MGN_Schema.model.entity.Mgni;
import com.example.MGN_Schema.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/mgni")
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;

    @GetMapping(produces = {"application/xml"})
    public MgniDetailResponse getAllMgni(){
        MgniDetailResponse response= transferService.getAllMgni();
        return response;
    }
    @PostMapping
    public StatusResponse createMgni(@Valid @RequestBody DepositMoneyRequest request){
        StatusResponse response = transferService.createMgni(request);
        return response;
    }
    @PutMapping("/{id}")
    public StatusResponse updateMgni(@NotEmpty @PathVariable String id, @Valid@RequestBody DepositMoneyRequest request){
        StatusResponse response= transferService.updateMgni(id,request);
        return  response;
    }
    @PostMapping("/cashi/search")
    public Cashi getTargetCashi(@RequestBody SearchCashiRequest request){
        Cashi cashi=transferService.searchTargetCashi(request);
        return cashi;
    }
    @PostMapping("/search/{page}")
    public List<Mgni> searchTargetMgni(@PathVariable int page,@RequestBody SearchMgniRequest request){
        List<Mgni> mgniList=transferService.searchTargetMgni(request,page);
        return mgniList;
    }

    @PutMapping
    public Mgni updateCashi(@RequestBody UpdateCashiRequest request){
        Mgni mgni=transferService.updateCashi(request);
        return mgni;
    }

}
