package br.com.studenton.sptechforum.observer;

import br.com.studenton.sptechforum.domain.PublicacaoEntity;

public interface StatusChangeObserver {
    void statusChange(Integer status, PublicacaoEntity publicacao);
}
