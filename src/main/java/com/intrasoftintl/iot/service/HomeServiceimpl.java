package com.intrasoftintl.iot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intrasoftintl.iot.dao.DeviceDAO;
import com.intrasoftintl.iot.dao.HomeDAO;
import com.intrasoftintl.iot.entity.Device;
import com.intrasoftintl.iot.entity.Home;
import com.intrasoftintl.iot.entity.Room;

@Service
public class HomeServiceimpl implements HomeService {
	
	
	@Autowired
	private HomeDAO homeDAO;
	
	@Autowired
	private DeviceDAO deviceDAO;
	
	public HomeServiceimpl(HomeDAO homeDAO,DeviceDAO deviceDAO) {
		this.homeDAO=homeDAO;
		this.deviceDAO=deviceDAO;
	}
	@Override
	public Home findById(int hid) {
		return homeDAO.findById(hid);
	}

	@Override
	public List<Room> findRooms(int hid) {
		 return homeDAO.findRooms(hid);
	}

	@Override
	public Room findRoom(int hid, int rid) {
		return homeDAO.findRoom(hid, rid);
	}
	
	@Override
	public List<Device> findHomeDevices(int hid){
		List<Device> devices=new ArrayList<Device>();
		List<Room> rooms=findRooms(hid);
		for(Room r:rooms) {
			for(Device d:r.getDevices()) {
				devices.add(d);
			}
		}
		return devices;
	}
	
	@Override
	public Device findHomeDevice(int hid,int did){
		List<Device> devices=findHomeDevices(hid);
		Device d=deviceDAO.findById(did);
		if (devices.contains(d)) {
			return d;
		}
		return null;
	}


	@Override
	public void removeMultipleRooms(int hid, int[] rids) {
		homeDAO.removeMultipleRooms(hid, rids);

	}

	@Override
	public void addRoom(int hid, int rid) {
		Room r=homeDAO.findRoom(hid, rid);
		homeDAO.addRoom(hid, r);

	}

	@Override
	public void removeRoom(int hid, int rid) {
		Room r=homeDAO.findRoom(hid, rid);
		homeDAO.removeRoom(hid, r);
	}
	@Override
	public List<List<Object>> findDevicesByroom(int hid) {
		return homeDAO.findDevicesByRoom(hid);
	}

}
