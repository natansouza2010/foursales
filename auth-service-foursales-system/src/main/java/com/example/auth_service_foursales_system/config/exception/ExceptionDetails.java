package com.example.auth_service_foursales_system.config.exception;


import lombok.Data;

@Data
public class ExceptionDetails {

    private int status;
    private String message;
}