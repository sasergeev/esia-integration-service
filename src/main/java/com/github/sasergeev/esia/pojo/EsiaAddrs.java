package com.github.sasergeev.esia.pojo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "stateFacts",
        "size",
        "eTag",
        "elements"
})
public class EsiaAddrs {

    @JsonProperty("stateFacts")
    private List<String> stateFacts = null;
    @JsonProperty("size")
    private Integer size;
    @JsonProperty("eTag")
    private String eTag;
    @JsonProperty("elements")
    private List<AddrsElement> elements = null;

    public Integer getSize() {
        return size;
    }

    public List<AddrsElement> getElements() {
        return elements;
    }
}
