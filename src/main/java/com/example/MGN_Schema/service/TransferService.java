package com.example.MGN_Schema.service;

import com.example.MGN_Schema.controller.dto.request.DepositMoneyRequest;
import com.example.MGN_Schema.controller.dto.request.SearchCashiRequest;
import com.example.MGN_Schema.controller.dto.request.SearchMgniRequest;
import com.example.MGN_Schema.controller.dto.request.UpdateCashiRequest;
import com.example.MGN_Schema.controller.dto.response.CashiAccAmt;
import com.example.MGN_Schema.controller.dto.response.MgniDetailResponse;
import com.example.MGN_Schema.controller.dto.response.StatusResponse;
import com.example.MGN_Schema.model.CashiRepository;
import com.example.MGN_Schema.model.MgniRepository;
import com.example.MGN_Schema.model.entity.Cashi;
import com.example.MGN_Schema.model.entity.Mgni;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TransferService {

    final private MgniRepository mgniRepository;
    final private CashiRepository cashiRepository;


    public MgniDetailResponse getAllMgni() {
        MgniDetailResponse response = new MgniDetailResponse();
        response.setMgniList(mgniRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return response;
    }

    public StatusResponse createMgni(DepositMoneyRequest request) {
        Mgni mgni = new Mgni();
        mgni.setId("MGI" + DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now()));
        mgni = setDepositmoneyInfo(mgni, request);

        for (CashiAccAmt cashiAccAmt : request.getAccAmt()) {
            Cashi cashi = setCashiInfo(mgni, cashiAccAmt);
            cashiRepository.save(cashi);

            mgni.setAmt(mgni.getAmt() == null ? cashiAccAmt.getAmt() : mgni.getAmt().add(cashiAccAmt.getAmt()));
        }
        mgniRepository.save(mgni);

        return new StatusResponse("儲存成功");
    }

    public Mgni updateCashi(UpdateCashiRequest request) {
        Cashi cashi = cashiRepository.findTargetCashi(request.getMgniId(), request.getAccNo(), request.getCcy());
        cashi.setAmt(request.getAmt());
        cashiRepository.save(cashi);

        Mgni mgni = mgniRepository.findMgniById(request.getMgniId());
//        List<Cashi> cashiList = cashiRepository.findCashiListById(request.getMgniId());
//        mgni.setCashiList(cashiList);
        mgni.setAmt(countAmt(mgni.getCashiList()));
        mgniRepository.save(mgni);

        return mgni;
    }

    private BigDecimal countAmt(List<Cashi> cashiList) {
        BigDecimal amt = new BigDecimal(0);
        for (Cashi cashi : cashiList) {
            amt = amt.add(cashi.getAmt());
        }
        return amt;
    }

    public StatusResponse updateMgni(String id, DepositMoneyRequest request) {
        Mgni mgni = mgniRepository.findMgniById(id);
        mgni = setDepositmoneyInfo(mgni, request);

        for (CashiAccAmt cashiAccAmt : request.getAccAmt()) {
            Cashi cashi = setCashiInfo(mgni, cashiAccAmt);
            cashiRepository.save(cashi);

            mgni.setAmt(mgni.getAmt() == null ? cashiAccAmt.getAmt() : mgni.getAmt().add(cashiAccAmt.getAmt()));
        }
        mgniRepository.save(mgni);
        return new StatusResponse("更新成功");
    }

    public Cashi searchTargetCashi(SearchCashiRequest request) {
        Cashi cashi = cashiRepository.findTargetCashi(request.getMgniId(), request.getAccNo(), request.getCcy());
        return cashi;
    }

    public List<Mgni> searchTargetMgni(SearchMgniRequest request,int page) {
        Specification<Mgni> specification = new Specification<Mgni>() {
            @Override
            public Predicate toPredicate(Root<Mgni> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<Object> id = root.get("id");
                Path<Object> kacType = root.get("kacType");
                Path<Object> ccy = root.get("ccy");

                List<Predicate> filteredList = new ArrayList<>();

                if (request.getId() != null) {
                    filteredList.add(cb.equal(id, request.getId()));
                }
                if (request.getKacType() != null) {
                    filteredList.add(cb.equal(kacType, request.getKacType()));
                }
                if (request.getCcy() != null) {
                    filteredList.add(cb.equal(ccy, request.getCcy()));
                }
                Predicate resultList = cb.and(filteredList.toArray((new Predicate[filteredList.size()])));

                return resultList;
            }
        };
        Pageable pageable=PageRequest.of(page-1,2);
        Page<Mgni> mgniList = mgniRepository.findAll(specification,pageable);
        return mgniList.getContent();
    }


//    private BigDecimal countTotalAmt(CashiAccAmt cashiAccAmt){
//
//    }

    //----------------------------------------------------------------Method
    private Mgni setDepositmoneyInfo(Mgni mgni, DepositMoneyRequest request) {
        mgni.setType("1");
        mgni.setCmNo(request.getCmNo());
        mgni.setKacType(request.getKacType());
        mgni.setBankNo(request.getBankNo());
        mgni.setCcy(request.getCcy());
        mgni.setPvType(request.getPvType());
        mgni.setBicaccNo(request.getBicaccNo());
        //mgni.setMgniITYPE();
//        mgni.setPReason();
//        mgni.setAmt(request.getAmt());
        mgni.setCtName(request.getCtName());
        mgni.setCtTel(request.getCtTel());
        mgni.setStatus("1");
        //mgni.setUTime(LocalDateTime.now());

        return mgni;
    }

    private Cashi setCashiInfo(Mgni mgni, CashiAccAmt cashiAccAmt) {
        Cashi cashi = new Cashi();
        cashi.setMgniId(mgni.getId());
        cashi.setAccNo(cashiAccAmt.getAcc());
        cashi.setCcy(mgni.getCcy());
        cashi.setAmt(cashiAccAmt.getAmt());
        return cashi;
    }

}
