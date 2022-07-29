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
@Table(name = "GST_LOG")
public class GstLog {
 
    @Id
    @Column(name = "gst_conflog_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdGstLogGenerator")
    @SequenceGenerator(allocationSize = 1, name = "IdGstLogGenerator", sequenceName = "gst_conflog_id_seq")
    @Getter @Setter
    private Long gstConfLogId;

    @Column(name = "gst_conflog_ip", nullable=false, length=20)
    @Getter @Setter
    private String gstConfLogIp;

    @Column(name = "gst_conflog_usuario", nullable=false, length=20)
    @Getter @Setter
    private String gstConfLogUsuario;

    @Column(name = "gst_conflog_clase", nullable=false, length=20)
    @Getter @Setter
    private String gstConfLogClase;

    @Column(name = "gst_conflog_tipo_transacion", nullable=false, length=200)
    @Getter @Setter
    private String gstConfLogTipoTransacion;

    @Column(name = "gst_conflog_fecha_transacion", nullable=false)
    @Getter @Setter
    private Date gstConfLogFechaTransacion;
}