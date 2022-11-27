package br.com.studenton.sptechforum.controller;

import br.com.studenton.sptechforum.domain.UsuarioEntity;
import br.com.studenton.sptechforum.mapper.*;
import br.com.studenton.sptechforum.service.UsuarioService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    final UsuarioService usuarioService;
    private final Path root = Paths.get("uploads");

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioEntity>> findAllUsuarios() {
        if (usuarioService.findAllUsuarios().isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(usuarioService.findAllUsuarios());
    }

//    @GetMapping
//    public ResponseEntity<UsuarioEntity> findUsuarioById(@RequestParam String id) {
//        //TODO
//        return ResponseEntity.ok(null);
//    }

    @PostMapping
    public ResponseEntity<UsuarioEntity> findByRaAndSenha(@RequestBody @Valid LoginPostRequestBody login) {

        UsuarioEntity usuario = usuarioService.findUsuarioByRaAndSenha(login.pegarSenha(), login.getRa());

        if (usuario != null) {
            usuario.setAutenticado(true);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }


    @DeleteMapping
    public ResponseEntity<UsuarioEntity> findByRa(@RequestParam String ra) {
        UsuarioEntity usuario = usuarioService.findUsuarioByRa(ra);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/autenticados")
    public ResponseEntity<List<UsuarioEntity>> findAutenticados() {
        return usuarioService.findAutenticados().isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAutenticados());
    }

    @GetMapping({"/relatorio"})
    @ApiResponses({@ApiResponse(
            responseCode = "200",
            content = {@Content(
                    mediaType = "text/csv"
            )}
    )})
    public ResponseEntity getRelatorio() {
        return ((ResponseEntity.BodyBuilder) ((ResponseEntity.BodyBuilder)
                ResponseEntity.status(200).header("content-type", new String[]{"text/csv"}))
                .header("content-disposition", new String[]{"filename=\"all-list-alunos-sptech-forum.csv\""}))
                .body(usuarioService.exportCsv());
    }

    @GetMapping({"/gerar-txt"})
    @ApiResponses({@ApiResponse(
            responseCode = "200",
            content = {@Content(
                    mediaType = "text/plain"
            )}
    )})
    public ResponseEntity gerarTxt() {

        return ((ResponseEntity.BodyBuilder) ((ResponseEntity.BodyBuilder)
                ResponseEntity.status(200).header("content-type", new String[]{"text/plain"})).
                header("attachment", new String[]{"filename=\"teste.txt\""}))
                .body(usuarioService.exportTxt());

    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo txt lido e adicionado na fila"),
            @ApiResponse(responseCode = "507", description = "A fila está cheia."),
            @ApiResponse(responseCode = "500", description = "Erro com o arquivo.")
    })
    @PostMapping("/gravar-txt")
    public ResponseEntity<List<UsuarioFilaRespostaBody>> uploadTxt(@RequestBody MultipartFile txt) {

        List<UsuarioEntity> usuario;

        String fileName = txt.getOriginalFilename();
        Path currentRelativePath = Paths.get("");
        String url = System.getProperty("user.home") + "\\" + fileName;

        try {

            txt.transferTo(new File(url));

            if (!usuarioService.gravarTxt(fileName)) {

                return ResponseEntity.status(507).build();

            }

        } catch (IOException e) {
            System.out.println(e);
            return ResponseEntity.status(500).build();
        }

        return ResponseEntity.status(200).body(usuarioService.respostaFilaObj());

    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioEntity> getUsuarioId(@RequestParam Integer id) {
        return usuarioService.existById(id) ?
                ResponseEntity.status(200).body(usuarioService.findUsuarioById(id)) :
                ResponseEntity.status(404).build();

    }

    @PatchMapping("/mudar-check")
    public ResponseEntity<Boolean> updateCheck(@RequestParam Integer id) {
        return ResponseEntity.status(200).body(usuarioService.trocarCheck(id));
    }

    @PatchMapping("/mudar-senha")
    public ResponseEntity updateSenha(@RequestParam Integer id,
                                      @RequestBody @Valid MudarSenhaRequestBody mudar) {
        boolean validacao = usuarioService.mudarSenha(id, mudar.getSenhaAntiga(), mudar.getSenhaNova());
        return validacao == true ? ResponseEntity.status(200).build() : ResponseEntity.status(404).build();
    }

    @PatchMapping("/mudar-perfil")
    public ResponseEntity updatePerfil(@RequestParam Integer id, @RequestParam String perfil) {
        UsuarioEntity usuario = usuarioService.trocarPerfil(id, perfil);
        return usuario != null ? ResponseEntity.status(200).build() : ResponseEntity.status(404).build();
    }

    @PatchMapping("esqueci-senha")
    public ResponseEntity esqueciSenha(@RequestBody @Valid EsqueciSenhaBody esqueci) {
        boolean validacao = usuarioService.esqueciSenha(esqueci.getEmail(), esqueci.getRa(), esqueci.getSenhaNova());
        return validacao == true ? ResponseEntity.status(200).build() : ResponseEntity.status(404).build();
    }

}
