package br.com.studenton.sptechforum.controller;

import br.com.studenton.sptechforum.domain.FavoritoEntity;
import br.com.studenton.sptechforum.domain.PublicacaoEntity;
import br.com.studenton.sptechforum.service.FavoritoService;
import br.com.studenton.sptechforum.service.PublicacaoService;
import br.com.studenton.sptechforum.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("favoritos")
public class FavoritoController {

    final PublicacaoService publicacaoService;
    final UsuarioService usuarioService;
    final FavoritoService favoritoService;

    public FavoritoController(PublicacaoService publicacaoService, UsuarioService usuarioService, FavoritoService favoritoService) {
        this.publicacaoService = publicacaoService;
        this.usuarioService = usuarioService;
        this.favoritoService = favoritoService;
    }


    @PostMapping
    public ResponseEntity<Boolean> favoritar(@RequestParam Integer idUsuario, @RequestParam Integer idPublicacao ){

        boolean confirmacao = favoritoService.favoritar(idPublicacao, idUsuario);
        if (!confirmacao){
            return ResponseEntity.status(200).body(false);
        }
        return ResponseEntity.status(201).body(true);
    }

    @DeleteMapping
    public ResponseEntity desfavoritar(@RequestParam Integer idUsuario, @RequestParam Integer idPublicacao ){
        boolean confirmacao = favoritoService.desfavoritar(idPublicacao, idUsuario);
        if (!confirmacao){
            return ResponseEntity.status(409).build();
        }

        return ResponseEntity.status(200).build();
    }

    @GetMapping("filtro-favoritos")
    public ResponseEntity<List<PublicacaoEntity>> findFavoritosById(@RequestParam Integer idUsuario){
        List<PublicacaoEntity> lista = favoritoService.getSalvos(idUsuario);
        return !lista.isEmpty() ? ResponseEntity.status(200).body(lista) : ResponseEntity.status(204).build();
    }

    @GetMapping("verificar")
    public ResponseEntity<Boolean> getSalvosById(@RequestParam Integer idUsu, @RequestParam Integer idPub ){
        return ResponseEntity.status(200).body(favoritoService.verificarSalvo(idUsu, idPub));
    }

}