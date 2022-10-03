package com.example.MGN_Schema.model.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MGNI")
@EntityListeners(AuditingEntityListener.class)
@XmlAccessorType(XmlAccessType.FIELD)
public class Mgni {
    @Id
    @Column(name = "MGNI_ID")
    private String id;

    @CreatedDate
    @XmlJavaTypeAdapter(DateAdapter.class)
    @Column(name = "MGNI_TIME")
    private LocalDateTime time;
    @Column(name = "MGNI_TYPE")
    private String type;
    @Column(name = "MGNI_CM_NO")
    private String cmNo;
    @Column(name = "MGNI_KAC_TYPE")
    private String kacType;
    @Column(name = "MGNI_BANK_NO")
    private String bankNo;
    @Column(name = "MGNI_CCY")
    private String ccy;
    @Column(name = "MGNI_PV_TYPE")
    private String pvType;
    @Column(name = "MGNI_BICACC_NO")
    private String bicaccNo;
    @Column(name = "MGNI_I_TYPE")
    private String iTYPE;
    @Column(name = "MGNI_P_REASON")
    private String pReason;
    @Column(name = "MGNI_AMT")
    private BigDecimal amt;
    @Column(name = "MGNI_CT_NAME")
    private String ctName;
    @Column(name = "MGNI_CT_TEL")
    private String ctTel;
    @Column(name = "MGNI_STATUS")
    private String status;
    @LastModifiedDate
    @XmlJavaTypeAdapter(DateAdapter.class)
    @Column(name = "MGNI_U_TIME")
    private LocalDateTime uTime;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "mgniId",fetch = FetchType.EAGER)
//    @JoinColumn(name = "CASHI_MGNI_ID")
    private List<Cashi> cashiList;

}
