package com.juanjonavarro;

import lombok.Getter;

@Getter
public class Resultado {
    private final String texto;

    public Resultado(String texto) {
        this.texto = texto;
    }

}
