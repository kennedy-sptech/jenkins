package br.com.studenton.sptechforum.repository;

import br.com.studenton.sptechforum.domain.PublicacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicacaoRepository extends JpaRepository<PublicacaoEntity, Integer> {
    PublicacaoEntity findPublicacaoEntitiesByIdPublicacao(Integer id);

    List<PublicacaoEntity> findPublicacaoEntitiesByFkUsuario(Integer fkUsuario);

    List<PublicacaoEntity> findAllByOrderByIdPublicacaoDesc();

    void deleteByIdPublicacao(Integer id);

    List<PublicacaoEntity> findByTextoLike(String texto);


    

}
