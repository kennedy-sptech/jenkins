package br.com.studenton.sptechforum.mapper;

import java.sql.Timestamp;

public class PublicacaoPostRequestBody {

    private String titulo;
    private String texto;
    private Integer fkCategoria;
    private Integer tipoPublicacao;
    private Integer fkUsuario;
    private Integer status;

    public PublicacaoPostRequestBody(String titulo, String texto, Integer fkCategoria, Integer tipoPublicacao,
                                     Integer fkUsuario, Integer status) {
        this.titulo = titulo;
        this.texto = texto;
        this.fkCategoria = fkCategoria;
        this.tipoPublicacao = tipoPublicacao;
        this.fkUsuario = fkUsuario;
        this.status = status;
    }

    public PublicacaoPostRequestBody() {

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

    public Integer getTipoPublicacao() {
        return tipoPublicacao;
    }

    public void setTipoPublicacao(Integer tipoPublicacao) {
        this.tipoPublicacao = tipoPublicacao;
    }

    public Integer getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(Integer fkUsuario) {
        this.fkUsuario = fkUsuario;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PublicacaoPostRequestBody that = (PublicacaoPostRequestBody) o;

        if (titulo != null ? !titulo.equals(that.titulo) : that.titulo != null) return false;
        if (texto != null ? !texto.equals(that.texto) : that.texto != null) return false;
        if (fkCategoria != null ? !fkCategoria.equals(that.fkCategoria) : that.fkCategoria != null) return false;
        if (tipoPublicacao != null ? !tipoPublicacao.equals(that.tipoPublicacao) : that.tipoPublicacao != null)
            return false;
        if (fkUsuario != null ? !fkUsuario.equals(that.fkUsuario) : that.fkUsuario != null) return false;
        return status != null ? status.equals(that.status) : that.status == null;
    }

    @Override
    public int hashCode() {
        int result = titulo != null ? titulo.hashCode() : 0;
        result = 31 * result + (texto != null ? texto.hashCode() : 0);
        result = 31 * result + (fkCategoria != null ? fkCategoria.hashCode() : 0);
        result = 31 * result + (tipoPublicacao != null ? tipoPublicacao.hashCode() : 0);
        result = 31 * result + (fkUsuario != null ? fkUsuario.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
