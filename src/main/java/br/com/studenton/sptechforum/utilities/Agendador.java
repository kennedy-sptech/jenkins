package br.com.studenton.sptechforum.utilities;

import br.com.studenton.sptechforum.domain.UsuarioEntity;
import br.com.studenton.sptechforum.repository.UsuarioRepository;

import java.util.*;

public class Agendador extends Thread {

    private Calendar cal;
    private Date timeoRun;
    private FilaObj<UsuarioEntity> filaObj;
    private UsuarioRepository usuarioRepository;
    private List<UsuarioEntity> listUsuarioAdicionados;
    private List<UsuarioEntity> listUsuarioJaNaFila;
    private List<UsuarioEntity> listUsuarioJaNoBanco;

    public Agendador(UsuarioRepository usuarioRepository) {

        this.listUsuarioAdicionados = new ArrayList<>();
        this.listUsuarioJaNaFila = new ArrayList<>();
        this.listUsuarioJaNoBanco = new ArrayList<>();

        this.cal = Calendar.getInstance();
        this.filaObj = new FilaObj<>(100);
        this.usuarioRepository = usuarioRepository;

        cal.set(Calendar.HOUR_OF_DAY,00);
        cal.set(Calendar.MINUTE,00);

        timeoRun = cal.getTime();

    }

    @Override
    public void run() {

        System.out.println("=".repeat(20));
        System.out.println("Horário de iniciação do job: "+ timeoRun);
        System.out.println("=".repeat(20));

        new Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {

                System.out.println("=".repeat(20));
                System.out.println("Executando o job" );
                System.out.println("Ele executará de novo daqui 24hrs");
                System.out.println("=".repeat(20));

                if(filaObj.isEmpty()){

                    System.out.println();
                    System.out.println("Fila vazia...");
                    System.out.println();

                }else {

                    while (!filaObj.isEmpty()) {

                        System.out.println();
                        System.out.println("Inserindo um aluno no banco.");
                        listUsuarioAdicionados.add(filaObj.peek());
                        usuarioRepository.save(filaObj.poll());

                    }

                }
            }
        },timeoRun,86400000); //86400000 = 24hrs
    }

    public FilaObj<UsuarioEntity> getFilaObj() {
        return filaObj;
    }

    public boolean inserirFilaObj(UsuarioEntity usuario){

        if(!filaObj.isEmpty()){

            for(int i =0; i< filaObj.getTamanho(); i++) {

                if (filaObj.peek().getRa().equals(usuario.getRa())) {

                    System.out.println("Esse aluno já está na fila");
                    listUsuarioJaNaFila.add(usuario);
                    return false;

                }
            }
        }

        if(usuarioRepository.existsByRa(usuario.getRa())){

            System.out.println("Esse aluno já está cadastrado");
            listUsuarioJaNoBanco.add(usuario);
            return false;

        }


        try{

            filaObj.insert(usuario);

        }catch (IllegalStateException e){

            System.out.println("A fila está cheia");
            return false;

        }catch (Exception e){

            System.out.println("Houve um problema na validação do usuario");
            System.out.println("ERRO: " + e);
            return false;

        }
        listUsuarioAdicionados.add(usuario);
        return true;

    }

    public List<UsuarioEntity> getListUsuarioAdicionados() {
        return listUsuarioAdicionados;
    }

    public List<UsuarioEntity> getListUsuarioJaNaFila() {
        return listUsuarioJaNaFila;
    }

    public List<UsuarioEntity> getListUsuarioJaNoBanco() {
        return listUsuarioJaNoBanco;
    }

    public void limparListas(){

        this.listUsuarioAdicionados = new ArrayList<>();
        this.listUsuarioJaNaFila = new ArrayList<>();
        this.listUsuarioJaNoBanco = new ArrayList<>();

    }
}
