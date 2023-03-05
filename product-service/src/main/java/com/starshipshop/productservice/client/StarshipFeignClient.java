package com.starshipshop.productservice.client;

import com.starshipshop.productservice.client.response.StarshipResponse;
import com.starshipshop.productservice.config.StarshipFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "starship-service", configuration = StarshipFeignConfiguration.class)
public interface StarshipFeignClient {

    @GetMapping("/api/v1/starships/{id}")
    StarshipResponse getStarshipById(@PathVariable String id);
}
