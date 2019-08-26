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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="room")
public class Room {
	
	@Id
	@Column(name="roomid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="roomname")
	private String name;
	
	@Column(name="homeid")
	private int homeid;
	
	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="roomid")
	private List<Device> devices;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade= {CascadeType.PERSIST})
	@JoinTable(name="user_rooms", joinColumns=@JoinColumn(name="room_id"),inverseJoinColumns=@JoinColumn(name="user_id"))
	@JsonIgnore
	private List<Person> users;
	
	public Room() {
		
	}

	public Room(int id, String name,int homeid,List<Device> devices,List<Person> users) {
		this.id = id;
		this.name = name;
		this.homeid=homeid;
		this.devices=devices;
		this.users=users;
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
	
	
	public int getHomeid() {
		return homeid;
	}

	public void setHomeid(int homeid) {
		this.homeid = homeid;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public List<Person> getUsers() {
		return users;
	}

	public void setUsers(List<Person> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", name=" + name + ", homeid=" + homeid + ", devices=" + devices + ", users=" + users
				+ "]";
	}
	
	public void addDevice(Device device) {
		if ((device!=null)&&(!devices.contains(device))) {
			devices.add(device);
		}else {
			//TO DO
		}
	}
	
	public void removeDevice(Device device) {
		if ((device!=null)&&(devices.contains(device))) {
			devices.remove(device);
		}else {
			//TO DO
		}
	}
	
	public void addUser(Person person) {
		if ((person!=null)&&(!users.contains(person))) {
			users.add(person);
		}
	}
	
	public void removeUser(Person person) {
		if ((person!=null)&&(users.contains(person))) {
			users.remove(person);
		}
	}

}
