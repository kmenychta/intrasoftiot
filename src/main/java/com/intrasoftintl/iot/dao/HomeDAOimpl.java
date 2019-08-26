package com.intrasoftintl.iot.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.intrasoftintl.iot.entity.Home;
import com.intrasoftintl.iot.entity.Room;

@Repository
public class HomeDAOimpl implements HomeDAO {

	private EntityManager entityManager;

	// constructor injection
	@Autowired
	public HomeDAOimpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public Home findById(int hid) {
		Session session = entityManager.unwrap(Session.class);
		Home h = session.get(Home.class, hid);
		return h;
	}

	@Override
	public List<Room> findRooms(int hid) {
		Home h = findById(hid);
		return h.getRooms();
	}

	@Override
	public void removeMultipleRooms(int hid, int[] rids) {
		for (int i = 0; i < rids.length; i++) {
			Room r = findRoom(hid, rids[i]);
			removeRoom(hid, r);
		}

	}

	@Override
	public void addRoom(int hid, Room r) {
		Home h = findById(hid);
		h.addRoom(r);
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(h);
	}

	@Override
	public void removeRoom(int hid, Room r) {
		Home h = findById(hid);
		h.removeRoom(r);
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(h);
	}

	@Override
	public Room findRoom(int hid, int rid) {
		for (Room r : findRooms(hid)) {
			if (rid == r.getId()) {
				return r;
			}
		}
		return null;
	}

	@Override
	@Transactional
	public List<List<Object>> findDevicesByRoom(int hid) {
		List<List<Object>> devices = new ArrayList<List<Object>>();
		int i = 0;
		for (Room r : findRooms(hid)) {
			List<Object> list = new ArrayList<Object>();
			list.add(r);
			devices.add(i, list);
			i++;
		}
		return devices;

	}

}
