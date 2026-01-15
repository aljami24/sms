package com.smha.sms.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseApi<T, W> {
    private W message;
    private T data;
    private Integer code;
    private String status;
}
