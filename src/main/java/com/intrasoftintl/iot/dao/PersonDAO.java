package com.intrasoftintl.iot.dao;

import java.util.List;

import com.intrasoftintl.iot.entity.*;

public interface PersonDAO {
	
	public List<Person> findAll();
	
	public Person findById(int id);
	
	public void save(Person person);
	
	public void deleteById(int id);
	
	public void deleteAll();
	
	public List<Device> findDevices(int id);
	
	public List<Person> findUsersIfAdmin(int id);
	
	public Device findDevice(int person_id, int device_id);

	public int logIn(String email,String password);
	
	public boolean addDevice(int id,Device d);
	
	public boolean removeDevice(int id,Device d);
	
	public long findIfEmailExists(String email);
	
	public Person findPersonByEmail(String email);
	
	public List<Room> findRooms(int id);
	
	public Room findRoom(int person_id, int room_id);
	
	public boolean addRoom(int id,Room room);
	
	public boolean removeRoom(int id,Room room);
	
	public List<Device> findRoomDevices(int id,int rid);

	public Device findRoomDevice(int id, int rid, int did);

	List<Room> findHomeRooms(int id);
	
	Room findHomeRoom(int id,int rid);

	public int findHome(int id);

	public void removeRoom(int rid);
}
