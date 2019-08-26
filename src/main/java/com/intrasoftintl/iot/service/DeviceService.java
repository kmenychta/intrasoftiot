package com.intrasoftintl.iot.service;

import java.util.List;

import com.intrasoftintl.iot.entity.Device;
import com.intrasoftintl.iot.entity.DeviceType;
import com.intrasoftintl.iot.entity.Person;

public interface DeviceService {
	
	public List<Device> findAll();
	
	public Device findById(int id);
	
	public List<Object> findInterfaceById(int id);
	
	public void save(Device device);
	
	public void deleteById(int id);
	
	public List<Person> findUsers(int id);
	
	public void removeUser(int device_id,int person_id);
	
	public void addUser(int device_id,int person_id);
	
	public Device changeStatus(int did);
	
	public List<DeviceType> getDeviceTypes();
	
	public void moveDevice(int did,int rid);

	public void removeRoomDevices(int rid);

}
