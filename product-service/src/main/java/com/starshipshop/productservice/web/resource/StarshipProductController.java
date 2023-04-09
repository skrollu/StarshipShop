package com.starshipshop.productservice.web.resource;

import com.starshipshop.productservice.domain.service.ProductService;
import com.starshipshop.productservice.web.response.StarshipProductResponse;
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products/starship")
public class StarshipProductController {

    private final ProductService productService;

    @PreAuthorize("permitAll()")
    @GetMapping
    List<StarshipProductResponse> getAll(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "price") String sortBy) {
        return productService.getAllStarshipProduct(pageNo, pageSize, sortBy);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{skuCode}")
//    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    StarshipProductResponse getBySkuCode(@PathVariable String skuCode) {
        return productService.getStarshipProductBySkuCode(skuCode);
    }

//    String fallBackMethod(String skuCoden, RuntimeException ex) {
//        return "Oops something went wrong";
//    }
}
