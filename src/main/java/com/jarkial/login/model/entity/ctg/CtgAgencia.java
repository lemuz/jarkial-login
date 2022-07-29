package com.jarkial.login.model.entity.ctg;

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

@Entity
@Table(name = "CTG_AGENCIA")
public class CtgAgencia {
    
    @Id
    @Column(name = "ctg_agn_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdCtgAgenciaGenerator")
    @SequenceGenerator(allocationSize = 1, name = "IdCtgAgenciaGenerator", sequenceName = "ctg_agn_id_seq")
    @Getter @Setter
    private Long ctgAgenciaId;

    @Column(name = "ctg_agn_activo", nullable=false, length=1)
    @Getter @Setter
    private String ctgAgenciaActivo;

    @Column(name = "ctg_agn_codigo", nullable=false, length=10)
    @Getter @Setter
    private String ctgAgenciaCodigo;

    @Column(name = "ctg_agn_descripcion", nullable=false, length=100)
    @Getter @Setter
    private String ctgAgenciaDescripcion;

    @ManyToOne
    @JoinColumn(name = "ctg_stagn_id", nullable= false)
    @Getter @Setter
    private CtgSubTipoAgencia ctgSubTipoAgencia;

    @Column(name = "ctg_agn_atiende_otrs_agn", length=1)
    @Getter @Setter
    private String ctgAgenciaAtiendeOtrasAgencias;

    @Column(name = "ctg_agn_posee_comite", length=1)
    @Getter @Setter
    private String ctgAgenciaPoseeComite;
}