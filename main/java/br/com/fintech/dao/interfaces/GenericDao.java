package br.com.fintech.dao.interfaces;

import java.util.List;

public interface GenericDao<T> {
    void cadastrar(T entity);
    
    List<T> listar();
}
