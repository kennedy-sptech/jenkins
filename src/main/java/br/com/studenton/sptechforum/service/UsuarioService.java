package br.com.studenton.sptechforum.service;

import br.com.studenton.sptechforum.domain.UsuarioEntity;
import br.com.studenton.sptechforum.mapper.UsuarioFilaRespostaBody;
import br.com.studenton.sptechforum.repository.UsuarioRepository;
import br.com.studenton.sptechforum.utilities.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    final UsuarioRepository usuarioRepository;
    static PilhaObj<UsuarioEntity> pilhaObj;
    static List<UsuarioEntity> lista;
    final ArquivoTxt arquivoTxt;
    final Agendador agendador;

    public UsuarioService(UsuarioRepository usuarioRepository) {

        this.usuarioRepository = usuarioRepository;
        lista = new ArrayList<>();

        lista.addAll(usuarioRepository.findAll());

        this.arquivoTxt = new ArquivoTxt();
        this.agendador = new Agendador(usuarioRepository);

        inserirUsuariosNaPilhaObj();

        agendador.run();

    }

    private void inserirUsuariosNaPilhaObj(){

        pilhaObj = new PilhaObj<>(lista.size());

        for (UsuarioEntity usuario : lista) {

            pilhaObj.push(usuario);

        }

    }

    public List<UsuarioEntity> findUsuarioEntityByCheckEmailTrue() {
        return usuarioRepository.findUsuarioEntityByCheckEmailTrue();
    }

    public List<UsuarioEntity> findAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public UsuarioEntity findUsuarioById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public UsuarioEntity findUsuarioByRaAndSenha(String senha, String ra){
        UsuarioEntity usuario = usuarioRepository.findUsuarioEntityByRaAndSenha(ra, senha);
        if (usuario != null){
            usuario.setAutenticado(true);
            usuarioRepository.updateAutenticado(true, usuario.getRa());
            return usuario;
        }
        return null;
    }

    public UsuarioEntity findUsuarioByRa(String ra){
        UsuarioEntity usuario = usuarioRepository.findUsuarioEntitiesByRa(ra);
        if (usuario != null){
            usuario.setAutenticado(false);
            usuarioRepository.updateAutenticado(false, usuario.getRa());
            return usuario;
        }
        return null;
    }

    public List<UsuarioEntity> findAutenticados(){
        return usuarioRepository.findAllByAutenticadoTrue();
    }


    public List<UsuarioFilaRespostaBody> respostaFilaObj(){

        List<UsuarioFilaRespostaBody> list = new ArrayList<>();

        for(UsuarioEntity usuarioDaVez: agendador.getListUsuarioAdicionados()){

            list.add(new UsuarioFilaRespostaBody(usuarioDaVez, "Usuario Adicionado na fila"));

        }

        for(UsuarioEntity usuarioDaVez: agendador.getListUsuarioJaNaFila()){

            list.add(new UsuarioFilaRespostaBody(usuarioDaVez, "Usuario Ja está na fila"));

        }

        for(UsuarioEntity usuarioDaVez: agendador.getListUsuarioJaNoBanco()){

            list.add(new UsuarioFilaRespostaBody(usuarioDaVez, "Usuario já está cadastrado no banco"));

        }

        agendador.limparListas();

        return list;

    }


    public Object exportCsv() {

        inserirUsuariosNaPilhaObj();
        ArquivoCsv.gravaArquivoCsv(pilhaObj, "all-list-alunos-sptech-forum");

        return ArquivoCsv.lerRetornarArquivo();
    }

    public String exportTxt(){

        inserirUsuariosNaPilhaObj();
        return arquivoTxt.gravaArquivoTxt(pilhaObj, "documento-layout-sptech-forum");

    }

    public boolean gravarTxt(String nomeArq){

        if(agendador.getFilaObj().isFull()){

            return false;

        }

        for(UsuarioEntity usuarioDaVez: arquivoTxt.leArquivoTxt(nomeArq)){

            if(agendador.inserirFilaObj(usuarioDaVez)){

                lista.add(usuarioDaVez);

            }
        }

        return true;

    }

    public boolean existById(Integer id){

        return usuarioRepository.existsById(id);

    }

    public boolean trocarCheck(Integer id){
        UsuarioEntity usuario = usuarioRepository.findUsuarioEntitiesByIdUsuario(id);
        if (usuario.getCheckEmail()){
            usuario.setCheckEmail(false);
            usuarioRepository.save(usuario);
            return false;
        }
        usuario.setCheckEmail(true);
        usuarioRepository.save(usuario);
        return true;
    }

    public UsuarioEntity trocarPerfil(Integer id, String link){
        UsuarioEntity usuario = usuarioRepository.findUsuarioEntitiesByIdUsuario(id);
        usuario.setFotoPerfil(link);
        usuarioRepository.save(usuario);
        return usuario;
    }

    public boolean mudarSenha(Integer id, String senha, String novaSenha){
        UsuarioEntity usuario = usuarioRepository.findUsuarioEntitiesByIdUsuarioAndSenha(id, senha);
        if (usuario == null){
            return false; //significa que a senha esta incorreta
        }
        usuario.setSenha(novaSenha);
        usuarioRepository.save(usuario);
        return true;

    }

    public boolean esqueciSenha(String email, String ra, String novaSenha){
        UsuarioEntity usuario = usuarioRepository.findUsuarioEntitiesByRaAndEmail(ra, email);
        if (usuario == null){
            return false; //significa que não existe esse email e senha
        }
        usuario.setSenha(novaSenha);
        usuarioRepository.save(usuario);
        return true;
    }

}
