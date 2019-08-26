package com.intrasoftintl.iot.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class Person {
	 
	@Id
	@Column(name="userid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name")
	private String firstName;
	
	@Column(name="surname")
	private String lastName;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name="role")
	private String role;
	
	@Column(name="homeid")
	private int homeid;
	
	private String confirmpassword;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade= {CascadeType.PERSIST})
	@JoinTable(name="user_devices", joinColumns=@JoinColumn(name="user_id"),inverseJoinColumns=@JoinColumn(name="device_id"))
	private List<Device> devices;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade= {CascadeType.PERSIST})
	@JoinTable(name="user_rooms", joinColumns=@JoinColumn(name="user_id"),inverseJoinColumns=@JoinColumn(name="room_id"))
	private List<Room> rooms;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade= {CascadeType.PERSIST})
	@JoinTable(name="user_admins", joinColumns=@JoinColumn(name="admin_id"),inverseJoinColumns=@JoinColumn(name="user_id"))
	private List<Person> users;

	public Person(int id, String firstName, String lastName, String email, String password, String role,int homeid,
			List<Device> devices,List<Room> rooms) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.role = role;
		this.homeid=homeid;
		this.devices = devices;
		this.rooms=rooms;
	}

	public Person() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Device> getDevices() {
		return devices;
	}
	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}
	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}
	

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

	public int getHomeid() {
		return homeid;
	}

	public void setHomeid(int homeid) {
		this.homeid = homeid;
	}
	

	public List<Person> getUsers() {
		return users;
	}

	public void setUsers(List<Person> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", role=" + role + ", homeid=" + homeid + ", confirmpassword="
				+ confirmpassword + ", devices=" + devices + ", rooms=" + rooms + ", ]";
	}
	

	public void addDevice(Device device) {
		if ((device!=null)&&(!devices.contains(device))) {
			devices.add(device);
		}else {
			
		}
	}
	
	public void removeDevice(Device device) {

		if ((device!=null)&&(devices.contains(device))) {
			devices.remove(device);
		}
	}
	public void addRoom(Room room) {
		if ((room!=null)&&(!rooms.contains(room))) {
			rooms.add(room);
		}
	}
	
	public void removeRoom(Room room) {

		if ((room!=null)&&(rooms.contains(room))) {
			rooms.remove(room);
		}
	}
	
}
