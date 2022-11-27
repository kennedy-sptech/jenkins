package br.com.studenton.sptechforum.mapper;

import br.com.studenton.sptechforum.domain.UsuarioEntity;

public class UsuarioFilaRespostaBody {

    private UsuarioEntity usuario;
    private String situacao;

    public UsuarioFilaRespostaBody(UsuarioEntity usuario, String situacao) {
        this.usuario = usuario;
        this.situacao = situacao;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
