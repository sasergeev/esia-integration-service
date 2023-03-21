package com.github.sasergeev.esia.config;

public class Constants {
    static final String ESIA_SERV = "https://esia.gosuslugi.ru";
    public static final String ESIA_CODE_POINT = ESIA_SERV + "/aas/oauth2/ac?";
    public static final String ESIA_TOKEN_POINT = ESIA_SERV + "/aas/oauth2/te?";
    public static final String ESIA_PRNS_POINT = "/rs/prns/{oid}";
    public static final String ESIA_ADDRS_INFO = ESIA_PRNS_POINT + "/addrs?embed=(elements)";
    public static final String ESIA_DOCS_INFO = ESIA_PRNS_POINT + "/docs?embed=(elements)";
    public static final String ESIA_CTTS_INFO = ESIA_PRNS_POINT + "/ctts?embed=(elements)";
    public static final String ACCESS_TYPE = "online";
    public static final String RESPONSE_TYPE = "code";
    public static final String GRANT_TYPE = "authorization_code";
    public static final String TOKEN_TYPE = "Bearer";
    public static final String RESIDENCE = "PLV";
    public static final String REGISTRATION = "PRG";
    public static final String EMAIL = "EML";
    public static final String MOBILE = "MBT";
    public static final String PASSPORT = "RF_PASSPORT";
    public static final String CITIZENSHIP = "RUS";
}
