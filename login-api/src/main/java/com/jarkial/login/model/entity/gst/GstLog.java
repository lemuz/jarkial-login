package com.jarkial.login.model.entity.gst;

import java.sql.Date;

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
@Table(name = "GST_LOGGER")
public class GstLog {
 
    @Id
    @Column(name = "gst_logg_id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    /*@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdGstLogGenerator")
    @SequenceGenerator(allocationSize = 1, name = "IdGstLogGenerator", sequenceName = "gst_conflog_id_seq")*/
    @Getter @Setter
    private Long gstConfLogId;

    @Column(name = "gst_logg_ip", nullable=false, length=20)
    @Getter @Setter
    private String gstConfLogIp;

    @Column(name = "gst_logg_usuario", nullable=false, length=20)
    @Getter @Setter
    private String gstConfLogUsuario;

    @Column(name = "gst_logg_clase", nullable=false, length=20)
    @Getter @Setter
    private String gstConfLogClase;

    @Column(name = "gst_logg_tipo_transaccion", nullable=false, length=200)
    @Getter @Setter
    private String gstConfLogTipoTransacion;

    @Column(name = "gst_logg_fecha_transaccion", nullable=false)
    @Getter @Setter
    private Date gstConfLogFechaTransacion;
}