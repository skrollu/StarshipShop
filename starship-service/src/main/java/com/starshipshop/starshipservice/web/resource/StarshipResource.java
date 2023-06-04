package com.starshipshop.starshipservice.web.resource;

import com.starshipshop.starshipservice.service.StarshipService;
import com.starshipshop.starshipservice.web.mapper.StarshipDtoMapper;
import com.starshipshop.starshipservice.web.response.StarshipOutputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/starships")
public class StarshipResource {

    final StarshipDtoMapper starshipDtoMapper;
    final StarshipService starshipService;

    @PreAuthorize("permitAll()")
    @GetMapping("/in")
    CollectionModel<StarshipOutputDto> getStarshipByIds(@RequestParam List<Long> ids) {
        return CollectionModel.of(
                        starshipService.getStarshipByIdIn(ids)
                                .stream()
                                .map(starshipDtoMapper::mapToStarshipOutputDto)
                                .collect(Collectors.toList()));
    }
}
