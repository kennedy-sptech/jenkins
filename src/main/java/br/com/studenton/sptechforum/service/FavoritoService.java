package br.com.studenton.sptechforum.service;

import br.com.studenton.sptechforum.domain.FavoritoEntity;
import br.com.studenton.sptechforum.domain.PublicacaoEntity;
import br.com.studenton.sptechforum.repository.FavoritoRepository;
import br.com.studenton.sptechforum.repository.PublicacaoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoritoService {

    final FavoritoRepository favoritoRepository;

    final PublicacaoRepository publicacaoRepository;

    public FavoritoService(FavoritoRepository favoritoRepository, PublicacaoRepository publicacaoRepository) {
        this.favoritoRepository = favoritoRepository;
        this.publicacaoRepository = publicacaoRepository;
    }

    public List<FavoritoEntity> getAll(Integer idUsuario){
        return favoritoRepository.findAllByFkUsuario(idUsuario);
    }

    public boolean favoritar(Integer idPublicacao, Integer idUsuario){
        FavoritoEntity favorito = favoritoRepository.findFavoritoEntitiesByFkPublicacaoAndFkUsuario(idPublicacao, idUsuario);
        if (favorito == null){
            FavoritoEntity criarFavorito = new FavoritoEntity();
            criarFavorito.setFkPublicacao(idPublicacao);
            criarFavorito.setFkUsuario(idUsuario);
            favoritoRepository.save(criarFavorito);
            return true;
        }
        favoritoRepository.deleteFavorito(idPublicacao, idUsuario);
        return false;
    }

    public boolean desfavoritar(Integer idPublicacao, Integer idUsuario){

        if (favoritoRepository.countByFkPublicacaoAndFkUsuario(idPublicacao, idUsuario) == 1){
            favoritoRepository.deleteFavorito(idPublicacao, idUsuario);

            return true;
        }

        return false;
    }

    public List<PublicacaoEntity> getSalvos(Integer idUsuario){
        List<FavoritoEntity> listaFavoritos = favoritoRepository.findAllByFkUsuario(idUsuario);
        List<PublicacaoEntity> lista = new ArrayList<>();
        for (FavoritoEntity daVez : listaFavoritos){
            lista.add(publicacaoRepository.findPublicacaoEntitiesByIdPublicacao(daVez.getFkPublicacao()));
        }
        return lista;
    }

    public Boolean verificarSalvo(Integer idUsu, Integer idPu){
        FavoritoEntity favorito = favoritoRepository.findFavoritoEntitiesByFkPublicacaoAndFkUsuario(idPu, idUsu);
        return favorito == null ? false : true;

    }
}