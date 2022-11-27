package br.com.studenton.sptechforum.repository;

import br.com.studenton.sptechforum.domain.CurtidaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CurtidaRepository extends JpaRepository<CurtidaEntity, Integer> {

    long countByFkPublicacaoAndFkUsuario(Integer idPublicacao, Integer idUsuario);

    CurtidaEntity findCurtidaEntitiesByFkPublicacaoAndFkUsuario(Integer idPublicacao, Integer idUsuario);

    @Transactional
    @Modifying
    @Query("delete from CurtidaEntity c where c.fkPublicacao=?1 and c.fkUsuario=?2")
    void deleteCurtida(Integer fkPub, Integer fkUsu);

    List<CurtidaEntity> getCurtidaEntityByIdCurtida(Integer id);
}