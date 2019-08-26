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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="home")
public class Home {

	@Id
	@Column(name="homeid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int homeid;
	
	@Column(name="address")
	private String address;
	
	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="homeid")
	@JsonIgnore
	private List<Room> rooms;

	public Home(int homeid, String address, List<Room> rooms) {
		this.homeid = homeid;
		this.address = address;
		this.rooms = rooms;
	}
	
	public Home() {
		
	}

	public int getHomeid() {
		return homeid;
	}

	public void setHomeid(int homeid) {
		this.homeid = homeid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	@Override
	public String toString() {
		return "Home [homeid=" + homeid + ", address=" + address + ", rooms=" + rooms + "]";
	}

	public void addRoom(Room r) {
		if ((r!=null)&&(!rooms.contains(r))) {
			rooms.add(r);
		}
		
	}

	public void removeRoom(Room r) {
		if ((r!=null)&&(!rooms.contains(r))) {
			rooms.remove(r);
		}
		
	}
	
	
}
