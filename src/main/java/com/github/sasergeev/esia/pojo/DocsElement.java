package com.github.sasergeev.esia.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "stateFacts",
        "id",
        "type",
        "vrfStu",
        "series",
        "number",
        "issueDate",
        "issueId",
        "issuedBy",
        "eTag"
})
public class DocsElement {
    @JsonProperty("stateFacts")
    private List<String> stateFacts = null;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("vrfStu")
    private String vrfStu;
    @JsonProperty("series")
    private String series;
    @JsonProperty("number")
    private String number;
    @JsonProperty("issueDate")
    private String issueDate;
    @JsonProperty("issueId")
    private String issueId;
    @JsonProperty("issuedBy")
    private String issuedBy;
    @JsonProperty("eTag")
    private String eTag;

    public String getType() {
        return type;
    }

    public String getVrfStu() {
        return vrfStu;
    }

    public String getSeries() {
        return series;
    }

    public String getNumber() {
        return number;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getIssueId() {
        return issueId;
    }

    public String getIssuedBy() {
        return issuedBy;
    }
}
