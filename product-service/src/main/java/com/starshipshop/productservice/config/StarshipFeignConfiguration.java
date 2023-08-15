package com.starshipshop.productservice.config;

import com.starshipshop.productservice.client.StarshipFeignClient;
import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StarshipFeignConfiguration {

    @Bean
    public StarshipFeignClient initFeignClient(final Decoder decoder, final Encoder encoder, final Client client, final Contract contract) {
        return Feign.builder()
                .client(client)
                .contract(contract)
                .decoder(decoder)
                .encoder(encoder)
                .target(StarshipFeignClient.class, "starship-service");
    }
}