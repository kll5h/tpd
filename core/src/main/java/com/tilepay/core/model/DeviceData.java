package com.tilepay.core.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="DEVICE_DATA")
public class DeviceData implements Serializable {

    private static final long serialVersionUID = 4516758999623102580L;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="device_id")
    private Device deviceId;

	@Column(name="date_time")
    private Date time;

    private String data;

	public Device getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Device deviceId) {
		this.deviceId = deviceId;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
