package br.com.studenton.sptechforum.repository;

import br.com.studenton.sptechforum.domain.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Integer> {

}
