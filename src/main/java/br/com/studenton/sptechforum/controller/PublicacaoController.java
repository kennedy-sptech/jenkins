package br.com.studenton.sptechforum.controller;

import br.com.studenton.sptechforum.domain.PublicacaoEntity;
import br.com.studenton.sptechforum.domain.RespostaEntity;
import br.com.studenton.sptechforum.mapper.AtualizarDuvidaRequestBody;
import br.com.studenton.sptechforum.mapper.AtualizarRespostaRequestBody;
import br.com.studenton.sptechforum.mapper.PublicacaoPostRequestBody;
import br.com.studenton.sptechforum.service.CategoriaService;
import br.com.studenton.sptechforum.service.PublicacaoService;
import br.com.studenton.sptechforum.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("publicacoes")
public class PublicacaoController {

    final PublicacaoService publicacaoService;
    final UsuarioService usuarioService;

    final CategoriaService categoriaService;

    public PublicacaoController(PublicacaoService publicacaoService, UsuarioService usuarioService, CategoriaService categoriaService) {
        this.publicacaoService = publicacaoService;
        this.usuarioService = usuarioService;
        this.categoriaService = categoriaService;
    }


    @GetMapping
    public ResponseEntity<List<PublicacaoEntity>> findAll() {
        if (publicacaoService.findAllPublicacoes().isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(publicacaoService.findAllPublicacoes());
    }

    @GetMapping("/perguntas")
    public ResponseEntity<List<PublicacaoEntity>> findAllPerguntas() {

        if (publicacaoService.findAllPerguntas().isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(publicacaoService.findAllPerguntas());
    }

    @GetMapping("/publicacao")
    public ResponseEntity<PublicacaoEntity> findById(@RequestParam Integer id) {

        if (!(publicacaoService.findAllPublicacoes().isEmpty())) {

            if (publicacaoService.existById(id)) {
                return ResponseEntity.status(200).body(publicacaoService.findPublicacaoById(id));
            }
        }
        return ResponseEntity.status(404).build();

    }

    @PostMapping("/publicar")
    public ResponseEntity realizarPublicacao(@RequestBody @Valid PublicacaoPostRequestBody novaPublicacao) {
        publicacaoService.realizarPublicacao(novaPublicacao);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/minhas-duvidas")
    public ResponseEntity<List<PublicacaoEntity>> findDuvidasByIdUsuario(@RequestParam Integer id) {
        if (!(publicacaoService.findAllPublicacoes().isEmpty())) {
            if (usuarioService.existById(id)) {
                return ResponseEntity.status(200).body(publicacaoService.findDuvidasByIdUsuario(id));
            }
            return ResponseEntity.status(404).build();

        }
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/atualizar-duvida")
    public ResponseEntity<PublicacaoEntity> atualizarDuvida(@RequestBody @Valid AtualizarDuvidaRequestBody duvida) {
        if (!(publicacaoService.findAllPublicacoes().isEmpty())) {
            if (publicacaoService.existById(duvida.getIdPublicacao())) {
                return ResponseEntity.status(200).body(publicacaoService.atualizarDuvida(duvida));
            }
        }
        return ResponseEntity.status(404).build();

    }

    @DeleteMapping("/apagar-duvida")
    public ResponseEntity<Void> deleteDuvidaById( @RequestParam Integer id) {
        if (!(publicacaoService.findAllPublicacoes().isEmpty())) {
            if (publicacaoService.existById(id)) {
                publicacaoService.deletePublicacaoById(id);
                return ResponseEntity.status(200).build();
            }
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/ordenado")
    public ResponseEntity<List<PublicacaoEntity>> getAllOrdenado() {
        if (!(publicacaoService.findAllPublicacoes().isEmpty())) {
            return ResponseEntity.status(200).body(publicacaoService.getAllOrdenado());
        }
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/minha-colaboracao")
    public ResponseEntity<List<PublicacaoEntity>> getRespostasByUsuario(@RequestParam Integer id) {

        if (!(publicacaoService.findAllPublicacoes().isEmpty())) {
            if (!(publicacaoService.getMinhaColaboracao(id).isEmpty())) {
                return ResponseEntity.status(200).body(publicacaoService.getMinhaColaboracao(id));
            }
        }
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/update-status")
    public ResponseEntity<PublicacaoEntity> updateStatus(
            @RequestParam Integer idPublicacao, @RequestParam Integer status) {
        PublicacaoEntity publicacaoEntity = publicacaoService.updateStatusById(idPublicacao, status);
//        usuarioService.findUsuarioById(publicacaoEntity.getFkUsuario())
//                .statusChange(status, publicacaoEntity);
        return ResponseEntity.status(200).body(publicacaoEntity);
    }



    @GetMapping("/filtro-categoria")
    public ResponseEntity<List<PublicacaoEntity>> findByCategoria(@RequestParam int idCategoria){
        if (categoriaService.getId(idCategoria) != null){
            if (!publicacaoService.getFiltroCategoria(idCategoria).isEmpty()){
                return ResponseEntity.status(200).body(publicacaoService.getFiltroCategoria(idCategoria));
            }
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("filtro-status")
    public ResponseEntity<List<PublicacaoEntity>> fingByStatus(@RequestParam Integer status){
        List<PublicacaoEntity> lista = publicacaoService.getByStatus(status);
        return !lista.isEmpty() ? ResponseEntity.status(200).body(lista) :
                                    ResponseEntity.status(204).build();
    }





}