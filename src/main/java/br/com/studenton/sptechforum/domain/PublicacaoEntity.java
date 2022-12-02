package br.com.studenton.sptechforum.domain;

import br.com.studenton.sptechforum.mapper.PublicacaoPostRequestBody;
import org.apache.tomcat.jni.Local;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "publicacao")
public class PublicacaoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_publicacao", nullable = false)
    private int idPublicacao;

    @NotBlank
    @Size(min = 5, max = 100, message = "O titulo da sua publicação deve ter entre 5 a 100 caracteres.")
    @Basic
    @Column(name = "titulo")
    private String titulo;

    @Transient
    private String nomeUsuario;

    @Transient
    private String fotoUsuario;

    @NotBlank
    @Size(min = 10, max = 800, message = "A sua publicação deve conter entre 10 até no máximo 800 caracteres.")
    @Basic
    @Column(name = "texto")
    private String texto;

    @Basic
    @Column(name = "fk_categoria", nullable = true)
    private Integer fkCategoria;

    @Transient
    private String categoria;

    @Range(min = 1, max = 2, message = "O tipo da sua publicação deve estar entre o n° 1 ou 2.")
    @NotNull
    @Basic
    @Column(name = "tipo_publicacao")
    private Integer tipoPublicacao;

    @Basic
    @Column(name = "fk_usuario", nullable = true)
    private Integer fkUsuario;


    @NotNull
    @Basic
    @Column(name = "data_hora")
    private String dataHora;

    @Transient
    private Long DiasAtras;

    @Range(message = "O campo status precisa receber um numérico de 1 a 3")
    @NotNull
    @Basic
    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "publicacaoByFkPublicacao")
    private List<CurtidaEntity> curtidasByIdPublicacao;

    @Transient
    private List<Integer> usuariosCurtidas;

    @Transient List<Integer> usuariosSalvos;

    @OneToMany(mappedBy = "publicacaoByFkPublicacao")
    private List<FavoritoEntity> favoritosByIdPublicacao;

    @ManyToOne
    @JoinColumn(name = "fk_categoria", referencedColumnName = "id_categoria", insertable = false, updatable = false)
    private CategoriaEntity categoriaByFkCategoria;

    @ManyToOne
    @JoinColumn(name = "fk_usuario", referencedColumnName = "id_usuario", insertable = false, updatable = false)
    private UsuarioEntity usuarioByFkUsuario;

    @OneToMany(mappedBy = "publicacaoByFkPublicacao")
    private List<RespostaEntity> respostasByIdPublicacao;

    @Transient
    private Integer countCurtidas;

    public PublicacaoEntity() {

    }

    public PublicacaoEntity(PublicacaoPostRequestBody publicacaoPostRequestBody) {
        titulo = publicacaoPostRequestBody.getTitulo();
        texto = publicacaoPostRequestBody.getTexto();
        fkCategoria = publicacaoPostRequestBody.getFkCategoria();
        tipoPublicacao = publicacaoPostRequestBody.getTipoPublicacao();
        fkUsuario = publicacaoPostRequestBody.getFkUsuario();

        dataHora = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:mm"))+".000";
        status = publicacaoPostRequestBody.getStatus();
    }

    public Long getDiasAtras() {

        LocalDateTime local;

        try{

            local = LocalDateTime.parse(dataHora.substring(0,16), DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm"));

        }catch (Exception e){

            local = LocalDateTime.parse(dataHora.substring(0,16), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        }
        System.out.println(local);

        return local.until(LocalDateTime.now(), ChronoUnit.DAYS);
    }

    public String getNomeUsuario() {
        return usuarioByFkUsuario.getNome();
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = usuarioByFkUsuario.getNome();
    }

    public String getCursoSemestre(){
        return  usuarioByFkUsuario.getSemestre() + "º " + usuarioByFkUsuario.getCurso() ;
    }

    public String getFotoUsuario() {
        return usuarioByFkUsuario.getFotoPerfil();
    }

    public void setFotoUsuario(String nomeUsuario) {
        this.nomeUsuario = usuarioByFkUsuario.getFotoPerfil();
    }

    public String getCategoria(){
        return categoriaByFkCategoria.getCategoria();
    }

    public int getIdPublicacao() {
        return idPublicacao;
    }

    public void setIdPublicacao(int idPublicacao) {
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

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
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
        PublicacaoEntity that = (PublicacaoEntity) o;
        return idPublicacao == that.idPublicacao && Objects.equals(titulo, that.titulo) && Objects.equals(texto, that.texto) && Objects.equals(fkCategoria, that.fkCategoria) && Objects.equals(tipoPublicacao, that.tipoPublicacao) && Objects.equals(fkUsuario, that.fkUsuario) && Objects.equals(dataHora, that.dataHora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPublicacao, titulo, texto, fkCategoria, tipoPublicacao, fkUsuario, dataHora);
    }

    public List<RespostaEntity> getRespostasByIdPublicacao() {
        return respostasByIdPublicacao;
    }

    public void setRespostasByIdPublicacao(List<RespostaEntity> respostasByIdPublicacao) {
        this.respostasByIdPublicacao = respostasByIdPublicacao;
    }

    public Integer getCountCurtidas() {
        return curtidasByIdPublicacao == null ? 0 : curtidasByIdPublicacao.size();
    }

//    public List<CurtidaEntity> getCurtidasByIdPublicacao() {
//
//        return curtidasByIdPublicacao;
//    }

    public List<Integer> getUsuariosCurtidas(){
        List<CurtidaEntity> curtidas = curtidasByIdPublicacao;
        List<Integer> usuariosCurtidas = new ArrayList<>();
        for (CurtidaEntity daVez: curtidas){
            usuariosCurtidas.add(daVez.getFkUsuario());
        }
        return usuariosCurtidas;
    }

    public List<Integer> getUsuariosSalvos(){
        List<FavoritoEntity> favoritos = favoritosByIdPublicacao;
        List<Integer> usuariosFavoritos = new ArrayList<>();
        for (FavoritoEntity daVez : favoritos){
            usuariosFavoritos.add(daVez.getFkUsuario());
        }
        return usuariosFavoritos;
    }
//
//    public void setCurtidasByIdPublicacao(Collection<CurtidaEntity> curtidasByIdPublicacao) {
//        this.curtidasByIdPublicacao = curtidasByIdPublicacao;
//    }
//
//    public Collection<FavoritoEntity> getFavoritosByIdPublicacao() {
//        return favoritosByIdPublicacao;
//    }
//
//    public void setFavoritosByIdPublicacao(Collection<FavoritoEntity> favoritosByIdPublicacao) {
//        this.favoritosByIdPublicacao = favoritosByIdPublicacao;
//    }
//
//    public CategoriaEntity getCategoriaByFkCategoria() {
//        return categoriaByFkCategoria;
//    }
//
//    public void setCategoriaByFkCategoria(CategoriaEntity categoriaByFkCategoria) {
//        this.categoriaByFkCategoria = categoriaByFkCategoria;
//    }
//
//    public UsuarioEntity getUsuarioByFkUsuario() {
//        return usuarioByFkUsuario;
//    }
//
//    public void setUsuarioByFkUsuario(UsuarioEntity usuarioByFkUsuario) {
//        this.usuarioByFkUsuario = usuarioByFkUsuario;
//    }
//
//    public Collection<RespostaEntity> getRespostasByIdPublicacao() {
//        return respostasByIdPublicacao;
//    }
//
//    public void setRespostasByIdPublicacao(Collection<RespostaEntity> respostasByIdPublicacao) {
//        this.respostasByIdPublicacao = respostasByIdPublicacao;
//    }
}