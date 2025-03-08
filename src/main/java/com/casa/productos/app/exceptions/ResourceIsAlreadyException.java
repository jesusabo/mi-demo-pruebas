package com.casa.productos.app.exceptions;

public class ResourceIsAlreadyException extends RuntimeException{

    public ResourceIsAlreadyException(String message) {
        super(String.format("Error %s", message));
    }

}
