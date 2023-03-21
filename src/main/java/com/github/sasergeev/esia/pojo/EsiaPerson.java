package com.github.sasergeev.esia.pojo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "stateFacts",
        "firstName",
        "lastName",
        "middleName",
        "birthPlace",
        "trusted",
        "citizenship",
        "snils",
        "inn",
        "updatedOn",
        "status",
        "verifying",
        "rIdDoc",
        "containsUpCfmCode",
        "eTag"
})
public class EsiaPerson {
    @JsonProperty("stateFacts")
    private List<String> stateFacts = null;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("middleName")
    private String middleName;
    @JsonProperty("birthDate")
    private String birthDate;
    @JsonProperty("birthPlace")
    private String birthPlace;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("trusted")
    private Boolean trusted;
    @JsonProperty("citizenship")
    private String citizenship;
    @JsonProperty("snils")
    private String snils;
    @JsonProperty("inn")
    private String inn;
    @JsonProperty("updatedOn")
    private Integer updatedOn;
    @JsonProperty("status")
    private String status;
    @JsonProperty("verifying")
    private Boolean verifying;
    @JsonProperty("rIdDoc")
    private Integer rIdDoc;
    @JsonProperty("containsUpCfmCode")
    private Boolean containsUpCfmCode;
    @JsonProperty("eTag")
    private String eTag;

    public List<String> getStateFacts() {
        return stateFacts;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public Boolean getTrusted() {
        return trusted;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public String getSnils() {
        return snils;
    }

    public String getInn() {
        return inn;
    }

    public String getStatus() {
        return status;
    }

    public Boolean getVerifying() {
        return verifying;
    }

    public Integer getrIdDoc() {
        return rIdDoc;
    }
}
