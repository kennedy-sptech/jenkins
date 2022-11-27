package br.com.studenton.sptechforum.controller;

import br.com.studenton.sptechforum.domain.CurtidaEntity;
import br.com.studenton.sptechforum.domain.PublicacaoEntity;
import br.com.studenton.sptechforum.domain.UsuarioEntity;
import br.com.studenton.sptechforum.repository.CurtidaRepository;
import br.com.studenton.sptechforum.service.CurtidaService;
import br.com.studenton.sptechforum.service.PublicacaoService;
import br.com.studenton.sptechforum.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {CurtidaController.class})
class CurtidaControllerTest {

    @Autowired
    CurtidaController curtidaController;

    @MockBean
    CurtidaService curtidaService;

    @MockBean
    CurtidaRepository curtidaRepository;

    @MockBean
    PublicacaoService publicacaoService;

    @MockBean
    UsuarioService usuarioService;


    @Test
    @DisplayName("getAll() deve retornar 204 - não tem curtidas ")
    void getAllCurtidasSemRetorno() {
        when(curtidaService.getAll()).thenReturn(new ArrayList<>());
        ResponseEntity<List<CurtidaEntity>> curtidas = curtidaController.all();
        assertEquals(204, curtidas.getStatusCodeValue());
        assertNull(curtidas.getBody());
    }

    @Test
    @DisplayName("getAll() deve retornar 200 - tem curtidas")
    void getAllCurtidasComRetorno() {
        CurtidaEntity c1 = new CurtidaEntity();
        CurtidaEntity c2 = new CurtidaEntity();
        when(curtidaService.getAll()).thenReturn(
                List.of(c1, c2)
        );
        ResponseEntity<List<CurtidaEntity>> curtidas = curtidaController.all();
        assertEquals(200, curtidas.getStatusCodeValue());
        assertEquals(2, curtidas.getBody().size());
        assertEquals(c1, curtidas.getBody().get(0));
    }

    @Test
    @DisplayName("curtir() deve curtir, pois ainda não esta curtido")
    void curtidaDeveCurtir() {
        when(curtidaService.curtirOuDescurtir(1, 2)).thenReturn(true);
        ResponseEntity<Boolean> curtida = curtidaController.curtida(2, 1);
        assertEquals(201, curtida.getStatusCodeValue());
        assertEquals(true, curtida.getBody());
    }

    @Test
    @DisplayName("curtir() deve descurtir, pois já esta curtido")
    void curtidaNãoDeveCurtir() {
        when(curtidaService.curtirOuDescurtir(1, 2)).thenReturn(false);
        ResponseEntity<Boolean> curtida = curtidaController.curtida(2, 1);
        assertEquals(200, curtida.getStatusCodeValue());
        assertEquals(false, curtida.getBody());
    }

    @Test
    void getCurtidaById(){
        when(curtidaService.verificarCurtida(1, 2)).thenReturn(true);
        ResponseEntity<Boolean> curtida = curtidaController.getCurtidaById(1, 2);
        assertEquals(200, curtida.getStatusCodeValue());
        assertEquals(true, curtida.getBody());
    }


}