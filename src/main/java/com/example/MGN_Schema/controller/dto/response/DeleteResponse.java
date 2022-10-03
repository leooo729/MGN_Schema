package com.example.MGN_Schema.controller.dto.response;

import com.example.MGN_Schema.model.entity.Mgni;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class DeleteResponse {
    private String message;
    private Mgni mgni;
}
