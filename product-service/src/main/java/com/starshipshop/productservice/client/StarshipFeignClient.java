package com.starshipshop.productservice.client;

import com.starshipshop.productservice.client.response.StarshipResponse;
import com.starshipshop.productservice.config.StarshipFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "starship-service", configuration = StarshipFeignConfiguration.class)
public interface StarshipFeignClient {

    @GetMapping("/api/v1/starships/{id}")
    StarshipResponse getStarshipById(@PathVariable Long id);

    @GetMapping("/api/v1/starships/in")
    CollectionModel<EntityModel<StarshipResponse>> getStarshipByIds(@RequestParam List<Long> ids);
}
