package br.com.studenton.sptechforum.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "resposta")
public class RespostaEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_resposta", nullable = false)
    private int idResposta;

    @NotBlank
    @Size(min = 2, max = 800, message = "A sua resposta deve conter entre 10 até no máximo 800 caracteres.")
    @Basic
    @Column(name = "texto")
    private String texto;

    @Basic
    @NotNull
    @Column(name = "fk_usuario", nullable = true)
    private Integer fkUsuario;

    @Transient
    private String nomeUsuario;

    @Transient
    private String fotoUsuario;

    @Basic
    @Column(name = "fk_publicacao", nullable = true)
    private Integer fkPublicacao;

    @NotNull
    @Basic
    @Column(name = "data_hora")
    private String dataHora;

    @Transient
    private Long DiasAtras;

    @OneToMany(mappedBy = "respostaByFkResposta")
    private Collection<CurtidaEntity> curtidasByIdResposta;

    @OneToMany(mappedBy = "respostaByFkResposta")
    private Collection<FavoritoEntity> favoritosByIdResposta;

    @ManyToOne
    @JoinColumn(name = "fk_usuario", referencedColumnName = "id_usuario", insertable = false, updatable = false)
    private UsuarioEntity usuarioByFkUsuario;

    @ManyToOne
    @JoinColumn(name = "fk_publicacao", referencedColumnName = "id_publicacao", insertable = false, updatable = false)
    private PublicacaoEntity publicacaoByFkPublicacao;

    public Long getDiasAtras() {

        LocalDateTime local;

        try{

            local = LocalDateTime.parse(dataHora.substring(0,19), DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm:ss"));

        }catch (Exception e){

            local = LocalDateTime.parse(dataHora.substring(0,19), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        }

        return local.until(LocalDateTime.now(), ChronoUnit.DAYS);
    }

    public String getNomeUsuario() {
        return usuarioByFkUsuario.getNome();
    }

    public String getFotoUsuario() {
        return usuarioByFkUsuario.getFotoPerfil();
    }

    public void setFotoUsuario(String nomeUsuario) {
        this.nomeUsuario = usuarioByFkUsuario.getFotoPerfil();
    }

    public String getCursoSemestre(){
        return  usuarioByFkUsuario.getSemestre() + "º " + usuarioByFkUsuario.getCurso() ;
    }

    public int getIdResposta() {
        return idResposta;
    }

//    public void setIdResposta(int idResposta) {
//        this.idResposta = idResposta;
//    }

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

    public Integer getFkPublicacao() {
        return fkPublicacao;
    }

    public void setFkPublicacao(Integer fkPublicacao) {
        this.fkPublicacao = fkPublicacao;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RespostaEntity that = (RespostaEntity) o;
        return idResposta == that.idResposta && Objects.equals(texto, that.texto) && Objects.equals(fkUsuario, that.fkUsuario) && Objects.equals(fkPublicacao, that.fkPublicacao) && Objects.equals(dataHora, that.dataHora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idResposta, texto, fkUsuario, fkPublicacao, dataHora);
    }

    public Integer QuantidadeDeCurtidas() {
        return curtidasByIdResposta.size();
    }

//    public void setCurtidasByIdResposta(Collection<CurtidaEntity> curtidasByIdResposta) {
//        this.curtidasByIdResposta = curtidasByIdResposta;
//    }

//    public Collection<FavoritoEntity> getFavoritosByIdResposta() {
//        return favoritosByIdResposta;
//    }
//
//    public void setFavoritosByIdResposta(Collection<FavoritoEntity> favoritosByIdResposta) {
//        this.favoritosByIdResposta = favoritosByIdResposta;
//    }

//    public UsuarioEntity getUsuarioByFkUsuario() {
//        return usuarioByFkUsuario;
//    }

//    public void setUsuarioByFkUsuario(UsuarioEntity usuarioByFkUsuario) {
//        this.usuarioByFkUsuario = usuarioByFkUsuario;
//    }

//    public PublicacaoEntity getPublicacaoByFkPublicacao() {
//        return publicacaoByFkPublicacao;
//    }

//    public void setPublicacaoByFkPublicacao(PublicacaoEntity publicacaoByFkPublicacao) {
//        this.publicacaoByFkPublicacao = publicacaoByFkPublicacao;
//    }
}
