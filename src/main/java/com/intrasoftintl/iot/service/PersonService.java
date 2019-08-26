package com.intrasoftintl.iot.service;

import java.util.List;

import com.intrasoftintl.iot.entity.Device;
import com.intrasoftintl.iot.entity.Person;
import com.intrasoftintl.iot.entity.Room;

public interface PersonService {
	
	public List<Person> findAll();
	
	public Person findById(int id);
	
	public void deleteById(int id);
	
	public List<Device> findDevices(int id);
	
	public boolean addDevice(int person_id,int device_id);
	
	public boolean removeDevice(int person_id,int device_id);
	
	public Device findDevice(int person_id,int device_id);
	
	public List<Room> findRooms(int id);
	
	public List<Person> findUsersIfAdmin(int id);

	public boolean addRoom(int person_id,int room_id);
	
	public boolean removeRoom(int person_id,int room_id);
	
	public Room findRoom(int person_id,int room_id);
	
	public List<Device> findRoomDevices(int pid,int rid);
	
	public Person findPersonByEmail(String email);
	
	public void saveUser(Person p);

	public Device findRoomDevice(int id, int rid, int did);

	public List<Room> findHomeRooms(int id);
	
	public Room findHomeRoom(int id,int rid);
	
	public int findHome(int id);

	public void removeRoom(int rid);

	public long findIfEmailExists(String email);
}
