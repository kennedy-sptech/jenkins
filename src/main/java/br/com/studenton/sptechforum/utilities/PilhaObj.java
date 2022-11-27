package br.com.studenton.sptechforum.utilities;

import br.com.studenton.sptechforum.domain.UsuarioEntity;

import java.util.Optional;

public class PilhaObj<T> {

    int topo;
    T[] pilha;

    public PilhaObj(Integer capacidadePilha) {
        this.topo = -1;
        pilha = (T[]) new Object[capacidadePilha];
    }

    public boolean isEmpty(){

        return topo == -1;

    }

    public boolean isFull(){

        return topo + 1 == pilha.length;

    }

    public void push(T info){

        if(isFull()){

            System.out.println("Pilha Cheia");
            return;
        }

        pilha[++topo] = info;

    }

    public void multiPush(PilhaObj<T> aux){

        while (!aux.isEmpty()){

            if(topo + aux.topo < pilha.length-1){

                pilha[++topo] = aux.pop();

            }else{

                System.out.println("A pilha encheu!");
                return;

            }

        }

    }

    public T pop(){

        if(isEmpty()){

            System.out.println("Pilha vazia");
            return null;
        }

        return pilha[topo--];

    }

    public PilhaObj<T> multiPop (int n){

        PilhaObj pilhaAuxiliar = new PilhaObj<T>(n);

        if(n > pilha.length){

            return null;

        }

        while (n != 0){

            pilhaAuxiliar.push(pilha[topo--]);
            n--;

        }

        return pilhaAuxiliar;

    }

    public T removeIndice(int n){

        PilhaObj<T> p = new PilhaObj<T>(n);

        p = multiPop(n);

        T teste = pop();

        multiPush(p);

        return teste;

    }

    public T peek(){

        if(isEmpty()){

            return null;

        }

        return pilha[topo];

    }

    public void exibe(){

        if(isEmpty()){

            System.out.println("Pilha vazia");
            return;

        }

        StringBuilder str = new StringBuilder();

        for(int i = topo; i != -1; i--){

            str.append("[");
            str.append(pilha[i]);
            str.append("]");
            str.append("\n");

        }

        System.out.println(str);

    }
}
