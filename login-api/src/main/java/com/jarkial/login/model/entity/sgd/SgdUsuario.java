package com.jarkial.login.model.entity.sgd;

import com.jarkial.login.model.entity.ctg.CtgAgencia;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "SGD_USUARIO")
public class SgdUsuario {
    
    @Id
    @Column(name = "sgd_usu_id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    /*@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdSgdUsuarioGenerator")
    @SequenceGenerator(allocationSize = 1, name = "IdSgdUsuarioGenerator", sequenceName = "sgd_usu_id_seq")*/
    @Getter @Setter
    private Long sgdUsuarioId;

    @Column(name = "sgd_usu_activo", nullable=false, length=1)
    @Getter @Setter
    private String sgdUsuarioActivo;

    @Column(name = "sgd_usu_clave", nullable=false, length=100)
    @Getter @Setter
    private String sgdUsuarioClave;

    @Column(name = "sgd_usu_correo_electronico", nullable=false, length=100)
    @Getter @Setter
    private String sgdUsuarioEmail;

    @Column(name = "sgd_usu_primer_apellido", nullable=false, length=20)
    @Getter @Setter
    private String sgdUsuarioPrimerApellido;

    @Column(name = "sgd_usu_primer_nombre", nullable=false, length=20)
    @Getter @Setter
    private String sgdUsuarioPrimerNombre;

    @Column(name = "sgd_usu_segundo_apellido", length=20)
    @Getter @Setter
    private String sgdUsuarioSegundoApellido;

    @Column(name = "sgd_usu_segundo_nombre", length=20)
    @Getter @Setter
    private String sgdUsuarioSegundoNombre;

    @ManyToOne
    @JoinColumn(name = "ctg_agn_id", nullable= false)
    @Getter @Setter
    private CtgAgencia ctgAgencia;

    @ManyToOne
    @JoinColumn(name = "sgd_usu_id_padre")
    @Getter @Setter
    private SgdUsuario sgdUsuarioPadre;

    @Column(name = "sgd_usu_usuario", nullable=false, length=50)
    @Getter @Setter
    private String sgdUsuarioUsername;

    @Column(name = "sgd_usu_ejecutivo", length=1)
    @Getter @Setter
    private String sgdUsuarioEjecutivo;

    @Column(name = "sgd_usu_cambiar_clave", length=1)
    @Getter @Setter
    private String sgdUsuarioCambiarClave;

    @Column(name = "sgd_usu_bloqueado", length=1)
    @Getter @Setter
    private String sgdUsuarioBloqueado;

    @Column(name = "sgd_usu_login_intento")
    @Getter @Setter
    private Integer sgdUsuarioLoginIntento;

    @Column(name = "sgd_usu_fecha_expiracion_clave", length=20)
    @Getter @Setter
    private String sgdUsuarioFechaExpiracion;

    @Column(name = "sgd_usu_telefono", length=20)
    @Getter @Setter
    private String sgdUsuarioTelefono;

    @Column(name = "sgd_usu_extencion", length=20)
    @Getter @Setter
    private String sgdUsuarioExtencion;

    @Column(name = "sgd_usu_celular", length=20)
    @Getter @Setter
    private String sgdUsuarioCelular;

    @Column(name = "sgd_usu_codigo", length=100)
    @Getter @Setter
    private String sgdUsuarioCodigo;

    @Column(name = "sgd_usu_supervisor", length=1)
    @Getter @Setter
    private String sgdUsuarioSupervisor;

    @Column(name = "sgd_usu_logueado")
    @Getter @Setter
    private Integer sgdUsuarioLogueado;
}