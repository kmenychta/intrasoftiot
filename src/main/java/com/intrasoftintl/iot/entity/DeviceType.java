package com.intrasoftintl.iot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;

@Entity
@Table(name = "device_type")
public class DeviceType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int typeid;

	@Column(name = "name")
	private String name;

	public DeviceType() {

	}

	public DeviceType(int typeid, String name) {
		this.typeid = typeid;
		this.name = name;
	}

	public int getTypeid() {
		return typeid;
	}

	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "DeviceType [typeid=" + typeid + ", name=" + name + "]";
	}

}
