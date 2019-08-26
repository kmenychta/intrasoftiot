package com.intrasoftintl.iot.service;

import java.util.List;

import com.intrasoftintl.iot.entity.*;

public interface RoomService {
	
	public List<Room> findAll();
	
	public Room findById(int id);
	
	public void save(Room room);
	
	public void deleteById(int id);
	
	public void addDevice(int room_id, int device_id);
	
	public void removeDevice(int room_id, int device_id);
	
	public List<Device> findDevices(int id);
	
	public Device findDevice(int room_id,int device_id);
	
	public void addUser(int room_id, int person_id);
	
	public void removeUser(int room_id, int person_id);
	
	public List<Person> findUsers(int id);
	
	public Person findUser(int room_id,int person_id);
}
