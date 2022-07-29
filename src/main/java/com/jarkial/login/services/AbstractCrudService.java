package com.jarkial.login.services;

import java.util.List;

public interface AbstractCrudService<C, I> {
    
    public abstract List<C> findAll() throws Exception;

    public abstract C findById(I id) throws Exception;

    public abstract C update(C entity) throws Exception;

    public abstract boolean deleteById(I id) throws Exception;

}