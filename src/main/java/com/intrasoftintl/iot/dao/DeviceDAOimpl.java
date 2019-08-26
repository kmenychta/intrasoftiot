package com.intrasoftintl.iot.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intrasoftintl.iot.entity.Device;
import com.intrasoftintl.iot.entity.Person;

@Repository
public class DeviceDAOimpl implements DeviceDAO {

	private EntityManager entityManager;

	@Autowired
	public DeviceDAOimpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public List<Device> findAll() {
		Session session = entityManager.unwrap(Session.class);
		List<Device> devices = session.createQuery("from Device", Device.class).getResultList();
		return devices;
	}

	@Override
	@Transactional
	public Device findById(int id) {
		Session session = entityManager.unwrap(Session.class);
		Device d = session.get(Device.class, id);
		return d;

	}

	@Override
	@Transactional
	public void save(Device device) {

		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(device);
	}

	@Override
	@Transactional
	public void deleteById(int id) {

		Session session = entityManager.unwrap(Session.class);
		Device d = findById(id);
		if (d == null) {
			throw new RuntimeException("Device with id: " + id + "not found!");
		} else {
			Query query = session.createQuery("delete from Device where deviceid=:did").setParameter("did", id);
			query.executeUpdate();
		}

	}

	@Override
	public List<Person> findUsers(int id) {
		List<Person> users = findById(id).getUsers();
		return users;
	}

	@Override
	public void deleteMultiple(int[] ids) {
		for (int i = 0; i < ids.length; i++) {
			deleteById(ids[i]);
		}

	}

	@Override
	@Transactional
	public void addUser(int id, Person p) {
		Device d = findById(id);
		d.addUser(p);
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(d);

	}

	@Override
	@Transactional
	public void removeUser(int id, Person p) {
		Device d = findById(id);
		d.removeUser(p);
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(d);

	}

	@Override
	@Transactional
	public void changeStatus(int did) {
		Session session = entityManager.unwrap(Session.class);
		Device d = findById(did);
		d.changeStatus();
		session.saveOrUpdate(d);
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public void removeRoomDevices(int rid) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("from Device where roomid=:roomId").setParameter("roomId", rid);
		List<Device> devices=query.getResultList();
		for(Device d:devices) {
			System.out.println(d);
			deleteById(d.getId());
		}
		
	}


}
