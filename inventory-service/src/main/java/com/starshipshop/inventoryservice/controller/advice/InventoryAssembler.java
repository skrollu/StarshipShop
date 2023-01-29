package com.starshipshop.inventoryservice.controller.advice;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.starshipshop.inventoryservice.controller.InventoryController;
import com.starshipshop.inventoryservice.domain.InventoryResponse;
import com.starshipshop.inventoryservice.service.mapper.converter.IdToHashConverter;

import lombok.RequiredArgsConstructor;

// TODO
// @RequiredArgsConstructor
// @Component
// public class InventoryAssembler
// implements RepresentationModelAssembler<InventoryResponse,
// EntityModel<InventoryResponse>> {

// }