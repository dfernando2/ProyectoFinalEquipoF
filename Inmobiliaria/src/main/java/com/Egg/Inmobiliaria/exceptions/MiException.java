package com.Egg.Inmobiliaria.exceptions;

import java.io.IOException;

public class MiException extends Exception {

    public MiException(String msg) {
        super(msg);
    }

    public MiException(String msg, IOException e) {
    }

}
