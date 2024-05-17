package com.jarkial.login.model.entity.sgd;

import com.jarkial.login.model.entity.ctg.CtgCatalogo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "SGD_USUARIO_PERFIL")
public class SgdUsuarioPerfil {

    @Id
    @Column(name = "sgd_usu_per_id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    /*@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdSgdUsuarioPerfilGenerator")
    @SequenceGenerator(allocationSize = 1, name = "IdSgdUsuarioPerfilGenerator", sequenceName = "sgd_usu_per_id_seq")*/
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