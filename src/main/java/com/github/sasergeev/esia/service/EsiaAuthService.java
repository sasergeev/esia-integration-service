package com.github.sasergeev.esia.service;

import com.github.sasergeev.esia.pojo.EsiaToken;
import com.github.sasergeev.esia.util.PkcsUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import static com.github.sasergeev.esia.config.Constants.*;
import static com.github.sasergeev.esia.util.ServiceUtil.getOidFromToken;
import static com.github.sasergeev.esia.util.ServiceUtil.getParamsString;

@Service
@RequiredArgsConstructor
@Slf4j
public class EsiaAuthService {

    @Value("${esia.client-id}")
    private String CLIENT_ID;
    @Value("${esia.scope}")
    private String SCOPE;
    @Value("${esia.redirect-base-url}")
    private String REDIRECT_BASE_URL;
    @Value("${esia.redirect-uri-ac}")
    private String REDIRECT_URL_AC;
    @Value("${esia.redirect-uri-te}")
    private String REDIRECT_URL_TE;

    private final PkcsUtil pkcsUtil;
    private final WebClient webClient;


    /**
     * URL-для авторизации на портале Госуслуги
     * @return URL для кнопки Войти через ЕСИА
     */
    public ResponseEntity urlForAuth() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss Z");
        String timeStamp = dateFormat.format(new Date());
        String state = UUID.randomUUID().toString();
        Map<String, String> params = new LinkedHashMap<>();
        params.put("client_id", CLIENT_ID);
        params.put("client_secret", pkcsUtil.getUrlSafeSign(SCOPE + timeStamp + CLIENT_ID + state));
        params.put("redirect_uri", REDIRECT_BASE_URL + REDIRECT_URL_AC);
        params.put("scope", SCOPE);
        params.put("response_type", RESPONSE_TYPE);
        params.put("state", state);
        params.put("timestamp", timeStamp);
        params.put("access_type", ACCESS_TYPE);
        return ResponseEntity
                .status(HttpStatus.TEMPORARY_REDIRECT)
                .location(URI.create(ESIA_CODE_POINT + getParamsString(params)))
                .build();
    }

    /**
     * Редирект после авторизации пользователя на портале Госуслуги
     * @param code код авторизации
     * @param state UUID исходного запроса (не используется)
     * @return редирект на ENDPOINT для доступа к ESIA API
     */
    public Mono<Void> exchangeCodeToToken(ServerHttpResponse response, String code, String state) {
        return requestForToken(code).flatMap(esiaToken -> {
            if (esiaToken.getAccessToken() != null)
                return redirect(response, esiaToken.getAccessToken(), getOidFromToken(esiaToken.getAccessToken()));
            return Mono.empty();
        });
    }

    private Mono<Void> redirect(ServerHttpResponse response, String token, String oid) {
        Map<String, String> params = new HashMap<>();
        params.put("jwt", token);
        params.put("oid", oid);
        response.setStatusCode(HttpStatus.PERMANENT_REDIRECT);
        response.getHeaders().setLocation(URI.create(REDIRECT_BASE_URL + REDIRECT_URL_TE + getParamsString(params)));
        return response.setComplete();
    }

    /**
     * Запрос на получение маркера доступа
     * @param code код авторизации
     * @return JWT - маркер доступа
     */
    private Mono<EsiaToken> requestForToken(String code) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss Z");
        String timeStamp = dateFormat.format(new Date());
        String state = UUID.randomUUID().toString();
        MultiValueMap<String, String> bodyParam = new LinkedMultiValueMap<>();
        bodyParam.add("client_id", CLIENT_ID);
        bodyParam.add("code", code);
        bodyParam.add("grant_type", GRANT_TYPE);
        bodyParam.add("client_secret", pkcsUtil.getUrlSafeSign(SCOPE + timeStamp + CLIENT_ID + state));
        bodyParam.add("state", state);
        bodyParam.add("redirect_uri", REDIRECT_BASE_URL + REDIRECT_URL_TE);
        bodyParam.add("scope", SCOPE);
        bodyParam.add("timestamp", timeStamp);
        bodyParam.add("token_type", TOKEN_TYPE);

        return webClient
                .post()
                .uri(ESIA_TOKEN_POINT)
                .body(BodyInserters.fromFormData(bodyParam))
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> clientResponse.createException().flatMap(response -> {
                    log.error("ESIA-ERROR Response: {}", response.getResponseBodyAsString());
                    return Mono.empty();
                }))
                .bodyToMono(EsiaToken.class);
    }

}
