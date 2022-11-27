package br.com.studenton.sptechforum.utilities;

import br.com.studenton.sptechforum.domain.UsuarioEntity;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ArquivoCsv {


    public static String lerRetornarArquivo() {
        Path currentRelativePath = Paths.get("");

        String url = System.getProperty("user.home") + "\\all-list-alunos-sptech-forum.csv";
        Path filePath = Path.of(url);
        String content = null;

        try {
            content = Files.readString(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }


    public static Object gravaArquivoCsv(PilhaObj<UsuarioEntity> lista, String nomeArq) {


        FileWriter arq = null;
        Formatter saida = null;
        nomeArq += ".csv";

        boolean deuRuim = false;

        try {
            arq = new FileWriter(new File(System.getProperty("user.home") + "\\" + nomeArq));
            saida = new Formatter(arq);
        } catch (IOException erro) {
            System.out.println("Erro ao abrir o arquivo");
            System.exit(1);
        }

        try {
            saida.format("%s;%s;%s;%s;%s\n","RA", "Nome", "E-mail", "Curso", "Semestre");
            while (!lista.isEmpty()) {
                UsuarioEntity aluno = lista.pop();
                saida.format("%s;%s;%s;%s;%d\n", aluno.getRa(), aluno.getNome(), aluno.getEmail(), aluno.getCurso(),
                        aluno.getSemestre());
            }
        } catch (FormatterClosedException erro) {
            System.out.println("Erro ao gravar o arquivo");
            deuRuim = true;
        } finally {
            saida.close();
            try {
                arq.close();
            } catch (IOException erro) {
                System.out.println("Erro ao fechar o arquivo");
                deuRuim = true;
            }
            if (deuRuim) {
                System.exit(1);
            }
        }
        return null;
    }
}