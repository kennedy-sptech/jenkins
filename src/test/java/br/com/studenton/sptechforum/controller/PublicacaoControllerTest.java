package br.com.studenton.sptechforum.controller;

import br.com.studenton.sptechforum.domain.CategoriaEntity;
import br.com.studenton.sptechforum.domain.PublicacaoEntity;
import br.com.studenton.sptechforum.domain.UsuarioEntity;
import br.com.studenton.sptechforum.mapper.AtualizarDuvidaRequestBody;
import br.com.studenton.sptechforum.mapper.PublicacaoPostRequestBody;
import br.com.studenton.sptechforum.observer.EmailSender;
import br.com.studenton.sptechforum.repository.PublicacaoRepository;
import br.com.studenton.sptechforum.service.CategoriaService;
import br.com.studenton.sptechforum.service.PublicacaoService;
import br.com.studenton.sptechforum.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = PublicacaoController.class)
class PublicacaoControllerTest {

    @Autowired
    PublicacaoController publicacaoController;

    @MockBean
    PublicacaoService publicacaoService;

    @MockBean
    PublicacaoRepository publicacaoRepository;

    @MockBean
    UsuarioService usuarioService;

    @MockBean
    CategoriaService categoriaService;

    @MockBean
    EmailSender emailSender;

    //metodos findAll()
    @Test
    @DisplayName("getAll() deve retornar 200 e com corpo")
    void findAllComRetorno(){

        PublicacaoEntity p1 = new PublicacaoEntity();
        PublicacaoEntity p2 = new PublicacaoEntity();

        when(publicacaoService.findAllPublicacoes()).thenReturn(
                List.of(p1, p2)
        );
        ResponseEntity<List<PublicacaoEntity>> all = publicacaoController.findAll();
        assertEquals(200, all.getStatusCodeValue());
        assertNotNull(all.getBody());
    }

    @Test
    @DisplayName("getAll() deve retornar 204 e sem corpo")
    void findAllSemRetorno(){
        when(publicacaoService.findAllPublicacoes()).thenReturn(
                new ArrayList<>()
        );
        ResponseEntity<List<PublicacaoEntity>> all = publicacaoController.findAll();
        assertEquals(204, all.getStatusCodeValue());
        assertNull(all.getBody());
    }

    //metodos findAllPerguntas()
    @Test
    @DisplayName("findAllPerguntas() deve retornar status 200 com corpo")
    void findAllPerguntasComResultado(){

        PublicacaoEntity p1 = new PublicacaoEntity();
        PublicacaoEntity p2 = new PublicacaoEntity();
        when(publicacaoService.findAllPerguntas()).thenReturn(
                List.of(p1, p2)
        );
        ResponseEntity<List<PublicacaoEntity>> perguntas = publicacaoController.findAllPerguntas();
        assertEquals(200, perguntas.getStatusCodeValue());
        assertNotNull(perguntas);
    }



    @Test
    @DisplayName("findAllPerguntas() deve retornar status 204 semm corpo")
    void findAllPerguntasSemResultado(){

        when(publicacaoService.findAllPerguntas()).thenReturn(
                new ArrayList<>()
        );
        ResponseEntity<List<PublicacaoEntity>> perguntas = publicacaoController.findAllPerguntas();
        assertEquals(404, perguntas.getStatusCodeValue());
    }

    //metodos findById()

    @Test
    @DisplayName("findById() não deve trazer - não tem dado na tabela de publicacao ")
    void findByIdDeveRetornarTV(){
        when(publicacaoService.findAllPublicacoes()).thenReturn(new ArrayList<>());
        ResponseEntity<PublicacaoEntity> teste = publicacaoController.findById(1);
        assertEquals(404, teste.getStatusCodeValue());
    }

    @Test
    @DisplayName("findById() deve retornar uma publicação - status 200")
    void findByIdDeveRetornar(){
        PublicacaoEntity p1 = new PublicacaoEntity();

        when(publicacaoService.findAllPublicacoes()).thenReturn(List.of(p1));
        when(publicacaoService.existById(1)).thenReturn(true);
        when(publicacaoService.findPublicacaoById(1)).thenReturn(new PublicacaoEntity());
        ResponseEntity<PublicacaoEntity> teste = publicacaoController.findById(1);
        assertEquals(200, teste.getStatusCodeValue());
        assertNotNull(teste);
    }

    @Test
    @DisplayName("findById() nao deve retornar uma publicação - status 404")
    void findByIdNaoDeveRetornar(){
        PublicacaoEntity p1 = new PublicacaoEntity();
        when(publicacaoService.findAllPublicacoes()).thenReturn(List.of(p1));
        when(publicacaoService.existById(1)).thenReturn(false);
        ResponseEntity<PublicacaoEntity> teste = publicacaoController.findById(1);
        assertEquals(404, teste.getStatusCodeValue());
        assertNull(teste.getBody());
    }

    //metodo realizarPublicacao()

    @Test
    @DisplayName("realizarPublicacao() deve publicar e deve retornar 201 e sem corpo")
    void realizarPublicacaoDevePublicar(){
        PublicacaoPostRequestBody p1 = new PublicacaoPostRequestBody();
        when(publicacaoService.realizarPublicacao(p1)).thenReturn(
                new PublicacaoEntity()
        );
        ResponseEntity postar = publicacaoController.realizarPublicacao(p1);
        assertEquals(201, postar.getStatusCodeValue());
        assertNull(postar.getBody());
    }

    //metodo findDuvidasByIdUsuario()

    @Test
    @DisplayName("findDuvidasByIdUsuario() - deve funcionar e retornar 200")
    void findDuvidasByIdUsuarioDeveFuncionar(){
        PublicacaoEntity p1 = new PublicacaoEntity();
        when(publicacaoService.findAllPublicacoes()).thenReturn(List.of(p1));
        when(usuarioService.existById(1)).thenReturn(true);
        ResponseEntity<List<PublicacaoEntity>> lista = publicacaoController.findDuvidasByIdUsuario(1);
        assertEquals(200, lista.getStatusCodeValue());
        assertNotNull(lista);
    }

    @Test
    @DisplayName("findDuvidasByIdUsuario() - não deve funcionar e retornar 404")
    void findDuvidasByIdUsuarioNaoDeveFuncionar(){
        PublicacaoEntity p1 = new PublicacaoEntity();
        when(publicacaoService.findAllPublicacoes()).thenReturn(List.of(p1));
        when(usuarioService.existById(1)).thenReturn(false);
        ResponseEntity<List<PublicacaoEntity>> lista = publicacaoController.findDuvidasByIdUsuario(1);
        assertEquals(404, lista.getStatusCodeValue());
        assertNull(lista.getBody());
    }

    @Test
    @DisplayName("findDuvidasByIdUsuario() - não deve funcionar e retornar 204")
    void findDuvidasByIdUsuarioNaoTemDadosNaTabela(){
        PublicacaoEntity p1 = new PublicacaoEntity();
        when(publicacaoService.findAllPublicacoes()).thenReturn(new ArrayList<>());
        when(usuarioService.existById(1)).thenReturn(false);
        ResponseEntity<List<PublicacaoEntity>> lista = publicacaoController.findDuvidasByIdUsuario(1);
        assertEquals(204, lista.getStatusCodeValue());
        assertNull(lista.getBody());
    }

    //metodo atualizarDuvida()

    @Test
    @DisplayName("atualizarDuvida() não deve trazer - não tem dado na tabela de publicacao ")
    void atualizarDuvidaNaoDeveRetornarTV(){
        when(publicacaoService.findAllPublicacoes()).thenReturn(new ArrayList<>());
        ResponseEntity<PublicacaoEntity> teste = publicacaoController.atualizarDuvida(new AtualizarDuvidaRequestBody());
        assertEquals(404, teste.getStatusCodeValue());
    }

    @Test
    @DisplayName("atualizarDuvida() deve funcionar e retornar status 200 ")
    void atualizarDuvidaDeveFuncionar(){
        PublicacaoEntity p1 = new PublicacaoEntity();
        AtualizarDuvidaRequestBody duvida = new AtualizarDuvidaRequestBody(1,
                "teste", "teste123", 3);

        when(publicacaoService.findAllPublicacoes()).thenReturn(List.of(p1));
        when(publicacaoService.existById(duvida.getIdPublicacao())).thenReturn(true);
        when(publicacaoService.atualizarDuvida(duvida)).thenReturn(
                new PublicacaoEntity()
        );

        ResponseEntity<PublicacaoEntity> teste = publicacaoController.atualizarDuvida(duvida);
        assertEquals(200, teste.getStatusCodeValue());
    }

    @Test
    @DisplayName("atualizarDuvida() não deve funcionar e deve retornar status 404 ")
    void atualizarDuvidaNaoDeveFuncionar(){
        PublicacaoEntity p1 = new PublicacaoEntity();
        AtualizarDuvidaRequestBody duvida = new AtualizarDuvidaRequestBody(1,
                "teste", "teste123", 3);

        when(publicacaoService.findAllPublicacoes()).thenReturn(List.of(p1));
        when(publicacaoService.existById(duvida.getIdPublicacao())).thenReturn(false);
        when(publicacaoService.atualizarDuvida(duvida)).thenReturn(
                new PublicacaoEntity()
        );

        ResponseEntity<PublicacaoEntity> teste = publicacaoController.atualizarDuvida(duvida);
        assertEquals(404, teste.getStatusCodeValue());
    }

    //metodo deleteDuvidaById()
    @Test
    @DisplayName("deleteDuvidaById() não deve deletar - não tem dado na tabela de publicacao")
    void deleteDuvidaByIdNaoDeveDeletarTabelaVazia(){
        when(publicacaoService.findAllPublicacoes()).thenReturn(new ArrayList<>());
        ResponseEntity teste = publicacaoController.deleteDuvidaById(1);
        assertEquals(404, teste.getStatusCodeValue());
    }

    @Test
    @DisplayName("deleteDuvidaById() não deve deletar - não tem publicacao com esse id")
    void deleteDuvidaByIdNaoDeveDeletarIdNaoEncontrado(){
        PublicacaoEntity p1 = new PublicacaoEntity();
        when(publicacaoService.findAllPublicacoes()).thenReturn(
                List.of(p1)
        );
        when(publicacaoService.existById(1)).thenReturn(false);
        ResponseEntity teste = publicacaoController.deleteDuvidaById(1);
        assertEquals(404, teste.getStatusCodeValue());
    }

    @Test
    @DisplayName("deleteDuvidaById() deve deletar - id encontrado")
    void deleteDuvidaByIdDeveDeletar(){
        PublicacaoEntity p1 = new PublicacaoEntity();
        when(publicacaoService.findAllPublicacoes()).thenReturn(
                List.of(p1)
        );
        when(publicacaoService.existById(1)).thenReturn(true);
        ResponseEntity teste = publicacaoController.deleteDuvidaById(1);
        assertEquals(200, teste.getStatusCodeValue());
    }

    //metodo getAllOrdenado()

    @Test
    @DisplayName("getAllOrdenado() não deve trazer - não tem dado na tabela de publicacao ")
    void getAllOrdenadoNaoDeveTrazer(){
        when(publicacaoService.findAllPublicacoes()).thenReturn(new ArrayList<>());
        ResponseEntity teste = publicacaoController.getAllOrdenado();
        assertEquals(204, teste.getStatusCodeValue());
    }

    @Test
    @DisplayName("getAllOrdenado() deve trazer - publicacoes ordenadas ")
    void getAllOrdenadoDeveTrazer(){
        PublicacaoEntity p1 = new PublicacaoEntity();
        when(publicacaoService.findAllPublicacoes()).thenReturn(List.of(p1));
        ResponseEntity teste = publicacaoController.getAllOrdenado();
        assertEquals(200, teste.getStatusCodeValue());
    }

    //metodo getRespostasByUsuario()
    @Test
    @DisplayName("getRespostasByUsuario() não deve trazer - não tem dado na tabela de publicacao ")
    void getRespostasByUsuarioNaoDeveTrazerTV(){
        when(publicacaoService.findAllPublicacoes()).thenReturn(new ArrayList<>());
        ResponseEntity teste = publicacaoController.getRespostasByUsuario(1);
        assertEquals(204, teste.getStatusCodeValue());
    }

    @Test
    @DisplayName("getRespostasByUsuario() não deve trazer - não tem dado na tabela com o id")
    void getRespostasByUsuarioNaoDeveTrazer(){
        PublicacaoEntity p1 = new PublicacaoEntity();
        when(publicacaoService.findAllPublicacoes()).thenReturn(List.of(p1));
        when(publicacaoService.getMinhaColaboracao(1)).thenReturn(new ArrayList<>());
        ResponseEntity teste = publicacaoController.getRespostasByUsuario(1);
        assertEquals(204, teste.getStatusCodeValue());
    }


    @Test
    @DisplayName("getRespostasByUsuario() deve trazer - publicacoes de um usuario ")
    void getRespostasByUsuarioDeveTrazer(){
        PublicacaoEntity p1 = new PublicacaoEntity();
        when(publicacaoService.findAllPublicacoes()).thenReturn(List.of(p1));
        when(publicacaoService.getMinhaColaboracao(1)).thenReturn(List.of(p1));
        ResponseEntity teste = publicacaoController.getRespostasByUsuario(1);
        assertEquals(200, teste.getStatusCodeValue());
    }


    @Test
    @DisplayName("updateStatus() - deve funcionar")
    void updateStatus(){
        PublicacaoEntity publicacao = new PublicacaoEntity();
        publicacao.setIdPublicacao(1);
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setEmail("paulo.santos@bandtec.com");
        when(publicacaoService.updateStatusById(1, 3)).thenReturn(publicacao);
        when(usuarioService.findUsuarioById(1)).thenReturn(usuario);
        ResponseEntity<PublicacaoEntity> teste = publicacaoController.updateStatus(1, 3);
        assertEquals(200, teste.getStatusCodeValue());

    }

    //metodo getRespostasByUsuario()
    @Test
    @DisplayName(("findByCategoria() - deve trazer uma publicacao"))
    void findByCategoriaTemCategoria(){
        PublicacaoEntity p1 = new PublicacaoEntity();
        CategoriaEntity c1 = new CategoriaEntity();
        when(categoriaService.getId(1)).thenReturn(c1);
        when(publicacaoService.getFiltroCategoria(1)).thenReturn(List.of(p1));
        ResponseEntity<List<PublicacaoEntity>> teste = publicacaoController.findByCategoria(1);
        assertEquals(200, teste.getStatusCodeValue());
    }

    @Test
    @DisplayName(("findByCategoria() - não deve trazer uma publicacao - nao existe com aquele id"))
    void findByCategoriaNaoTemCategoria(){
        PublicacaoEntity p1 = new PublicacaoEntity();
        CategoriaEntity c1 = new CategoriaEntity();
        when(categoriaService.getId(1)).thenReturn(c1);
        when(publicacaoService.getFiltroCategoria(1)).thenReturn(new ArrayList<>());
        ResponseEntity<List<PublicacaoEntity>> teste = publicacaoController.findByCategoria(1);
        assertEquals(204, teste.getStatusCodeValue());
    }

    @Test
    @DisplayName(("findByCategoria() - não deve trazer uma publicacao - nao exste o id da categoria"))
    void findByCategoriaNaoExisteIdCategoria(){
        PublicacaoEntity p1 = new PublicacaoEntity();
        when(categoriaService.getId(1)).thenReturn(null);
        ResponseEntity<List<PublicacaoEntity>> teste = publicacaoController.findByCategoria(1);
        assertEquals(404, teste.getStatusCodeValue());
    }

}