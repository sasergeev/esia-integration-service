package com.github.sasergeev.esia.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import static com.github.sasergeev.esia.config.Constants.ESIA_SERV;

@Configuration
@Slf4j
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(ESIA_SERV)
                .build();
    }

    private static ExchangeFilterFunction logErrorResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> clientResponse.createException().flatMap(response -> {
            log.error("ESIA-ERROR Response: {}", response.getResponseBodyAsString());
            return Mono.just(clientResponse);
        }));
    }
}
