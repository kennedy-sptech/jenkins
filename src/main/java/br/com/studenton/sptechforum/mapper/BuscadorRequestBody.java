package br.com.studenton.sptechforum.mapper;

import javax.validation.constraints.Size;

public class BuscadorRequestBody {

    @Size(min = 4)
    String texto;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
