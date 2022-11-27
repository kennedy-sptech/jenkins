package br.com.studenton.sptechforum.mapper;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

public class ResponderPublicacaoResquestBody {

    @NotBlank
    @Size(min = 2, max = 800, message = "A sua resposta deve conter entre 10 até no máximo 800 caracteres.")
    @Basic
    @Column(name = "texto")
    private String texto;

    @NotNull
    private Integer fkUsuario;


    public ResponderPublicacaoResquestBody(String texto, Integer fkUsuario) {
        this.texto = texto;
        this.fkUsuario = fkUsuario;
    }

    public ResponderPublicacaoResquestBody() {
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Integer getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(Integer fkUsuario) {
        this.fkUsuario = fkUsuario;
    }

}
