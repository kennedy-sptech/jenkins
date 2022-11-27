package br.com.studenton.sptechforum.service;

import br.com.studenton.sptechforum.domain.CurtidaEntity;
import br.com.studenton.sptechforum.repository.CurtidaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurtidaService {
    final CurtidaRepository curtidaRepository;

    public CurtidaService(CurtidaRepository curtidaRepository) {
        this.curtidaRepository = curtidaRepository;
    }

    public Boolean curtirOuDescurtir(Integer idPublicacao, Integer idUsuario){
            CurtidaEntity curtida = curtidaRepository.findCurtidaEntitiesByFkPublicacaoAndFkUsuario(idPublicacao, idUsuario);
        if (curtida == null){
            CurtidaEntity curtidaEntity = new CurtidaEntity();
            curtidaEntity.setFkPublicacao(idPublicacao);
            curtidaEntity.setFkUsuario(idUsuario);
            curtidaRepository.save(curtidaEntity);
            return true;
        }
        curtidaRepository.deleteCurtida(idPublicacao, idUsuario);
        return false;
    }



    public List<CurtidaEntity> getAll(){

        return curtidaRepository.getCurtidaEntityByIdCurtida(2);
    }

    public Boolean verificarCurtida(Integer idUsu, Integer idPub){
        CurtidaEntity curtida = curtidaRepository.findCurtidaEntitiesByFkPublicacaoAndFkUsuario(idPub, idUsu);
        return curtida == null ? false : true;
    }

}