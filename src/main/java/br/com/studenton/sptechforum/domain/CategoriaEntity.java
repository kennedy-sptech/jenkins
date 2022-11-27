package br.com.studenton.sptechforum.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "categoria")
public class CategoriaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_categoria", nullable = false)
    private int idCategoria;


    @Size(min = 2, max = 45, message = "O campo categoria tem o limite de 45 caracteres." )
    @NotBlank
    @Basic
    @Column(name = "categoria")
    private String categoria;


    @OneToMany(mappedBy = "categoriaByFkCategoria")
    private Collection<PublicacaoEntity> publicacaosByIdCategoria;

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriaEntity that = (CategoriaEntity) o;
        return idCategoria == that.idCategoria && Objects.equals(categoria, that.categoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCategoria, categoria);
    }

    public Collection<PublicacaoEntity> getPublicacaosByIdCategoria() {
        return publicacaosByIdCategoria;
    }

    public void setPublicacaosByIdCategoria(Collection<PublicacaoEntity> publicacaosByIdCategoria) {
        this.publicacaosByIdCategoria = publicacaosByIdCategoria;
    }
}
