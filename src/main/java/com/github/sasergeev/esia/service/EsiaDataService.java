package com.github.sasergeev.esia.service;

import com.github.sasergeev.esia.model.EsiaDataEntity;
import com.github.sasergeev.esia.pojo.EsiaAddrs;
import com.github.sasergeev.esia.pojo.EsiaCtts;
import com.github.sasergeev.esia.pojo.EsiaDocs;
import com.github.sasergeev.esia.pojo.EsiaPerson;
import reactor.core.publisher.Mono;

public interface EsiaDataService {

    Mono<EsiaPerson> getPersonData(String jwt, String oid);

    Mono<EsiaDocs> getDocsData(String jwt, String oid);

    Mono<EsiaAddrs> getAddrsData(String jwt, String oid);

    Mono<EsiaCtts> getCttsData(String jwt, String oid);

    default Mono<EsiaDataEntity> returnEsiaData(String jwt, String oid) {
        return Mono
                .zip(getPersonData(jwt, oid), getDocsData(jwt, oid), getAddrsData(jwt, oid), getCttsData(jwt, oid))
                .map(objects -> EsiaDataEntity.builder().esiaPerson(objects.getT1()).esiaDocs(objects.getT2()).esiaAddrs(objects.getT3()).esiaCtts(objects.getT4()).build());
    }
}
