package com.github.sasergeev.esia.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.SneakyThrows;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class ServiceUtil {

    /**
     * Парсим JWT для получения идентификатора пользователя
     * @param accessToken токен полученный после обмена на код авторизации
     * @return OID пользователя
     */
    public static String getOidFromToken(String accessToken) {
        int i = accessToken.lastIndexOf(".");
        Claims jwtBody = Jwts.parser().setAllowedClockSkewSeconds(60).parseClaimsJwt(accessToken.substring(0, i + 1)).getBody();
        return String.valueOf(jwtBody.get("urn:esia:sbj_id"));
    }

    /**
     * Формируем URL из параметров
     * @param params параметры URL
     * @return URL
     */
    public static String getParamsString(Map<String, String> params) {
        return params.keySet().stream().map(key -> key + "=" + urlEncodeParam(params.get(key))).collect(Collectors.joining("&"));
    }

    @SneakyThrows
    private static String urlEncodeParam(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }
}
