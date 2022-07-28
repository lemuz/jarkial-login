package com.jarkial.login.model.sgd;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "SGD_USUARIO_TOKEN")
public class SgdUsuarioToken {

    @Id
    @Column(name = "sgd_usu_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdSgdUsuarioGenerator")
    @SequenceGenerator(allocationSize = 1, name = "IdSgdUsuarioGenerator", sequenceName = "sgd_usu_id_seq")
    @Getter @Setter
    private Long sgdUsuarioId;

    @Column(name = "sgd_usu_clave")
    @Getter @Setter
    private String sgdUsuarioClave;

    @Column(name = "sgd_usu_correo_electronico")
    @Getter @Setter
    private String sgdUsuarioEmail;

    @Column(name = "sgd_usu_nombre")
    @Getter @Setter
    private String sgdUsuarioNombre;

    @Column(name = "sgd_usu_username")
    @Getter @Setter
    private String sgdUsuarioUsername;

    @Column(name = "sgd_usu_grant_type")
    @Getter @Setter
    private String sgdUsuarioGrantType;

    @Column(name = "sgd_usu_scope")
    @Getter @Setter
    private String sgdUsuarioScope;

    @Column(name = "sgd_usu_authorization")
    @Getter @Setter
    private String sgdUsuarioAuthorization;
    
}