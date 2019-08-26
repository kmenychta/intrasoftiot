package com.intrasoftintl.iot.service;

import java.util.List;

import com.intrasoftintl.iot.entity.Device;
import com.intrasoftintl.iot.entity.Home;
import com.intrasoftintl.iot.entity.Room;

public interface HomeService {
	 
	public Home findById(int hid);
	
	public List<Room> findRooms(int hid);
	
	public Room findRoom(int hid,int rid);
	
	public void removeMultipleRooms(int hid,int[] rids);
	
	public void addRoom(int hid,int rid);
	
	public void removeRoom(int hid,int rid);
	
	public List<Device> findHomeDevices(int hid);
	
	public Device findHomeDevice(int hid,int did);
	
	public List<List<Object>> findDevicesByroom(int hid);
	
}
