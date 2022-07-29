package com.jarkial.login.model.entity.sgd;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jarkial.login.model.entity.ctg.CtgCatalogo;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "SGD_ROL_PERFIL")
public class SgdRolPerfil {
    
    @Id
    @Column(name = "sgd_rol_per_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdSgdRolPerfilGenerator")
    @SequenceGenerator(allocationSize = 1, name = "IdSgdRolPerfilGenerator", sequenceName = "sgd_rol_per_id_seq")
    @Getter @Setter
    private Long sgdRolPerfilId;

    @ManyToOne
    @JoinColumn(name = "sgd_per_id", nullable= false)
    @Getter @Setter
    private CtgCatalogo sgdPerfil;

    @ManyToOne
    @JoinColumn(name = "sgd_rol_id", nullable= false)
    @Getter @Setter
    private CtgCatalogo sgdRol;
}