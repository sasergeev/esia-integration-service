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
        "addressStr",
        "flat",
        "countryId",
        "house",
        "zipCode",
        "city",
        "street",
        "region",
        "eTag"
})
public class AddrsElement {

    @JsonProperty("stateFacts")
    private List<String> stateFacts = null;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("addressStr")
    private String addressStr;
    @JsonProperty("flat")
    private String flat;
    @JsonProperty("countryId")
    private String countryId;
    @JsonProperty("house")
    private String house;
    @JsonProperty("zipCode")
    private String zipCode;
    @JsonProperty("city")
    private String city;
    @JsonProperty("street")
    private String street;
    @JsonProperty("region")
    private String region;
    @JsonProperty("eTag")
    private String eTag;

    public String getType() {
        return type;
    }

    public String getAddressStr() {
        return addressStr;
    }

    public String getFlat() {
        return flat;
    }

    public String getCountryId() {
        return countryId;
    }

    public String getHouse() {
        return house;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getRegion() {
        return region;
    }
}
