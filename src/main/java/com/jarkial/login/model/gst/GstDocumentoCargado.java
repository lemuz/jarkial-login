package com.jarkial.login.model.gst;

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
@Table(name = "GST_DOCUMENTO_CARGADO")
public class GstDocumentoCargado {
    
    @Id
    @Column(name = "gst_dcar_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdGstDocumentoCargadoGenerator")
    @SequenceGenerator(allocationSize = 1, name = "IdGstDocumentoCargadoGenerator", sequenceName = "gst_dcar_id_seq")
    @Getter @Setter
    private Long gstDocumentoCargadoId;

    @Column(name = "gst_dcar_descripcion", nullable=false, length=100)
    @Getter @Setter
    private String gstDocumentoCargadoDescripcion;

    @Column(name = "gst_dcar_fecha", nullable=false, length=15)
    @Getter @Setter
    private String gstDocumentoCargadoFecha;

    @ManyToOne
    @JoinColumn(name = "ctg_cat_id")
    @Getter @Setter
    private CtgCatalogo gstDocumentoCargadoTipoDocumento;

    @Column(name = "gst_dcar_extencion", length=10)
    @Getter @Setter
    private String gstDocumentoCargadoExtencion;
}