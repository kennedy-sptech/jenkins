package br.com.studenton.sptechforum.controller;

import br.com.studenton.sptechforum.domain.CurtidaEntity;
import br.com.studenton.sptechforum.service.CurtidaService;
import br.com.studenton.sptechforum.service.PublicacaoService;
import br.com.studenton.sptechforum.service.UsuarioService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("curtidas")
public class CurtidaController {

    final PublicacaoService publicacaoService;
    final UsuarioService usuarioService;
    final CurtidaService curtidaService;

    public CurtidaController(PublicacaoService publicacaoService, UsuarioService usuarioService, CurtidaService curtidaService) {
        this.publicacaoService = publicacaoService;
        this.usuarioService = usuarioService;
        this.curtidaService = curtidaService;
    }


    @PostMapping
    public ResponseEntity<Boolean> curtida(@RequestParam Integer idUsuario, @RequestParam Integer idPublicacao ){

        boolean confirmacao = curtidaService.curtirOuDescurtir(idPublicacao, idUsuario);
        if (!confirmacao){
            return ResponseEntity.status(200).body(false);
        }
        return ResponseEntity.status(201).body(true);
    }


    @GetMapping
    public ResponseEntity<List<CurtidaEntity>> all(){

        return curtidaService.getAll().isEmpty() ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(curtidaService.getAll());
    }

    @GetMapping("/verificar")
    public ResponseEntity<Boolean> getCurtidaById(@RequestParam Integer idUsu, @RequestParam Integer idPub ){
        return ResponseEntity.status(200).body(curtidaService.verificarCurtida(idUsu, idPub));
    }
}