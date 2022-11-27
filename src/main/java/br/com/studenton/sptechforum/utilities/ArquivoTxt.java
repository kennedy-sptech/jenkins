package br.com.studenton.sptechforum.utilities;

import br.com.studenton.sptechforum.domain.UsuarioEntity;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ArquivoTxt {

    public String gravaArquivoTxt(PilhaObj<UsuarioEntity> lista, String nomeArq) {

        StringBuilder str = new StringBuilder();

        nomeArq += ".txt";

        int contaRegistroCorpo = 0;

        String header = "00ALUNOS";

        header += LocalDateTime.now(ZoneId.of("America/Sao_Paulo"))
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

        header += "01";

        str.append(header);
        str.append("\n");

        String corpo = "";

        while(!lista.isEmpty()){

            corpo = "01";

            corpo += String.format("%-8.8s", lista.peek().getRa());

            corpo += String.format("%-45.45s", lista.peek().getCurso());

            corpo += String.format("%-60.60s", lista.peek().getNome());

            corpo += String.format("%-55.55s", lista.peek().getEmail());

            corpo += String.format("%-20.20s", lista.peek().pegarSenha());

            corpo += String.format("%1d", lista.peek().getSemestre());

            if(lista.peek().getSemestre() == 1){

                corpo += String.format("%-10.10s", "Calouro");

            }else{

                corpo += String.format("%-10.10s", "Veterano");

            }

            corpo += String.format("%-20.20s", "SpTech School");

            corpo += String.format("%-40.40s", "secretaria@sptech.school");

            corpo += String.format("%14s", "82529127000167");

            contaRegistroCorpo++;

            str.append(corpo);
            str.append("\n");

            lista.pop();
        }

        String trailer = "02";
        trailer += String.format("%05d", contaRegistroCorpo);

        str.append(trailer);
        str.append("\n");

        return String.valueOf(str);

    }

    public List<UsuarioEntity> leArquivoTxt(String nomeArq) {

        BufferedReader entrada = null;

        String registro, tipoRegistro;
        String ra, curso, nome, email, tipoAluno, nomeInstituicao, emailInstituicao, cnpj, senha;
        Integer semestre, fkAcesso;

        UsuarioEntity usuario = null;

        int contaRegDadoLido = 0;

        int qtdRegDadoGravado;

        List<UsuarioEntity> listaLida = new ArrayList<>();

        try {

            entrada = new BufferedReader(new FileReader(System.getProperty("user.home") + "\\" + nomeArq));

        }

        catch (IOException erro) {

            System.out.println("Erro na abertura do arquivo: " + erro);

        }

        try {

            registro = entrada.readLine();

            while (registro != null) {

                tipoRegistro = registro.substring(0,2);

                if (tipoRegistro.equals("00")) {

                    System.out.println("Tipo do arquivo: " +
                            registro.substring(2,8));

                    System.out.println("Data e hora de gravação: " +
                            registro.substring(8,26));

                    System.out.println("Versão do documento de layout: " +
                            registro.substring(26,28));

                } else if (tipoRegistro.equals("02")) {

                    qtdRegDadoGravado= Integer.parseInt(registro.substring(2,7));

                    if (contaRegDadoLido == qtdRegDadoGravado) {

                        System.out.println("Quantidade de registros lidos compatível " +
                                "com a quantidade de registros gravados");

                    }
                    else {

                        System.out.println("Quantidade de registros lidos incompatível " +
                                "com a quantidade de registros gravados");

                    }

                } else if (tipoRegistro.equals("01")) {

                    ra = registro.substring(2, 10).trim();

                    curso = registro.substring(10, 55).trim();

                    nome = registro.substring(55, 115).trim();

                    email = registro.substring(115, 170).trim();

                    senha = registro.substring(170,190).trim();

                    semestre = Integer.valueOf(registro.substring(190, 191));

                    tipoAluno = registro.substring(191, 201).trim();

                    nomeInstituicao = registro.substring(201, 221).trim();

                    emailInstituicao = registro.substring(221, 241);

                    cnpj = registro.substring(241, 255);

                    contaRegDadoLido++;

                    if(tipoAluno.equalsIgnoreCase("Calouro")){

                        fkAcesso = 1;

                    }else{

                        fkAcesso = 2;

                    }

                    listaLida.add (new UsuarioEntity(ra, nome, email, senha, curso, semestre, fkAcesso));

                    usuario = new UsuarioEntity(ra, nome, email,senha, curso, semestre, fkAcesso);

                } else {

                    System.out.println("Tipo de registro inválido");

                }

                registro = entrada.readLine();

            }
            entrada.close();

        }

        catch (IOException erro) {

            System.out.println("Erro ao ler arquivo: " + erro);

        }

        System.out.println("\nLista lida do arquivo:");

        return listaLida;

    }

}
