package br.com.studenton.sptechforum.service;

import br.com.studenton.sptechforum.domain.PublicacaoEntity;
import br.com.studenton.sptechforum.domain.RespostaEntity;
import br.com.studenton.sptechforum.mapper.ResponderPublicacaoResquestBody;
import br.com.studenton.sptechforum.repository.PublicacaoRepository;
import br.com.studenton.sptechforum.repository.RespostaRepository;
import br.com.studenton.sptechforum.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class RespostaService {

    final PublicacaoRepository publicacaoRepository;
    final RespostaRepository respostaRepository;
    final UsuarioRepository usuarioRepository;

    public RespostaService(PublicacaoRepository publicacaoRepository, RespostaRepository respostaRepository,
                           UsuarioRepository usuarioRepository) {
        this.publicacaoRepository = publicacaoRepository;
        this.respostaRepository = respostaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<RespostaEntity> getAllRespostas(){

        return respostaRepository.findAll();

    }

    public RespostaEntity respostaDuvida(ResponderPublicacaoResquestBody respostaBody, Integer id){

        PublicacaoEntity publicacao = publicacaoRepository.findPublicacaoEntitiesByIdPublicacao(id);
        RespostaEntity resposta = new RespostaEntity();

        resposta.setTexto(respostaBody.getTexto());

        resposta.setDataHora(LocalDateTime.now(ZoneId.of("America/Sao_Paulo"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:mm"))+".000");

        resposta.setFkPublicacao(id);

        resposta.setFkUsuario(respostaBody.getFkUsuario());

        resposta.setUsuarioByFkUsuario(usuarioRepository.findUsuarioEntitiesByIdUsuario(respostaBody.getFkUsuario()));

        publicacao.getRespostasByIdPublicacao().add(resposta);

        respostaRepository.save(resposta);

        return resposta;

    }

    public void deletarRespostaById(Integer id){

        respostaRepository.deleteByIdResposta(id);

    }

    public boolean existeById(Integer id){

        return respostaRepository.existsById(id);

    }

    public List<RespostaEntity> finByFkPublicacao(int idPublicacao){

        return respostaRepository.findAllByFkPublicacao(idPublicacao);
    }

    public void updateResposta(String texto, Integer idResposta){

        respostaRepository.atualizarRespostaById(idResposta, texto);

    }

}
