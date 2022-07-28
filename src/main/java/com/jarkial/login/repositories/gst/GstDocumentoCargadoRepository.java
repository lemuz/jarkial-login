package com.jarkial.login.repositories.gst;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jarkial.login.model.gst.GstDocumentoCargado;

@Repository
public interface GstDocumentoCargadoRepository extends JpaRepository<GstDocumentoCargado, Long>{
    
}
