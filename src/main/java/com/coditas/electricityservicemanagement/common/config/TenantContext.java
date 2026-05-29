package com.coditas.electricityservicemanagement.common.config;

public class TenantContext {

    private static final ThreadLocal<String> CURRENT_THREAD=new ThreadLocal<>();
    private static final ThreadLocal<String> CURRENT_SCHEMA=new ThreadLocal<>();

    public static void setCurrentTenant(String tenant){
        CURRENT_THREAD.set(tenant);
    }

    public static void setCurrentSchema(String schema){
        CURRENT_SCHEMA.set(schema);
    }
    public static String getCurrentTenant(){
        return CURRENT_THREAD.get();
    }

    public static String getCurrentSchema(){
        return CURRENT_SCHEMA.get();
    }

    public static void clear(){
        CURRENT_THREAD.remove();
        CURRENT_SCHEMA.remove();
    }

}