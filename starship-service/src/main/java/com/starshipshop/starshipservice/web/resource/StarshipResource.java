package com.starshipshop.starshipservice.web.resource;

import com.starshipshop.starshipservice.domain.Starship;
import com.starshipshop.starshipservice.service.StarshipService;
import com.starshipshop.starshipservice.web.mapper.StarshipDtoMapper;
import com.starshipshop.starshipservice.web.response.StarshipOutputDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.NonComposite;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/starships")
public class StarshipResource {

    final StarshipDtoMapper starshipDtoMapper;
    final StarshipService starshipService;

    @PreAuthorize("permitAll()")
    @GetMapping("/in")
    CollectionModel<StarshipOutputDto> getStarshipByIds(@NonComposite @RequestParam List<Long> ids) {
        CollectionModel<StarshipOutputDto> result = CollectionModel.of(
                        starshipService.getStarshipByIdIn(ids)
                                .stream()
                                .map(starshipDtoMapper::mapToStarshipOutputDto)
                                .collect(Collectors.toList()));
        result.add(linkTo(methodOn(StarshipResource.class).getStarshipByIds(ids)).withSelfRel());
        return result;
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    EntityModel<StarshipOutputDto> getStarshipByIds(@PathVariable Long id) {
        Starship starship =
                starshipService.getStarshipById(id);
        EntityModel<StarshipOutputDto> result = EntityModel.of(starshipDtoMapper.mapToStarshipOutputDto(starship));
        result.add(linkTo(methodOn(StarshipResource.class).getStarshipByIds(id)).withSelfRel());
        return result;
    }
}
