package com.example.MGN_Schema.controller;

import com.example.MGN_Schema.controller.dto.request.*;
import com.example.MGN_Schema.controller.dto.response.DeleteResponse;
import com.example.MGN_Schema.controller.dto.response.MgniListResponse;
import com.example.MGN_Schema.controller.dto.response.StatusResponse;
import com.example.MGN_Schema.model.entity.Mgni;
import com.example.MGN_Schema.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/api/v1/mgni")
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;

    @GetMapping(produces = {"application/xml", "application/json"})
    public MgniListResponse getAllMgni() throws Exception {
        MgniListResponse response = transferService.getAllMgni();
        return response;
    }

    @PostMapping
    public Mgni createMgni(@Valid @RequestBody DepositRequest request) throws Exception {
        Mgni mgni = transferService.createMgni(request);
        return mgni;
    }

    @PutMapping("/{id}")
    public Mgni updateMgni(@PathVariable @NotEmpty @Pattern(regexp = "^$|(MGI[0-9]{17})", message = "ID格式請輸入：GMI + 17位數字") String id,
                           @Valid @RequestBody DepositRequest request) throws Exception {
        Mgni mgni = transferService.updateMgni(id, request);
        return mgni;
    }


    @PostMapping("/search/{page}")
    public List<Mgni> searchTargetMgni(@PathVariable @Pattern(regexp = "^$|[0-9]+", message = "請輸入數字") String page,
                                       @Valid @RequestBody SearchMgniRequest request) throws Exception {
        List<Mgni> mgniList = transferService.searchTargetMgni(request, page);
        return mgniList;
    }

    @PutMapping
    public Mgni updateCashi(@Valid @RequestBody UpdateCashiRequest request) throws Exception {
        Mgni mgni = transferService.updateCashi(request);
        return mgni;
    }

    @DeleteMapping("/cashi")
    public DeleteResponse deleteCashi(@Valid @RequestBody DeleteCashiRequest request) throws Exception {
        DeleteResponse response = transferService.deleteCashi(request);
        return response;
    }

    @DeleteMapping("/{id}")
    public StatusResponse deleteMgni(@PathVariable @NotEmpty @Pattern(regexp = "^$|(MGI[0-9]{17})", message = "ID格式請輸入：GMI + 17位數字") String id) throws Exception {
        StatusResponse response = transferService.deleteMgni(id);
        return response;
    }

}
