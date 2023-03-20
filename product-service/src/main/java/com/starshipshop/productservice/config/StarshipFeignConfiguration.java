package com.starshipshop.productservice.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.starshipshop.productservice.client.StarshipFeignClient;
import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StarshipFeignConfiguration {

    @Value("${client.starship.username}")
    private String username;
    @Value("${client.starship.password}")
    private String password;
//    private final StarshipErrorDecoder errorDecoder;

    @Bean
    public StarshipFeignClient initFeignClient(final Decoder decoder, final Encoder encoder, final Client client, final Contract contract) {
        return Feign.builder()
                .client(client)
                .contract(contract)
                .decoder(decoder)
                .encoder(encoder)
//                .errorDecoder(errorDecoder)
                .requestInterceptor(new BasicAuthRequestInterceptor(username, password))
                .target(StarshipFeignClient.class, "starship-service");
    }
}