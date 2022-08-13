package com.jarkial.login.model.entity.ctg;

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
@Table(name = "CTG_TIPO_AGENCIA")
public class CtgTipoAgencia {

    @Id
    @Column(name = "ctg_tagn_id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    /*@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdCtgTipoAgenciaGenerator")
    @SequenceGenerator(allocationSize = 1, name = "IdCtgTipoAgenciaGenerator", sequenceName = "ctg_tagn_id_seq")*/
    @Getter @Setter
    private Long ctgTipoAgenciaId;

    @Column(name = "ctg_tagn_activo", nullable=false, length=1)
    @Getter @Setter
    private String ctgTipoAgenciaActivo;

    @Column(name = "ctg_tagn_descripcion", nullable=false, length=100)
    @Getter @Setter
    private String ctgTipoAgenciaDescripcion;
    
}
