package com.intrasoftintl.iot.dao;

import java.util.List;

import com.intrasoftintl.iot.entity.*;

public interface RoomDAO {

	public List<Room> findAll();
	
	public Room findById(int id);
	
	public void save(Room room);
	
	public void deleteById(int id);
	
	public void deleteAll();
	
	public List<Device> findDevices(int id);
	
	public Device findDevice(int room_id, int device_id);
	
	public void addDevice(int id,Device d);
	
	public void removeDevice(int id,Device d);
	
	public List<Person> findUsers(int id);
	
	public Person findUser(int room_id, int person_id);
	
	public void addUser(int id,Person p);
	
	public void removeUser(int id,Person p);
}
