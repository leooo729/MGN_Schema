package com.example.MGN_Schema.controller.dto.response;

import com.example.MGN_Schema.model.entity.DateAdapter;
import com.example.MGN_Schema.model.entity.Mgni;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class MgniDetailResponse {
    List<Mgni> mgniList;
}
