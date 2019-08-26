package com.intrasoftintl.iot.dao;

import java.util.List;
import com.intrasoftintl.iot.entity.Home;
import com.intrasoftintl.iot.entity.Room;

public interface HomeDAO {
	
	public Home findById(int hid);
	
	public List<Room> findRooms(int hid);
	
	public Room findRoom(int hid,int rid);
	
	public void removeMultipleRooms(int hid,int[] rids);
	
	public void addRoom(int hid,Room r);
	
	public void removeRoom(int hid,Room r);
	
	public List<List<Object>> findDevicesByRoom(int hid);
	
	
}
