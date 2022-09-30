package com.example.MGN_Schema.controller;

import com.example.MGN_Schema.controller.dto.request.*;
import com.example.MGN_Schema.controller.dto.response.MgniDetailResponse;
import com.example.MGN_Schema.controller.dto.response.StatusResponse;
import com.example.MGN_Schema.model.entity.Mgni;
import com.example.MGN_Schema.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/mgni")
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;

    @GetMapping(produces = {"application/xml"})
    public MgniDetailResponse getAllMgni() {
        MgniDetailResponse response = transferService.getAllMgni();
        return response;
    }

    @PostMapping
    public StatusResponse createMgni(@Valid @RequestBody DepositRequest request) {
        StatusResponse response = transferService.createMgni(request);
        return response;
    }

    @PutMapping("/{id}")
    public StatusResponse updateMgni(@PathVariable @NotEmpty @Pattern(regexp = "^$|(MGI[0-9]{17})",message = "ID格式請輸入：GMI + 17位數字")String id,
                                     @Valid @RequestBody DepositRequest request) {
        StatusResponse response = transferService.updateMgni(id, request);
        return response;
    }


    @PostMapping("/search/{page}")
    public List<Mgni> searchTargetMgni(@PathVariable @Pattern(regexp = "^$|[0-9]+", message = "請輸入數字")String page,
                                       @Valid @RequestBody SearchMgniRequest request) {
        List<Mgni> mgniList = transferService.searchTargetMgni(request, page);
        return mgniList;
    }

    @PutMapping
    public Mgni updateCashi(@Valid @RequestBody UpdateCashiRequest request) {
        Mgni mgni = transferService.updateCashi(request);
        return mgni;
    }

    @DeleteMapping("/cashi")
    public Mgni deleteCashi(@Valid@RequestBody CashiPKRequest request) {
        Mgni mgni = transferService.deleteCashi(request);
        return mgni;
    }

    @DeleteMapping("/{id}")
    public StatusResponse deleteMgni( @PathVariable @NotEmpty @Pattern(regexp = "^$|(MGI[0-9]{17})",message = "ID格式請輸入：GMI + 17位數字") String id) {
        StatusResponse response = transferService.deleteMgni(id);
        return response;
    }

}

//    @PostMapping("/cashi/search")
//    public Cashi getTargetCashi(@RequestBody CashiPKRequest request) {
//        Cashi cashi = transferService.searchTargetCashi(request);
//        return cashi;
//    }