package br.com.studenton.sptechforum.utilities;

import java.util.ArrayList;
import java.util.List;

public class ListaObj <T>{

    T [] vetor;
    int indice;

    public ListaObj(int tam) {
        this.vetor = (T[]) new Object[tam];
        this.indice = indice;
    }

    public void adicionar(T obj){

        if(indice == vetor.length){

            System.out.println("Lista cheia");
            return;
        }

        vetor[indice] = obj;
        indice++;

    }

    public void adicionarLista(List<T> lista){

        for(int i =0 ;i < lista.size(); i++){

            vetor[i] = lista.get(i);

        }

        indice = lista.size();

    }

    public List<T> exibirTodos(){

        List<T> lista = new ArrayList<>();

        for(int i = 0; i < indice; i++){

            lista.add(vetor[i]);

        }

        return lista;
    }

    public void exibir(int indiceEscolhido){

        if(!(indiceEscolhido >= 0 && indiceEscolhido < indice)){

            System.out.println("Indice Inválido");
            return;
        }

        StringBuilder str = new StringBuilder();

        str.append("\n");
        str.append(vetor[indiceEscolhido]);

        System.out.println(str);

    }

    public int buscar(T obj){

        for(int i = 0; i< indice; i++){

            if(obj.equals(vetor[i])){

                return i;

            }
        }

        return -1;
    }

    public boolean remove(int indiceEscolhido){

        if(!(indiceEscolhido> 0 && indiceEscolhido < indice)){

            System.out.println("Indice Inválido");

        }else{

            for(int i = indiceEscolhido; i < indice; i++){

                if((i+1) < indice){

                    vetor[i] = vetor[i+1];
                    return true;
                }
            }

        }
        return false;
    }

    public boolean remove(T obj){

        if(buscar(obj) == -1){

            System.out.println("Objeto não encontrado");

        }else{

            remove(buscar(obj));
            return true;
        }

        return false;
    }

    public int getTamanho(){

        return indice;

    }

    public T getElemento(int indice){

        return vetor[indice];

    }

    public void limpar(){

        indice = 0;

    }
}
