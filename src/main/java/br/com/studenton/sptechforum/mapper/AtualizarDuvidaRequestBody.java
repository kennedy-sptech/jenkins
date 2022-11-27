package br.com.studenton.sptechforum.mapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AtualizarDuvidaRequestBody {

    private Integer idPublicacao;

    @NotBlank
    @Size(min = 5, max = 100, message = "O titulo da sua publicação deve ter entre 5 a 100 caracteres.")
    private String titulo;

    @NotBlank
    @Size(min = 10, max = 800, message = "A sua publicação deve conter entre 10 até no máximo 800 caracteres.")
    private String texto;
    private Integer fkCategoria;

    public AtualizarDuvidaRequestBody(Integer idPublicacao, String titulo, String texto, Integer fkCategoria) {
        this.idPublicacao = idPublicacao;
        this.titulo = titulo;
        this.texto = texto;
        this.fkCategoria = fkCategoria;
    }

    public AtualizarDuvidaRequestBody(){

    }

    public Integer getIdPublicacao() {
        return idPublicacao;
    }

    public void setIdPublicacao(Integer idPublicacao) {
        this.idPublicacao = idPublicacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Integer getFkCategoria() {
        return fkCategoria;
    }

    public void setFkCategoria(Integer fkCategoria) {
        this.fkCategoria = fkCategoria;
    }
}
