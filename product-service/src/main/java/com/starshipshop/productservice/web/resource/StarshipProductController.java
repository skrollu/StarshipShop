package com.starshipshop.productservice.web.resource;

import com.starshipshop.productservice.domain.service.ProductService;
import com.starshipshop.productservice.web.response.StarshipProductResponse;
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products/starship")
public class StarshipProductController {

    private final ProductService productService;

    @GetMapping("/{skuCode}")
//    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    StarshipProductResponse getBySkuCode(@PathVariable String skuCode) {
        return productService.getStarshipProduct(skuCode);
    }

//    String fallBackMethod(String skuCoden, RuntimeException ex) {
//        return "Oops something went wrong";
//    }
}
