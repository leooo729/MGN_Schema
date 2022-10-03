package com.example.MGN_Schema.service;

import com.example.MGN_Schema.controller.dto.request.DepositRequest;
import com.example.MGN_Schema.controller.dto.request.DeleteCashiRequest;
import com.example.MGN_Schema.controller.dto.request.SearchMgniRequest;
import com.example.MGN_Schema.controller.dto.request.UpdateCashiRequest;
import com.example.MGN_Schema.model.entity.CashiAccAmt;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TransferService {

    final private MgniRepository mgniRepository;
    final private CashiRepository cashiRepository;


    public MgniDetailResponse getAllMgni() throws Exception {
        checkMgniExist();
        MgniDetailResponse response = new MgniDetailResponse();
        response.setMgniList(mgniRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return response;
    }

    public Mgni createMgni(DepositRequest request) throws Exception {
        Mgni mgni = new Mgni();
        mgni.setId("MGI" + DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now()));

        if (mgniRepository.findMgniById(mgni.getId()) != null) {
            throw new Exception("此ID已存在");
        }
        mgni = setDepositInfo(mgni, request);

        mgniRepository.save(mgni);

        return mgni;
    }

    public Mgni updateCashi(UpdateCashiRequest request) throws Exception {
        checkCashiExist(request.getMgniId(), request.getAccNo(), request.getCcy());

        Cashi cashi = cashiRepository.findTargetCashi(request.getMgniId(), request.getAccNo(), request.getCcy());
        cashi.setAmt(request.getAmt());
        cashiRepository.save(cashi);

        Mgni mgni = mgniRepository.findMgniById(request.getMgniId());
        mgni.setAmt(countAmt(mgni.getCashiList()));
        mgniRepository.save(mgni);

        return mgni;
    }


    public Mgni updateMgni(String id, DepositRequest request) throws Exception {
        checkMgniExist(id);
        Mgni mgni = mgniRepository.findMgniById(id);
        mgni = setDepositInfo(mgni, request);
        mgniRepository.save(mgni);
        return mgni;
    }


    public List<Mgni> searchTargetMgni(SearchMgniRequest request, String page) throws Exception {
        Specification<Mgni> specification = new Specification<Mgni>() { //創建一匿名類Specification接口
            @Override // root from Mgni ,取得要篩選的列 // CriteriaBuilder 用來設置條件
            public Predicate toPredicate(Root<Mgni> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<String> id = root.get("id");
                Path<String> kacType = root.get("kacType");
                Path<String> ccy = root.get("ccy");

                List<Predicate> filteredList = new ArrayList<>(); //條件放進List

                if (request.getId() != null) {
                    filteredList.add(cb.equal(id, request.getId()));
                }
                if (request.getKacType() != null) {
                    filteredList.add(cb.equal(kacType, request.getKacType()));
                }
                if (request.getCcy() != null) {
                    filteredList.add(cb.equal(ccy, request.getCcy()));
                }
                Predicate resultList = cb.and(filteredList.toArray((new Predicate[filteredList.size()]))); //拼接條件

//                CriteriaBuilder.In<String> in = cb.in(ccy);
//                in.value("ccy");
                return resultList;
            }
        };
        Pageable pageable = PageRequest.of(Integer.parseInt(page) - 1, 2);
        Page<Mgni> mgniList = mgniRepository.findAll(specification, pageable);
        checkMgniExist();

        return mgniList.getContent();
    }

    public Mgni deleteCashi(DeleteCashiRequest request) throws Exception {
        checkCashiExist(request.getMgniId(), request.getAccNo(), request.getCcy());
        Cashi cashi = cashiRepository.findTargetCashi(request.getMgniId(), request.getAccNo(), request.getCcy());
        cashiRepository.delete(cashi);
        Mgni mgni = mgniRepository.findMgniById(request.getMgniId());
        mgni.setAmt(countAmt(mgni.getCashiList()));
        mgniRepository.save(mgni);
        return mgniRepository.findMgniById(request.getMgniId());
    }

    public StatusResponse deleteMgni(String id) throws Exception {
        checkMgniExist(id);
//        cashiRepository.deleteCashiById(id);
        mgniRepository.deleteById(id);
        return new StatusResponse("刪除成功");
    }

    //----------------------------------------------------------------Method
    private Mgni setDepositInfo(Mgni mgni, DepositRequest request) throws Exception {
        if (mgniRepository.findMgniById(mgni.getId()) != null) {
            cashiRepository.deleteCashiById(mgni.getId());
        }
        mgni.setType("1");
        mgni.setCmNo(request.getCmNo());
        mgni.setKacType(request.getKacType());
        mgni.setBankNo(request.getBankNo());
        mgni.setCcy(request.getCcy());
        mgni.setPvType(request.getPvType());
        mgni.setBicaccNo(request.getBicaccNo());
        //mgni.setMgniITYPE();
//        mgni.setPReason();
        mgni.setCtName(request.getCtName());
        mgni.setCtTel(request.getCtTel());
        mgni.setStatus("1");

        List<String> accList = request.getAccAmt().stream().map(m -> m.getAcc()).distinct().collect(Collectors.toList());

        if (request.getKacType().equals("2") && accList.size() > 1) {
            throw new Exception("存入保管專戶別為交割結算基金專戶時，只能存入一筆總金額");
        }
        BigDecimal totalAmt = new BigDecimal(0);

//        List<Cashi> a = new ArrayList<>();
        for (String targetAcc : accList) {
            BigDecimal amt = new BigDecimal(0);
            for (CashiAccAmt cashiAccAmt : request.getAccAmt()) {
                if (cashiAccAmt.getAcc().equals(targetAcc)) {
                    amt = amt.add(cashiAccAmt.getAmt());
                }
            }
            Cashi cashi = setCashiInfo(mgni, targetAcc, amt);
//            a.add(cashi);
            cashiRepository.save(cashi);
            totalAmt = totalAmt.add(amt);
        }
//        mgni.setCashiList(a);
        mgni.setAmt(totalAmt);

        return mgni;
    }

    private Cashi setCashiInfo(Mgni mgni, String acc, BigDecimal amt) {
        Cashi cashi = new Cashi();
        cashi.setMgniId(mgni.getId());
        cashi.setAccNo(acc);
        cashi.setCcy(mgni.getCcy());
        cashi.setAmt(amt);
        return cashi;
    }

    private BigDecimal countAmt(List<Cashi> cashiList) {
        BigDecimal amt = new BigDecimal(0);
        for (Cashi cashi : cashiList) {
            amt = amt.add(cashi.getAmt());
        }
        return amt;
    }

    //----------------------------------------------------------------Check

    private Boolean checkMgniExist() throws Exception {
        if (mgniRepository.findAll() == null) {
            throw new Exception("資料庫無符合資料");
        }
        return true;
    }

    private Boolean checkMgniExist(String id) throws Exception {
        if (mgniRepository.findMgniById(id) == null) {
            throw new Exception("此ID不存在");
        }
        return true;
    }

    private Boolean checkCashiExist(String id, String accNo, String ccy) throws Exception {
        if (cashiRepository.findTargetCashi(id, accNo, ccy) == null) {
            throw new Exception("資料庫無符合資料");
        }
        return true;
    }
}


//    public Cashi searchTargetCashi(CashiPKRequest request) {
//        Cashi cashi = cashiRepository.findTargetCashi(request.getMgniId(), request.getAccNo(), request.getCcy());
//        return cashi;
//    }