package br.com.studenton.sptechforum.repository;

import br.com.studenton.sptechforum.domain.CurtidaEntity;
import br.com.studenton.sptechforum.domain.FavoritoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface FavoritoRepository extends JpaRepository<FavoritoEntity, Integer> {

    long countByFkPublicacaoAndFkUsuario(Integer idPub, Integer idUsu);

    FavoritoEntity findFavoritoEntitiesByFkPublicacaoAndFkUsuario(Integer idPublicacao, Integer idUsuario);

    @Transactional
    @Modifying
    @Query("delete from FavoritoEntity f where f.fkPublicacao=?1 and f.fkUsuario=?2")
    void deleteFavorito(Integer fkPub, Integer fkUsu);

    List<FavoritoEntity> findAllByFkUsuario(Integer idUsuario);
}