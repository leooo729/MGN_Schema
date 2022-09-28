package com.example.MGN_Schema.controller.dto.response;

import com.example.MGN_Schema.model.entity.Mgni;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "mgniList")
@XmlAccessorType(XmlAccessType.FIELD)
public class MgniDetailResponse {
    List<Mgni> mgniList;
}
