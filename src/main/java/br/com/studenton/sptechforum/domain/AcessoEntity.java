package br.com.studenton.sptechforum.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "acesso")
public class AcessoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_acesso", nullable = false)
    private int idAcesso;

    @Size(min = 7, max = 10, message = "O tipo de acesso deve ser 'veterano', 'calouro' ou 'moderador'." )
    @NotBlank
    @Basic
    @Column(name = "tipo_acesso")
    private String tipoAcesso;



    @OneToMany(mappedBy = "acessoByFkAcesso")
    private Collection<UsuarioEntity> usuariosByIdAcesso;

    public int getIdAcesso() {
        return idAcesso;
    }

    public void setIdAcesso(int idAcesso) {
        this.idAcesso = idAcesso;
    }

    public String getTipoAcesso() {
        return tipoAcesso;
    }

    public void setTipoAcesso(String tipoAcesso) {
        this.tipoAcesso = tipoAcesso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcessoEntity that = (AcessoEntity) o;
        return idAcesso == that.idAcesso && Objects.equals(tipoAcesso, that.tipoAcesso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAcesso, tipoAcesso);
    }

    public Collection<UsuarioEntity> getUsuariosByIdAcesso() {
        return usuariosByIdAcesso;
    }

    public void setUsuariosByIdAcesso(Collection<UsuarioEntity> usuariosByIdAcesso) {
        this.usuariosByIdAcesso = usuariosByIdAcesso;
    }
}
