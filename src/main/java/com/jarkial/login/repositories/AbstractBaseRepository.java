package com.jarkial.login.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface AbstractBaseRepository<C, I extends Serializable>
extends JpaRepository<C, I> {
    
}
