package com.luminicel.tenant.tenant.domain.exception;

public class TenantNotFoundException extends RuntimeException{
    public TenantNotFoundException(String message){
        super(message);
    }
}
