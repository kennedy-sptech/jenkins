package br.com.studenton.sptechforum.controller;

import br.com.studenton.sptechforum.domain.RespostaEntity;
import br.com.studenton.sptechforum.mapper.AtualizarRespostaRequestBody;
import br.com.studenton.sptechforum.mapper.ResponderPublicacaoResquestBody;
import br.com.studenton.sptechforum.service.PublicacaoService;
import br.com.studenton.sptechforum.service.RespostaService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("respostas")
public class RespostaController {

    final PublicacaoService publicacaoService;
    final RespostaService respostaService;

    public RespostaController(PublicacaoService publicacaoService, RespostaService respostaService) {
        this.publicacaoService = publicacaoService;
        this.respostaService = respostaService;
    }

    @GetMapping
    public ResponseEntity<List<RespostaEntity>> getAllRepostas(){

        return respostaService.getAllRespostas().isEmpty()? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(respostaService.getAllRespostas());

    }

    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Publicação respondida"),
            @ApiResponse(responseCode = "404", description = "Não foi possivel encontrar a publicação"),
            @ApiResponse(responseCode = "409", description = "Essa Publicação já foi respondida")
    })
    @PostMapping
    public ResponseEntity responderPergunta(@RequestParam Integer idPublicacao,
                                                            @RequestBody ResponderPublicacaoResquestBody
                                                                    resposta){

        if(publicacaoService.findPublicacaoById(idPublicacao) == null){

            return ResponseEntity.status(404).build();

        }

        if(publicacaoService.findPublicacaoById(idPublicacao).getTipoPublicacao() == 1){

            respostaService.respostaDuvida(resposta, idPublicacao);
            return ResponseEntity.status(200).build();

        }else if(publicacaoService.findPublicacaoById(idPublicacao).getRespostasByIdPublicacao().isEmpty() &&
                publicacaoService.findPublicacaoById(idPublicacao).getStatus() == 1){

            publicacaoService.updateStatusById(idPublicacao, 2);

            respostaService.respostaDuvida(resposta, idPublicacao);

            return ResponseEntity.status(200).build();

        }

        return ResponseEntity.status(409).build();

    }

    @DeleteMapping
    public ResponseEntity deletarResposta(@RequestParam Integer id){

        if(respostaService.existeById(id)){
            respostaService.deletarRespostaById(id);
            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(404).build();
    }

    @GetMapping("/getFk")
    public ResponseEntity<List<RespostaEntity>> getFk(@RequestParam Integer idPublicacao){

        if (!respostaService.finByFkPublicacao(idPublicacao).isEmpty()){
            return ResponseEntity.status(200).body(respostaService.finByFkPublicacao(idPublicacao));
        }
        return ResponseEntity.status(404).build();
    }

    @PatchMapping("/atualizar-resposta")
    public ResponseEntity<RespostaEntity> updateResposta(
            @RequestParam Integer idResposta, @RequestBody AtualizarRespostaRequestBody atualizar
    ){
        if (respostaService.existeById(idResposta)){

            respostaService.updateResposta(atualizar.getTexto(), idResposta);
            return ResponseEntity.status(200).build();

        }

            return ResponseEntity.status(404).build();
    }
}
