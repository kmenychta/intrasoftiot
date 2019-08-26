package com.intrasoftintl.iot.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.intrasoftintl.iot.entity.Device;
import com.intrasoftintl.iot.entity.Person;
import com.intrasoftintl.iot.entity.Room;

@Repository
public class RoomDAOimpl implements RoomDAO {

	private EntityManager entityManager;

	@Autowired
	public RoomDAOimpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public List<Room> findAll() {
		Session session = entityManager.unwrap(Session.class);
		List<Room> rooms = session.createQuery("from Room", Room.class).getResultList();
		return rooms;
	}

	@Override
	@Transactional
	public Room findById(int id) {
		Session session = entityManager.unwrap(Session.class);
		Room r = session.get(Room.class, id);
		return r;
	}

	@Override
	@Transactional
	public void save(Room room) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(room);

	}

	@Override
	@Transactional
	public void deleteById(int id) {
		Session session = entityManager.unwrap(Session.class);
		Room r = findById(id);
		if (r == null) {
			throw new RuntimeException("Room with id: " + id + "not found!");
		} else {
			Query query = session.createQuery("delete from Room where roomid=:rid").setParameter("rid", id);
			query.executeUpdate();
		}

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Device> findDevices(int id) {
		List<Device> devices = findById(id).getDevices();
		return devices;
	}

	@Override
	@Transactional
	public Device findDevice(int room_id, int device_id) {
		Session session = entityManager.unwrap(Session.class);
		Room r = session.get(Room.class, room_id);
		Device d = session.get(Device.class, device_id);
		if (r.getDevices().contains(d)) {
			return d;
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public void addDevice(int id, Device d) {
		Room r = findById(id);
		r.addDevice(d);
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(r);

	}

	@Override
	@Transactional
	public void removeDevice(int id, Device d) {
		Room r = findById(id);
		r.removeDevice(d);
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(r);

	}

	@Override
	public List<Person> findUsers(int id) {
		List<Person> users = findById(id).getUsers();
		return users;
	}

	@Override
	@Transactional
	public Person findUser(int room_id, int person_id) {
		Session session = entityManager.unwrap(Session.class);
		Person p = session.get(Person.class, person_id);
		Room r = session.get(Room.class, room_id);
		if (r.getUsers().contains(p)) {
			return p;
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public void addUser(int id, Person p) {
		Room r = findById(id);
		r.addUser(p);
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(r);
	}

	@Override
	@Transactional
	public void removeUser(int id, Person p) {
		Room r = findById(id);
		r.removeUser(p);
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(r);

	}

}
