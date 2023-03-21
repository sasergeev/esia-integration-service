package com.github.sasergeev.esia.model;

import com.github.sasergeev.esia.pojo.EsiaAddrs;
import com.github.sasergeev.esia.pojo.EsiaCtts;
import com.github.sasergeev.esia.pojo.EsiaDocs;
import com.github.sasergeev.esia.pojo.EsiaPerson;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class EsiaDataEntity {
    private EsiaPerson esiaPerson;
    private EsiaDocs esiaDocs;
    private EsiaAddrs esiaAddrs;
    private EsiaCtts esiaCtts;
}
