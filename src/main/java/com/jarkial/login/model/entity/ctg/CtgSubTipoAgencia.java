package com.jarkial.login.model.entity.ctg;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "CTG_SUBTIPO_AGENCIA")
public class CtgSubTipoAgencia {
    
    @Id
    @Column(name = "ctg_stagn_id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    /*@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdCtgSubTipoAgenciaGenerator")
    @SequenceGenerator(allocationSize = 1, name = "IdCtgSubTipoAgenciaGenerator", sequenceName = "ctg_stagn_id_seq")*/
    @Getter @Setter
    private Long ctgSubTipoAgenciaId;

    @Column(name = "ctg_stagn_activo", nullable=false, length=1)
    @Getter @Setter
    private String ctgSubTipoAgenciaActivo;

    @Column(name = "ctg_stagn_descripcion", nullable=false, length=100)
    @Getter @Setter
    private String ctgSubTipoAgenciaDescripcion;

    @ManyToOne
    @JoinColumn(name = "ctg_tagn_id", nullable= false)
    @Getter @Setter
    private CtgTipoAgencia ctgTipoAgencia;
}