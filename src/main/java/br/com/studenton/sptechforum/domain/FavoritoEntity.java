package br.com.studenton.sptechforum.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "favorito")
public class FavoritoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_favorito", nullable = false)
    private int idFavorito;

    @Basic
    @Column(name = "fk_usuario", nullable = true)
    private Integer fkUsuario;

    @Basic
    @Column(name = "fk_publicacao", nullable = true)
    private Integer fkPublicacao;

    @Basic
    @Column(name = "fk_resposta", nullable = true)
    private Integer fkResposta;

    @ManyToOne
    @JoinColumn(name = "fk_usuario", referencedColumnName = "id_usuario", insertable = false, updatable = false)
    private UsuarioEntity usuarioByFkUsuario;

    @ManyToOne
    @JoinColumn(name = "fk_publicacao", referencedColumnName = "id_publicacao", insertable = false, updatable = false)
    private PublicacaoEntity publicacaoByFkPublicacao;

    @ManyToOne
    @JoinColumn(name = "fk_resposta", referencedColumnName = "id_resposta", insertable = false, updatable = false)
    private RespostaEntity respostaByFkResposta;

    public int getIdFavorito() {
        return idFavorito;
    }

    public void setIdFavorito(int idFavorito) {
        this.idFavorito = idFavorito;
    }

    public Integer getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(Integer fkUsuario) {
        this.fkUsuario = fkUsuario;
    }

    public Integer getFkPublicacao() {
        return fkPublicacao;
    }

    public void setFkPublicacao(Integer fkPublicacao) {
        this.fkPublicacao = fkPublicacao;
    }

    public Integer getFkResposta() {
        return fkResposta;
    }

    public void setFkResposta(Integer fkResposta) {
        this.fkResposta = fkResposta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoritoEntity that = (FavoritoEntity) o;
        return idFavorito == that.idFavorito && Objects.equals(fkUsuario, that.fkUsuario) && Objects.equals(fkPublicacao, that.fkPublicacao) && Objects.equals(fkResposta, that.fkResposta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFavorito, fkUsuario, fkPublicacao, fkResposta);
    }

    public UsuarioEntity getUsuarioByFkUsuario() {
        return usuarioByFkUsuario;
    }

    public void setUsuarioByFkUsuario(UsuarioEntity usuarioByFkUsuario) {
        this.usuarioByFkUsuario = usuarioByFkUsuario;
    }

    public PublicacaoEntity getPublicacaoByFkPublicacao() {
        return publicacaoByFkPublicacao;
    }

    public void setPublicacaoByFkPublicacao(PublicacaoEntity publicacaoByFkPublicacao) {
        this.publicacaoByFkPublicacao = publicacaoByFkPublicacao;
    }

    public RespostaEntity getRespostaByFkResposta() {
        return respostaByFkResposta;
    }

    public void setRespostaByFkResposta(RespostaEntity respostaByFkResposta) {
        this.respostaByFkResposta = respostaByFkResposta;
    }
}
