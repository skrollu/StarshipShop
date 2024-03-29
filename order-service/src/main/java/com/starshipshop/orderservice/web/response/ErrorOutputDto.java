package com.starshipshop.orderservice.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorOutputDto {

    private String title;
    private String code;
    private String message;

}
