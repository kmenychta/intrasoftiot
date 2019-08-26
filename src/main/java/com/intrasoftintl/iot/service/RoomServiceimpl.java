package com.intrasoftintl.iot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intrasoftintl.iot.dao.DeviceDAO;
import com.intrasoftintl.iot.dao.PersonDAO;
import com.intrasoftintl.iot.dao.RoomDAO;
import com.intrasoftintl.iot.entity.Device;
import com.intrasoftintl.iot.entity.Person;
import com.intrasoftintl.iot.entity.Room;

@Service
public class RoomServiceimpl implements RoomService {
	@Autowired
	private RoomDAO roomDAO;
	
	@Autowired
	private DeviceDAO deviceDAO;
	
	@Autowired
	private PersonDAO personDAO;
	

	public RoomServiceimpl(RoomDAO roomDAO, DeviceDAO deviceDAO,PersonDAO personDAO) {
		this.roomDAO=roomDAO;
		this.deviceDAO=deviceDAO;
		this.personDAO=personDAO;
		
	}
	@Override
	public List<Room> findAll() {
		return roomDAO.findAll();
	}

	@Override
	public Room findById(int id) {
		return roomDAO.findById(id);
	}

	@Override
	public void save(Room room) {
		roomDAO.save(room);
	}

	@Override
	public void deleteById(int id) {
		roomDAO.deleteById(id);

	}
	@Override
	public void addDevice(int room_id, int device_id) {
		Device device=deviceDAO.findById(device_id);
		roomDAO.findById(room_id).addDevice(device);
		
	}
	@Override
	public void removeDevice(int room_id, int device_id) {
		Device device=deviceDAO.findById(device_id);
		roomDAO.findById(room_id).removeDevice(device);
		
	}
	@Override
	public List<Device> findDevices(int id) {
		return roomDAO.findDevices(id);
	}
	@Override
	public Device findDevice(int room_id, int device_id) {
		return roomDAO.findDevice(room_id, device_id);
	}
	@Override
	public void addUser(int room_id, int person_id) {
		Person person=personDAO.findById(person_id);
		roomDAO.findById(room_id).addUser(person);
		
	}
	@Override
	public void removeUser(int room_id, int person_id) {
		Person person=personDAO.findById(person_id);
		roomDAO.findById(room_id).removeUser(person);
	}
	@Override
	public List<Person> findUsers(int id) {
		return roomDAO.findUsers(id);
	}
	@Override
	public Person findUser(int room_id, int person_id) {
		return roomDAO.findUser(room_id, person_id);
	}

}
