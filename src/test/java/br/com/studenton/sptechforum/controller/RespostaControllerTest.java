package br.com.studenton.sptechforum.controller;
import br.com.studenton.sptechforum.domain.PublicacaoEntity;
import br.com.studenton.sptechforum.domain.RespostaEntity;
import br.com.studenton.sptechforum.mapper.ResponderPublicacaoResquestBody;
import br.com.studenton.sptechforum.repository.PublicacaoRepository;
import br.com.studenton.sptechforum.repository.RespostaRepository;
import br.com.studenton.sptechforum.service.PublicacaoService;
import br.com.studenton.sptechforum.service.RespostaService;
import br.com.studenton.sptechforum.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = RespostaController.class)
class RespostaControllerTest {

    @Autowired
    RespostaController respostaController;

    @MockBean
    RespostaService respostaService;

    @MockBean
    RespostaRepository respostaRepository;

    @MockBean
    PublicacaoService publicacaoService;

    @MockBean
    PublicacaoRepository publicacaoRepository;

    @MockBean
    UsuarioService usuarioService;



    @Test
    @DisplayName("getAllRepostas() não deve trazer - não tem dado na tabela de resposta - 204")
    void getAllRepostasNaoDeveFuncionarTV(){
        when(respostaService.getAllRespostas()).thenReturn(new ArrayList<>());
        ResponseEntity<List<RespostaEntity>> teste = respostaController.getAllRepostas();
        assertEquals(204, teste.getStatusCodeValue());
    }

    @Test
    @DisplayName("getAllRepostas() deve trazer - tem registros na tabela - 200")
    void getAllRepostasDeveFuncionar(){
        RespostaEntity rp = new RespostaEntity();
        when(respostaService.getAllRespostas()).thenReturn(List.of(rp));
        ResponseEntity<List<RespostaEntity>> teste = respostaController.getAllRepostas();
        assertEquals(200, teste.getStatusCodeValue());

    }

    @Test
    @DisplayName("responderPergunta() - Não foi possivel encontrar a publicação - 404")
    void responderPerguntaNaoAchouaPublicacao(){
        ResponderPublicacaoResquestBody resposta = new ResponderPublicacaoResquestBody();
        when(publicacaoService.findPublicacaoById(1)).thenReturn(null);
        ResponseEntity teste = respostaController.responderPergunta(1, resposta);
        assertEquals(404, teste.getStatusCodeValue());
    }


}