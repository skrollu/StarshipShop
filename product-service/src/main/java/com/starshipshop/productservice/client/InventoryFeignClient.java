package com.starshipshop.productservice.client;

import com.starshipshop.productservice.client.request.InventoryResponse;
import com.starshipshop.productservice.config.InventoryFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventory-service", configuration = InventoryFeignConfiguration.class)
public interface InventoryFeignClient {

    @GetMapping("/api/v1/inventory/{skuCode}")
    InventoryResponse isInStock(@PathVariable String skuCode);

}
