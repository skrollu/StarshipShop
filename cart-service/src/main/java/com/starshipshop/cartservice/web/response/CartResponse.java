package com.starshipshop.cartservice.web.response;

import com.starshipshop.cartservice.domain.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Data
@Builder
@AllArgsConstructor
public class CartResponse {
    private Long id;
    private String userId;
    private String state;
    private HashMap<String, ProductResponse> products;
}
