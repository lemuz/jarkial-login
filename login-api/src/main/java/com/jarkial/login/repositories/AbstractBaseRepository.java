package com.jarkial.login.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractBaseRepository<C, I extends Serializable>
extends JpaRepository<C, I> {
    
}
