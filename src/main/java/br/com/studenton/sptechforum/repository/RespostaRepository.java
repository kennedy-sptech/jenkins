package br.com.studenton.sptechforum.repository;

import br.com.studenton.sptechforum.domain.RespostaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RespostaRepository extends JpaRepository<RespostaEntity, Integer> {

    @Transactional
    public void deleteByIdResposta(Integer id);

    List<RespostaEntity> findAllByFkPublicacao(int idPublicacao);

    @Transactional
    @Modifying
    @Query("update RespostaEntity r set r.texto = ?2 where r.idResposta = ?1")
    void atualizarRespostaById(Integer idResposta, String texto);

    RespostaEntity findRespostaEntitiesByIdRespostaAndTextoLike(int id, String texto);

    RespostaEntity findByIdResposta(int id);

}
