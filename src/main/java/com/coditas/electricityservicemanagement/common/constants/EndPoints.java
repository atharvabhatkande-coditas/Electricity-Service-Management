package com.coditas.electricityservicemanagement.common.constants;


public final class EndPoints {
    private EndPoints(){

    }

    public static final String AUTH_TENANT="/tenant/auth/**";
    public static final String AUTH="/platform/auth/**";
    public static final String SWAGGER1="/swagger-ui/**";
    public static final String SWAGGER2="/v3/api-docs/**";
    public static final String SWAGGER3="/swagger-ui.html";


    public static final String TENANT="/platform/tenant/**";
    public static final String INVITATION="/platform/invite/**";


    public static final String STATE="/platform/state/**";
    public static final String STATE_STATE_HEAD="/platform/state";
    public static final String STATE_MANAGEMENT="/platform/state/page/**";


    public static final String DISTRICT="/platform/district/**";
    public static final String DISTRICT_MANAGEMENT="/platform/district/page/**";
    public static final String DISTRICT_STATE_HEAD="/platform/district/state/**";


    public static final String CITY="/platform/city/**";
    public static final String CITY_MANAGEMENT="/platform/city/page/**";
    public static final String CITY_STATE_HEAD="/platform/city/state/**";
    public static final String CITY_DISTRICT_HEAD="/platform/district/state/**";






}
