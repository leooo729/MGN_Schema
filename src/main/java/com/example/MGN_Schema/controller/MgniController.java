package com.example.MGN_Schema.controller;

import com.example.MGN_Schema.controller.dto.request.CreateMgniRequest;
import com.example.MGN_Schema.controller.dto.request.UpdateMgniRequest;
import com.example.MGN_Schema.controller.dto.response.MgniDetailResponse;
import com.example.MGN_Schema.controller.dto.response.StatusResponse;
import com.example.MGN_Schema.model.entity.Mgni;
import com.example.MGN_Schema.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/mgni")
@RequiredArgsConstructor
public class MgniController {
    private final TransferService transferService;

    @GetMapping(produces = {"application/xml"})
    public MgniDetailResponse getAllMgni(){
        MgniDetailResponse response= transferService.getAllMgni();
        return response;
    }
    @PostMapping
    public StatusResponse createMgni(@RequestBody CreateMgniRequest request){
        StatusResponse response = transferService.createMgni(request);
        return response;
    }
    @PutMapping("/{id}")
    public StatusResponse updateMgni(@PathVariable String id, @RequestBody UpdateMgniRequest request){
        StatusResponse response= transferService.updateMgni(id,request);
        return  response;
    }

}
