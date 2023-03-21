package com.github.sasergeev.esia.service;

import com.github.sasergeev.esia.pojo.EsiaAddrs;
import com.github.sasergeev.esia.pojo.EsiaCtts;
import com.github.sasergeev.esia.pojo.EsiaDocs;
import com.github.sasergeev.esia.pojo.EsiaPerson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import static com.github.sasergeev.esia.config.Constants.*;

@Service
@AllArgsConstructor
@Slf4j
public class EsiaDataServiceImpl implements EsiaDataService {

    private final WebClient webClient;

    /**
     * Запрос на получение основных данных о пользователе ЕСИА
     * @param accessToken маркер доступа
     * @param oid идентификатор пользователя
     */
    public Mono<EsiaPerson> getPersonData(String accessToken, String oid) {
        return requestToEsia(ESIA_PRNS_POINT, accessToken, oid, EsiaPerson.class);
    }

    /**
     * Запрос на получение адресов пользователя ЕСИА
     * @param accessToken маркер доступа
     * @param oid идентификатор пользователя
     */
    public Mono<EsiaAddrs> getAddrsData(String accessToken, String oid) {
        return requestToEsia(ESIA_ADDRS_INFO, accessToken, oid, EsiaAddrs.class);
    }

    /**
     * Запрос на получение документов пользователя ЕСИА
     * @param accessToken маркер доступа
     * @param oid идентификатор пользователя
     */
    public Mono<EsiaDocs> getDocsData(String accessToken, String oid) {
        return requestToEsia(ESIA_DOCS_INFO, accessToken, oid, EsiaDocs.class);
    }

    /**
     * Запрос на получение контактных данных пользователя ЕСИА
     * @param accessToken маркер доступа
     * @param oid идентификатор пользователя
     */
    public Mono<EsiaCtts> getCttsData(String accessToken, String oid) {
        return requestToEsia(ESIA_CTTS_INFO, accessToken, oid, EsiaCtts.class);
    }

    /**
     * GET-запрос для вызова ESIA API
     * @param uri endpoint метода
     * @param jwt маркер доступа
     * @param oid идентификатор клиента в системе ЕСИА
     * @param <T> тип объекта
     */
    private <T> Mono<T> requestToEsia(String uri, String jwt, String oid, Class<T> clazz) {
        return webClient
                .get()
                .uri(uri, oid)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .retrieve()
                .onStatus(status -> {
                   switch (status) {
                       case UNAUTHORIZED:
                           log.error("ESIA-ERROR Response: {}", String.format("%s Invalid access token for %s", status.value() + " " + status.getReasonPhrase(), clazz.getSimpleName()));
                           break;
                       case FORBIDDEN:
                           log.error("ESIA-ERROR Response: {}", String.format("%s Invalid scope for %s", status.value() + " " + status.getReasonPhrase(), clazz.getSimpleName()));
                   }
                   return true;
                }, response -> Mono.empty())
                .bodyToMono(clazz);
    }

}
