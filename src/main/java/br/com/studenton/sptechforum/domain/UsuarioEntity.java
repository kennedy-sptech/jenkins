package br.com.studenton.sptechforum.domain;

import br.com.studenton.sptechforum.observer.EmailSender;
import br.com.studenton.sptechforum.observer.StatusChangeObserver;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "Usuario")
public class UsuarioEntity implements StatusChangeObserver {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_usuario", nullable = false)
    private int idUsuario;

    @Size(min = 7, max = 8)
    @NotBlank
    @Basic
    @Column(name = "ra")
    private String ra;

    @NotBlank
    @Basic
    @Column(name = "nome", length = 60)
    private String nome;

    @NotBlank
    @Email
    @Basic
    @Column(name = "email", length = 55)
    private String email;

    @Size (min = 8, max = 20, message = "A sua senha deve conter entre 8 a 20 caracteres.")
    @NotBlank
    @Basic
    @Column(name = "senha")
    private String senha;

    @Size (min = 3, max = 45, message = "A demilitação do campo curso é de 3 até 45 caracteres.")
    @NotBlank
    @Basic
    @Column(name = "curso")
    private String curso;



    @NotNull
    @Basic
    @Column(name = "semestre")
    private Integer semestre;

    @Basic
    @Column(name = "foto_perfil",length = 100)
    private String fotoPerfil;

    @Basic
    @Column(name = "fk_acesso", nullable = true)
    private Integer fkAcesso;

    @Basic
    @Column(name = "check_email", nullable = true)
    private Boolean checkEmail;

    @Basic
    @Column(name = "autenticado", nullable = true)
    private Boolean autenticado ;

    @OneToMany(mappedBy = "usuarioByFkUsuario")
    private Collection<CurtidaEntity> curtidasByIdUsuario;

    @OneToMany(mappedBy = "usuarioByFkUsuario")
    private Collection<FavoritoEntity> favoritosByIdUsuario;

    @OneToMany(mappedBy = "usuarioByFkUsuario")
    private Collection<PublicacaoEntity> publicacaosByIdUsuario;

    @OneToMany(mappedBy = "usuarioByFkUsuario")
    private Collection<RespostaEntity> respostasByIdUsuario;

    @ManyToOne
    @JoinColumn(name = "fk_acesso", referencedColumnName = "id_acesso", insertable = false, updatable = false)
    private AcessoEntity acessoByFkAcesso;



    public UsuarioEntity(String ra, String nome, String email, String senha, String curso, Integer semestre, Integer fkAcesso) {
        this.ra = ra;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.curso = curso;
        this.semestre = semestre;
        this.fkAcesso = fkAcesso;
    }

    public UsuarioEntity() {

    }
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String pegarSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public Integer getFkAcesso() {
        return fkAcesso;
    }

    public void setFkAcesso(Integer fkAcesso) {
        this.fkAcesso = fkAcesso;
    }

    public Boolean getCheckEmail() {
        return checkEmail;
    }

    public void setCheckEmail(Boolean checkEmail) {
        this.checkEmail = checkEmail;
    }

    public Boolean getAutenticado(){
        return autenticado == null ? false : autenticado;
    }

    public void setAutenticado(boolean autenticado) {
        this.autenticado = autenticado;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioEntity that = (UsuarioEntity) o;
        return idUsuario == that.idUsuario && Objects.equals(ra, that.ra) && Objects.equals(nome, that.nome) && Objects.equals(email, that.email) && Objects.equals(senha, that.senha) && Objects.equals(curso, that.curso) && Objects.equals(semestre, that.semestre) && Objects.equals(fotoPerfil, that.fotoPerfil) && Objects.equals(fkAcesso, that.fkAcesso) && Objects.equals(checkEmail, that.checkEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, ra, nome, email, senha, curso, semestre, fotoPerfil, fkAcesso, checkEmail);
    }

    @Override
    public void statusChange(Integer status, PublicacaoEntity publicacao) {
        new EmailSender(email, status, publicacao).run();
    }

//    public Collection<CurtidaEntity> getCurtidasByIdUsuario() {
//        return curtidasByIdUsuario;
//    }
//
//    public void setCurtidasByIdUsuario(Collection<CurtidaEntity> curtidasByIdUsuario) {
//        this.curtidasByIdUsuario = curtidasByIdUsuario;
//    }
//
//    public Collection<FavoritoEntity> getFavoritosByIdUsuario() {
//        return favoritosByIdUsuario;
//    }
//
//    public void setFavoritosByIdUsuario(Collection<FavoritoEntity> favoritosByIdUsuario) {
//        this.favoritosByIdUsuario = favoritosByIdUsuario;
//    }
//
//    public Collection<PublicacaoEntity> getPublicacaosByIdUsuario() {
//        return publicacaosByIdUsuario;
//    }
//
//    public void setPublicacaosByIdUsuario(Collection<PublicacaoEntity> publicacaosByIdUsuario) {
//        this.publicacaosByIdUsuario = publicacaosByIdUsuario;
//    }
//
//    public Collection<RespostaEntity> getRespostasByIdUsuario() {
//        return respostasByIdUsuario;
//    }
//
//    public void setRespostasByIdUsuario(Collection<RespostaEntity> respostasByIdUsuario) {
//        this.respostasByIdUsuario = respostasByIdUsuario;
//    }
//
//    public AcessoEntity getAcessoByFkAcesso() {
//        return acessoByFkAcesso;
//    }
//
//    public void setAcessoByFkAcesso(AcessoEntity acessoByFkAcesso) {
//        this.acessoByFkAcesso = acessoByFkAcesso;
//    }
}
