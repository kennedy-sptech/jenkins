package br.com.studenton.sptechforum.service;

import br.com.studenton.sptechforum.domain.FavoritoEntity;
import br.com.studenton.sptechforum.domain.PublicacaoEntity;
import br.com.studenton.sptechforum.domain.RespostaEntity;
import br.com.studenton.sptechforum.mapper.AtualizarDuvidaRequestBody;
import br.com.studenton.sptechforum.mapper.PublicacaoPostRequestBody;
import br.com.studenton.sptechforum.repository.FavoritoRepository;
import br.com.studenton.sptechforum.repository.PublicacaoRepository;
import br.com.studenton.sptechforum.repository.RespostaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class PublicacaoService {

    final PublicacaoRepository publicacaoRepository;
    final RespostaRepository respostaRepository;

    final FavoritoRepository favoritoRepository;

    public PublicacaoService(PublicacaoRepository publicacaoRepository, RespostaRepository respostaRepository, FavoritoRepository favoritoRepository) {
        this.publicacaoRepository = publicacaoRepository;
        this.respostaRepository = respostaRepository;
        this.favoritoRepository = favoritoRepository;
    }

    public List<PublicacaoEntity> findAllPublicacoes() {
        List<PublicacaoEntity> todas = publicacaoRepository.findAllByOrderByIdPublicacaoDesc();
        List<PublicacaoEntity> verificadas = new ArrayList<>();

        for (PublicacaoEntity daVez : todas){
            if (daVez.getStatus() != 1){
                verificadas.add(daVez);
            }
        }
        return verificadas;
    }

    public List<PublicacaoEntity> findAllPerguntas() {
        List<PublicacaoEntity> todas = publicacaoRepository.findAllByOrderByIdPublicacaoDesc();
        List<PublicacaoEntity> verificadas = new ArrayList<>();

        for (PublicacaoEntity daVez : todas){
            if (daVez.getTipoPublicacao() == 2 && daVez.getStatus() == 1){
                verificadas.add(daVez);
            }

        }
        return verificadas;
    }

    public PublicacaoEntity findPublicacaoById(Integer id) {
        return publicacaoRepository.findPublicacaoEntitiesByIdPublicacao(id);
    }

    public PublicacaoEntity realizarPublicacao(PublicacaoPostRequestBody publicacaoPostRequestBody){
        return publicacaoRepository.save(new PublicacaoEntity(publicacaoPostRequestBody));
    }

    public List<PublicacaoEntity> findDuvidasByIdUsuario(Integer id) {
        return publicacaoRepository.findPublicacaoEntitiesByFkUsuario(id);
    }

    public PublicacaoEntity atualizarDuvida(AtualizarDuvidaRequestBody duvidaAtualizada){
        PublicacaoEntity duvida = publicacaoRepository
                .findPublicacaoEntitiesByIdPublicacao(duvidaAtualizada.getIdPublicacao());
        duvida.setTitulo(duvidaAtualizada.getTitulo());
        duvida.setTexto(duvidaAtualizada.getTexto());
        duvida.setFkCategoria(duvidaAtualizada.getFkCategoria());
        publicacaoRepository.save(duvida);
        return publicacaoRepository.save(duvida);
    }

    public List<PublicacaoEntity> getAllOrdenado(){
        List<PublicacaoEntity> publicacaoEntities =publicacaoRepository.findAll();
        List<PublicacaoEntity> verificadas = new ArrayList<>();
        publicacaoEntities.sort(Comparator.comparing(PublicacaoEntity::getCountCurtidas).reversed());
        for (PublicacaoEntity daVez: publicacaoEntities){
            if (daVez.getStatus() != 1){
                verificadas.add(daVez);
            }
        }
        return verificadas;
    }

    public List<PublicacaoEntity> getMinhaColaboracao(int idUsuario){

        List<PublicacaoEntity> listaN = publicacaoRepository.findAllByOrderByIdPublicacaoDesc();
        List<PublicacaoEntity> lista = new ArrayList<>();
        for (PublicacaoEntity daVez: listaN){
            if(daVez.getTipoPublicacao() == 1){
                if (daVez.getFkUsuario() == idUsuario){
                    lista.add(daVez);
                }
            }else {
                if (daVez.getStatus() != 1){
                    if (daVez.getFkUsuario() == idUsuario){
                        lista.add(daVez);
                    }else {
                        List<RespostaEntity> resposta = daVez.getRespostasByIdPublicacao();
                        for (RespostaEntity resDaVez : resposta){
                            if (resDaVez.getFkUsuario() == idUsuario){
                                lista.add(daVez);
                            }
                        }
                    }
                }
            }

        }
        return lista;
    }

    @Transactional
    public void deletePublicacaoById(Integer id) {
        publicacaoRepository.deleteByIdPublicacao(id);
    }

    public PublicacaoEntity updateStatusById(Integer id, Integer status) {
        PublicacaoEntity publicacaoEntity = publicacaoRepository.findById(id).orElse(null);
        publicacaoEntity.setStatus(status);
        publicacaoRepository.save(publicacaoEntity);
        return publicacaoEntity;
    }

    public boolean existById(Integer id){

        return publicacaoRepository.existsById(id);

    }

    public List<PublicacaoEntity> getFiltroCategoria(Integer idCategoria){
        List<PublicacaoEntity> lista = publicacaoRepository.findAll();
        List<PublicacaoEntity> listaFiltrada = new ArrayList<>();
        for (PublicacaoEntity daVez : lista){
            if (daVez.getFkCategoria() == idCategoria && daVez.getStatus() == 3){
                listaFiltrada.add(daVez);
            }
        }
        return listaFiltrada;
    }


    public List<PublicacaoEntity> getByStatus(Integer status){
        List<PublicacaoEntity> lista = publicacaoRepository.findAllByOrderByIdPublicacaoDesc();
        List<PublicacaoEntity> listaStatus = new ArrayList<>();
        for (PublicacaoEntity daVez : lista){
            if (daVez.getStatus() == status){
                listaStatus.add(daVez);
            }
        }
        return listaStatus;
    }



}
