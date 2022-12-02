package br.com.studenton.sptechforum.repository;

import br.com.studenton.sptechforum.domain.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    UsuarioEntity findUsuarioEntityByRaAndSenha(String ra, String senha);

    List<UsuarioEntity> findUsuarioEntityByCheckEmailTrue();

    UsuarioEntity findUsuarioEntitiesByRa(String ra);

    List<UsuarioEntity> findAllByAutenticadoTrue();

    UsuarioEntity findUsuarioEntitiesByIdUsuario(Integer id);

    UsuarioEntity findUsuarioEntitiesByIdUsuarioAndSenha(Integer id, String senha);

    Boolean existsByRa(String ra);

    @Modifying
    @Transactional
    @Query("update UsuarioEntity u set u.autenticado = ?1 where u.ra = ?2")
    void updateAutenticado(Boolean autenticado, String ra);

    UsuarioEntity findUsuarioEntitiesByRaAndEmail(String ra, String nome);
}
