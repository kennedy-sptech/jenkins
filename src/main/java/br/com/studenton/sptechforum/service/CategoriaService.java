package br.com.studenton.sptechforum.service;

import br.com.studenton.sptechforum.domain.CategoriaEntity;
import br.com.studenton.sptechforum.mapper.CategoriaResposta;
import br.com.studenton.sptechforum.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaService {

    final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaResposta> getAll(){
        List<CategoriaEntity> categorias = categoriaRepository.findAll();
        List<CategoriaResposta> categoriasResultado = new ArrayList<>();
        for (CategoriaEntity daVez : categorias){
            CategoriaResposta cat = new CategoriaResposta();
            cat.setIdCategoria(daVez.getIdCategoria());
            cat.setCategoria(daVez.getCategoria());
            categoriasResultado.add(cat);
        }
        return categoriasResultado;
    }

    public CategoriaEntity getId(int id){
        List<CategoriaEntity> categorias = categoriaRepository.findAll();
        for (CategoriaEntity daVez : categorias){
            if (daVez.getIdCategoria() == id){
                return daVez;
            }
        }
        return null;
    }
}
