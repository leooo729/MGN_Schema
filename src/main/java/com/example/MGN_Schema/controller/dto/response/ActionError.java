package com.example.MGN_Schema.controller.dto.response;


import lombok.Getter;

@Getter
public enum ActionError {
    //列舉實例先定義
    Query("查詢失敗"),
    CREATE("新增失敗"),
    Modify("異動失敗"),
    DELETE("刪除失敗"),
    VALIDATE("檢核失敗"),
    SYSTEM("系統錯誤");

    //宣告新屬性
    private final String msg;

    //建構子
    ActionError(String msg) {
        this.msg = msg;
    }


}
