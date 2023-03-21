package com.github.sasergeev.esia.pojo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "stateFacts",
        "id",
        "type",
        "vrfStu",
        "value",
        "eTag"
})

public class CttsElement {
    @JsonProperty("stateFacts")
    private List<String> stateFacts = null;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("vrfStu")
    private String vrfStu;
    @JsonProperty("value")
    private String value;
    @JsonProperty("eTag")
    private String eTag;

    public String getType() {
        return type;
    }

    public String getVrfStu() {
        return vrfStu;
    }

    public String getValue() {
        return value;
    }
}
