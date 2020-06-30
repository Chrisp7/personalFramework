package com.imooc.entity.dto;


import lombok.Data;

@Data
public class Result<T> {
    // the status code of this request, 200 means successful
    private int code;
    private String msg;
    private T data;
}
