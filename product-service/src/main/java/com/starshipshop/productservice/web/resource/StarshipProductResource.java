package com.starshipshop.productservice.web.resource;

import com.starshipshop.productservice.domain.model.StarshipProduct;
import com.starshipshop.productservice.domain.service.ProductService;
import com.starshipshop.productservice.web.mapper.StarshipProductDtoMapper;
import com.starshipshop.productservice.web.response.StarshipProductOutputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products/starship")
public class StarshipProductResource {

    private final ProductService productService;
    private final StarshipProductDtoMapper starshipProductDtoMapper;

    @PreAuthorize("permitAll()")
    @GetMapping
    ResponseEntity<Page<StarshipProductOutputDto>> getPage(@PageableDefault(size = 5, page = 0, direction = Sort.Direction.ASC, sort = "price") Pageable page) {
        Page<StarshipProduct> pageResult = productService.getStarshipProductFromPage(page);
        List<StarshipProductOutputDto> result = pageResult.stream().map(starshipProductDtoMapper::mapToStarshipProductResponse).collect(Collectors.toList());
        return ResponseEntity.ok(new PageImpl(result, page, result.size()));
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{skuCode}")
    ResponseEntity<StarshipProductOutputDto> getBySkuCode(@PathVariable String skuCode) {
        StarshipProduct starshipProduct = productService.getStarshipProductBySkuCode(skuCode);
        return ResponseEntity.ok(starshipProductDtoMapper.mapToStarshipProductResponse(starshipProduct));
    }
}
