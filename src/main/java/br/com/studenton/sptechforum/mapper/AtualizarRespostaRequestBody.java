package br.com.studenton.sptechforum.mapper;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AtualizarRespostaRequestBody {

    @NotNull
    @Size(min = 10, max = 800, message = "A sua resposta deve conter entre 10 até no máximo 800 caracteres.")
    private String texto;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
