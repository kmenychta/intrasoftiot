package com.intrasoftintl.iot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.intrasoftintl.ACUnit.service.ACUnitService;
import com.intrasoftintl.iot.dao.DeviceDAO;
import com.intrasoftintl.iot.dao.DeviceTypeDAO;
import com.intrasoftintl.iot.dao.PersonDAO;
import com.intrasoftintl.iot.dao.RoomDAO;
import com.intrasoftintl.iot.entity.Device;
import com.intrasoftintl.iot.entity.DeviceType;
import com.intrasoftintl.iot.entity.Person;
import com.intrasoftintl.iot.entity.Room;

@Service
public class DeviceServiceimpl implements DeviceService {

	private DeviceDAO deviceDAO;
	
	private PersonDAO personDAO;
	
	private DeviceTypeDAO deviceTypeDAO;
	
	private RoomDAO roomDAO;
	
	private ACUnitService acunitservice;
	
	@Autowired
	public DeviceServiceimpl(DeviceDAO deviceDAO,PersonDAO personDAO,DeviceTypeDAO deviceTypeDAO,RoomDAO roomDAO,ACUnitService acunitservice) {
		this.deviceDAO=deviceDAO;
		this.personDAO=personDAO;
		this.deviceTypeDAO=deviceTypeDAO;
		this.roomDAO=roomDAO;
		this.acunitservice=acunitservice;
	}

	@Override
	public List<Device> findAll() {
		return deviceDAO.findAll();
	}
	
	@Override
	public Device findById(int id) {
		return deviceDAO.findById(id);
	}
	

	@Override
	public List<Object> findInterfaceById(int id) {
		Device d=deviceDAO.findById(id);
		List<Object> device = new ArrayList<Object>();
		device.add(d);
		switch (d.getDeviceType().getTypeid()) {
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			device.add(acunitservice.findById(id));
			break;
		case 5:
			break;
		}
		return device;
	}

	@Override
	public void save(Device device) {
		deviceDAO.save(device);
		
	}

	@Override
	public void deleteById(int id) {
		deviceDAO.deleteById(id);
		
	}

	@Override
	public List<Person> findUsers(int id) {
		return deviceDAO.findUsers(id);
	}

	@Override
	public void removeUser(int device_id,int person_id) {
		Person person=personDAO.findById(device_id);
		deviceDAO.addUser(device_id,person);
		
	}

	@Override
	public void addUser(int device_id,int person_id) {
		Person person=personDAO.findById(device_id);
		deviceDAO.removeUser(device_id,person);
		
	}

	@Override
	public Device changeStatus(int did) {
		deviceDAO.changeStatus(did);
		return deviceDAO.findById(did);
		
	}

	@Override
	public List<DeviceType> getDeviceTypes() {
		return deviceTypeDAO.findAll();
	}

	@Override
	public void moveDevice(int did, int rid) {
		Device device=deviceDAO.findById(did);
		int previousrid=device.getRoomId();
		Room previousroom=roomDAO.findById(previousrid);
		previousroom.removeDevice(device);
		device.setRoomId(rid);
		deviceDAO.save(device);
		Room newroom=roomDAO.findById(rid);
		newroom.addDevice(device);
		
	}

	@Override
	public void removeRoomDevices(int rid) {
		deviceDAO.removeRoomDevices(rid);
		
	}
}
