package com.intrasoftintl.iot.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "device")
public class Device {

	@Id
	@Column(name = "deviceid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;

	@Column(name = "name")
	protected String name;

	@Column(name = "status")
	private String status;

	@Column(name = "information")
	protected String information;

	@Column(name = "ip_address")
	protected String ip_address;

	@Column(name = "api_key")
	protected String api_key;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinTable(name = "user_devices", joinColumns = @JoinColumn(name = "device_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	@JsonIgnore
	protected List<Person> users;

	@Column(name = "roomid")
	protected int roomId;

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })
	@JoinColumn(name = "typeid", nullable = false, referencedColumnName = "typeid")
	private DeviceType deviceType;

	public Device() {
	}

	public Device(int id, String name, String status, String information, String ip_address, String api_key,
			List<Person> users, int roomId, DeviceType deviceType) {
		this.id = id;
		this.name = name;
		this.status = status;
		this.information = information;
		this.ip_address = ip_address;
		this.api_key = api_key;
		this.users = users;
		this.roomId = roomId;
		this.deviceType = deviceType;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public String getApi_key() {
		return api_key;
	}

	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}

	public List<Person> getUsers() {
		return users;
	}

	public void setUsers(List<Person> users) {
		this.users = users;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	@Override
	public String toString() {
		return "Device [id=" + id + ", name=" + name + ", deviceType=" + deviceType + ", status=" + status
				+ ", information=" + information + ", ip_address=" + ip_address + ", api_key=" + api_key + ", users="
				+ users + ", roomId=" + roomId + "]";
	}

	public void addUser(Person p) {
		users.add(p);
	}

	public void removeUser(Person p) {
		users.remove(p);
	}

	public void changeStatus() {
		if (status.toLowerCase().contentEquals("on")) {
			status = "off";
		} else {
			status = "on";
		}
	}
	@JsonIgnore
	public boolean isValid() {
		if((name!=null)&&(status!=null)&&(information!=null)&&(ip_address!=null)&&(api_key!=null)){
			return true;
		}
		return false;
	}

}
