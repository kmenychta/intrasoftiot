package com.intrasoftintl.iot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intrasoftintl.ACUnit.domain.ACUnit;
import com.intrasoftintl.ACUnit.domain.FanSpeed;
import com.intrasoftintl.ACUnit.domain.Mode;
import com.intrasoftintl.ACUnit.service.ACUnitService;
import com.intrasoftintl.CoffeeMaker.domain.CoffeeMaker;
import com.intrasoftintl.CoffeeMaker.domain.CoffeeMode;
import com.intrasoftintl.CoffeeMaker.service.CoffeeMakerService;
import com.intrasoftintl.Lock.domain.IsLocked;
import com.intrasoftintl.Lock.domain.Lock;
import com.intrasoftintl.Lock.service.LockService;
import com.intrasoftintl.SmartFridge.domain.SmartFridge;
import com.intrasoftintl.SmartFridge.service.SmartFridgeService;
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
	
	@Autowired
	private ACUnitService acunitservice;
	
	@Autowired
	private LockService lockservice;
	
	@Autowired
	private SmartFridgeService smartfridgeservice;
	
	//@Autowired
	//private CoffeeMakerService coffeemakerservice;
	
	@Autowired
	public DeviceServiceimpl(DeviceDAO deviceDAO,PersonDAO personDAO,DeviceTypeDAO deviceTypeDAO,RoomDAO roomDAO) {
		this.deviceDAO=deviceDAO;
		this.personDAO=personDAO;
		this.deviceTypeDAO=deviceTypeDAO;
		this.roomDAO=roomDAO;
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
			//device.add(coffeemakerservice.findById(id));
			break;
		case 2:
			device.add(smartfridgeservice.findById(id));
			break;
		case 3:
			device.add(lockservice.findById(id));
			break;
		case 4:
			device.add(acunitservice.findById(id));
			break;
		}
		return device;
	}

	@Override
	public void save(Device device) {
		deviceDAO.save(device);
		int id=device.getId();
		int typeid=device.getDeviceType().getTypeid();
		switch (typeid) {
		case 1:
			//coffeemakerservice.save(new CoffeeMaker(id,80,CoffeeMode.COFFEE,device));
			break;
		case 2:
			smartfridgeservice.save(new SmartFridge(id,2,-10,device));
			break;
		case 3:
			lockservice.save(new Lock(id,IsLocked.UNLOCKED,device));
			break;
		case 4:
			acunitservice.save(new ACUnit(id,25,Mode.COOL,FanSpeed.LOW,device));
			break;
		}
		
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
