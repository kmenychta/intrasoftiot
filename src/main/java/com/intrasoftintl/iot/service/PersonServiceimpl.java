package com.intrasoftintl.iot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.intrasoftintl.iot.dao.DeviceDAO;
import com.intrasoftintl.iot.dao.PersonDAO;
import com.intrasoftintl.iot.dao.RoomDAO;
import com.intrasoftintl.iot.entity.Device;
import com.intrasoftintl.iot.entity.Person;
import com.intrasoftintl.iot.entity.Room;

@Service
public class PersonServiceimpl implements PersonService {
	
	@Autowired
	private PersonDAO personDAO;
	
	@Autowired
	private DeviceDAO deviceDAO;
	
	@Autowired
	private RoomDAO roomDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public PersonServiceimpl(PersonDAO personDAO, DeviceDAO deviceDAO,RoomDAO roomDAO) {
		this.personDAO=personDAO;
		this.deviceDAO=deviceDAO;
		this.roomDAO=roomDAO;
	}
	
	@Override
	public List<Person> findAll() {
		return personDAO.findAll();
	}

	@Override
	public Person findById(int id) {
		return personDAO.findById(id);
	}


	@Override
	public void deleteById(int id) {
		personDAO.deleteById(id);

	}

	@Override
	public List<Device> findDevices(int id) {
		return personDAO.findDevices(id);
	}

	@Override
	public boolean addDevice(int person_id, int device_id) {
		Device device=deviceDAO.findById(device_id);
		return personDAO.addDevice(person_id,device);
		
	}

	@Override
	public boolean removeDevice(int person_id, int device_id) {
		Device device=deviceDAO.findById(device_id);
		return personDAO.removeDevice(person_id,device);
		
	}

	@Override
	public Device findDevice(int person_id, int device_id) {
		return personDAO.findDevice(person_id, device_id);
	}

	@Override
	public List<Room> findRooms(int id) {
		return personDAO.findRooms(id);
	}

	@Override
	public boolean addRoom(int person_id, int room_id) {
		Room room=roomDAO.findById(room_id);
		return personDAO.addRoom(person_id,room);
		
	}

	@Override
	public boolean removeRoom(int person_id, int room_id) {
		Room room=roomDAO.findById(room_id);
		return personDAO.removeRoom(person_id,room);
		
	}

	@Override
	public Room findRoom(int person_id, int room_id) {
		return personDAO.findRoom(person_id, room_id);
	}

	@Override
	public List<Device> findRoomDevices(int pid, int rid) {
		return personDAO.findRoomDevices(pid, rid);
	}
	

	@Override
	public long findIfEmailExists(String email) {
		return personDAO.findIfEmailExists(email);
	}

	@Override
	public void saveUser(Person p) {
		p.setPassword(passwordEncoder.encode(p.getPassword()));
		p.setRole("1");
		personDAO.save(p);
	}

	@Override
	public Device findRoomDevice(int id, int rid, int did) {
		return personDAO.findRoomDevice(id,rid,did);
	}

	@Override
	public List<Person> findUsersIfAdmin(int id) {
		return personDAO.findUsersIfAdmin(id);
	}

	@Override
	public List<Room> findHomeRooms(int id) {
		return personDAO.findHomeRooms(id);
	}
	
	@Override
	public Room findHomeRoom(int id,int rid) {
		return personDAO.findHomeRoom(id, rid);
	}

	@Override
	public int findHome(int id) {
		return personDAO.findHome(id);
	}

	@Override
	public void removeRoom(int rid) {
		personDAO.removeRoom(rid);
	}

	@Override
	public Person findPersonByEmail(String email) {
		return personDAO.findPersonByEmail(email);
	}	

}
