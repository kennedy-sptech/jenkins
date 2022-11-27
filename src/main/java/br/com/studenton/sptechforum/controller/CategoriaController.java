package br.com.studenton.sptechforum.controller;

import br.com.studenton.sptechforum.domain.CategoriaEntity;
import br.com.studenton.sptechforum.mapper.CategoriaResposta;
import br.com.studenton.sptechforum.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("categorias")
public class CategoriaController {

    private CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResposta>> getAll(){
        List<CategoriaResposta> categorias = categoriaService.getAll();

        if (categorias != null){
            return ResponseEntity.status(201).body(categorias);
        }
        return ResponseEntity.status(401).build();
    }
}
