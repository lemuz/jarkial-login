package com.jarkial.login.model.entity.ctg;

import com.jarkial.login.model.entity.sgd.SgdUsuario;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "CTG_RUTA_ADJUNTOS")
public class CtgRutaAdjuntos {

    @Id
    @Column(name = "ctg_ruta_id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    /*@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdCtgRutaAdjuntosGenerator")
    @SequenceGenerator(allocationSize = 1, name = "IdCtgRutaAdjuntosGenerator", sequenceName = "ctg_ruta_id_seq")*/
    @Getter @Setter
    private Long ctgRutaAdjuntosId;

    @Column(name = "ctg_ruta_nombre", length=25)
    @Getter @Setter
    private String ctgRutaAdjuntosNombre;
    
    @Column(name = "ctg_ruta_valor", length=100)
    @Getter @Setter
    private String ctgRutaAdjuntosValor;

    @Column(name = "ctg_ruta_max_size", length=20)
    @Getter @Setter
    private String ctgRutaAdjuntosMaxSize;

    @Column(name = "ctg_ruta_fecha_actu")
    @Getter @Setter
    private Timestamp ctgRutaAdjuntosFechaActualizacion;
    
    @ManyToOne
    @JoinColumn(name = "sgd_usu_id")
    @Getter @Setter
    private SgdUsuario sgdUsuario;

}
