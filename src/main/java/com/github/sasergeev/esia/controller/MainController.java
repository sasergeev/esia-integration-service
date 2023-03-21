package com.github.sasergeev.esia.controller;

import com.github.sasergeev.esia.model.EsiaDataEntity;
import com.github.sasergeev.esia.service.EsiaAuthService;
import com.github.sasergeev.esia.service.EsiaDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("esia")
@AllArgsConstructor
public class MainController {

    private final EsiaAuthService esiaAuthService;
    private final EsiaDataService esiaDataService;

    @Operation(summary = "Редирект на страницу авторизации на портале Госуслуги")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "URL для авторизации")
    })
    @GetMapping("login")
    public ResponseEntity authUrl() {
        return esiaAuthService.urlForAuth();
    }

    @Operation(summary = "Редирект на ENDPOINT после аутентификации пользователя на портале Госуслуги для обмена кода авторизации на маркер доступа")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Перехват кода авторизации"),
            @ApiResponse(responseCode = "400", description = "Ошибка при обмене кода авторизации на меркер доступа")
    })
    @GetMapping(value = "auth", params = {"code", "state"})
    public Mono<Void> authCode(ServerHttpResponse response, @RequestParam("code") String code, @RequestParam("state") String state) {
        return esiaAuthService.exchangeCodeToToken(response, code, state);
    }

    @Operation(summary = "Редирект на ENDPOINT после обмена кода авторизации на токен для доступа к ESIA API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Результат вызова ESIA API",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EsiaDataEntity.class))
            }),
            @ApiResponse(responseCode = "500", description = "Ошибка при обращении к ESIA API")
    })
    @GetMapping(value = "done", params = {"oid", "jwt"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<EsiaDataEntity> authDone(@RequestParam("jwt") String jwt, @RequestParam("oid") String oid) {
        return esiaDataService.returnEsiaData(jwt, oid);
    }

}
