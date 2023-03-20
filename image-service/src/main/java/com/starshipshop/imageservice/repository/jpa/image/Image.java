package com.starshipshop.imageservice.repository.jpa.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_image", updatable = false, columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "uri", unique = true, updatable = false)
    private String uri;

    @Column(name = "filename", unique = true, updatable = false)
    private String filename;

    @Column(name = "extension", updatable = false)
    private String extension;

}