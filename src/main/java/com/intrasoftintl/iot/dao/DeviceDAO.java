package com.intrasoftintl.iot.dao;

import java.util.List;

import com.intrasoftintl.iot.entity.*;

public interface DeviceDAO {
	
	public List<Device> findAll();
	
	public Device findById(int id);
	
	public void save(Device device);
	
	public void deleteById(int id);
	
	public void deleteMultiple(int[] ids);
	
	public List<Person> findUsers(int id);
	
	public void addUser(int id,Person p);
	
	public void removeUser(int id,Person p);
	
	public void changeStatus(int did);

	public void removeRoomDevices(int rid);

}
