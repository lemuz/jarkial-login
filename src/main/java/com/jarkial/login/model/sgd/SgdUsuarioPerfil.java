package com.jarkial.login.model.sgd;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import com.jarkial.login.model.ctg.CtgCatalogo;

@Entity
@Table(name = "SGD_USUARIO_PERFIL")
public class SgdUsuarioPerfil {

    @Id
    @Column(name = "sgd_usu_per_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdSgdUsuarioPerfilGenerator")
    @SequenceGenerator(allocationSize = 1, name = "IdSgdUsuarioPerfilGenerator", sequenceName = "sgd_usu_per_id_seq")
    @Getter @Setter
    private Long sgdUsuarioPerfilId;

    @ManyToOne
    @JoinColumn(name = "sgd_per_id", nullable=false)
    @Getter @Setter
    private CtgCatalogo sgdPerfil;
    
    @ManyToOne
    @JoinColumn(name = "sgd_usu_id", nullable=false)
    @Getter @Setter
    private SgdUsuario sgdUsuario;
}