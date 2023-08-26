package com.starshipshop.productservice.client;

import com.starshipshop.productservice.client.response.InventoryResponse;
import com.starshipshop.productservice.config.InventoryFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "inventory-service", configuration = InventoryFeignConfiguration.class)
public interface    InventoryFeignClient {

    @GetMapping("/api/v1/inventory/{skuCode}")
    InventoryResponse isInStock(@PathVariable String skuCode);

    @GetMapping("/api/v1/inventory")
    List<InventoryResponse> isInStockIn(@RequestParam List<String> skuCodes);
}
