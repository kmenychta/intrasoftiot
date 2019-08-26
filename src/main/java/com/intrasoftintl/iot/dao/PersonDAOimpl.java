package com.intrasoftintl.iot.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intrasoftintl.iot.entity.Device;
import com.intrasoftintl.iot.entity.Home;
import com.intrasoftintl.iot.entity.Person;
import com.intrasoftintl.iot.entity.Room;

@Repository
public class PersonDAOimpl implements PersonDAO {

	private EntityManager entityManager;

	// constructor injection
	@Autowired
	public PersonDAOimpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public List<Person> findAll() {
		Session session = entityManager.unwrap(Session.class);
		List<Person> users = session.createQuery("from Person", Person.class).getResultList();
		return users;
	}

	@Override
	@Transactional
	public Person findById(int id) {

		Session session = entityManager.unwrap(Session.class);
		Person p = session.get(Person.class, id);
		return p;
	}

	@Override
	@Transactional
	public void save(Person person) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(person);

	}

	@Override
	@Transactional
	public void deleteById(int id) {
		Session session = entityManager.unwrap(Session.class);
		Person p = findById(id);
		if (p == null) {
			throw new RuntimeException("User with id: " + id + "not found!");
		} else {
			Query query = session.createQuery("delete from Person where userid=:pid").setParameter("pid", id);
			query.executeUpdate();
		}
	}

	@Override
	public List<Device> findDevices(int id) {

		List<Device> devices = findById(id).getDevices();
		return devices;
	}

	@Override
	public void deleteAll() {

	}

	@Override
	@Transactional
	public Device findDevice(int person_id, int device_id) {
		Session session = entityManager.unwrap(Session.class);
		Person p = session.get(Person.class, person_id);
		Device d = session.get(Device.class, device_id);
		if (p.getDevices().contains(d)) {
			return d;
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public int logIn(String email, String password) {
		int userid;
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("Select userid from Person where email=:user_email and password=:ps")
				.setParameter("user_email", email).setParameter("ps", password);
		userid = (int) query.getSingleResult();
		// todo checks
		return userid;

	}

	@Override
	@Transactional
	public boolean addDevice(int id, Device d) {
		Person p = findById(id);
		if (!p.getDevices().contains(d)) {
			p.addDevice(d);
			Session session = entityManager.unwrap(Session.class);
			session.saveOrUpdate(p);
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean removeDevice(int id, Device d) {
		Person p = findById(id);
		if((d!=null)&&(!p.getDevices().contains(d))) {
			p.removeDevice(d);
			Session session = entityManager.unwrap(Session.class);
			session.saveOrUpdate(p);
			return true;
		}
		return false;

	}

	@Override
	@Transactional
	public long findIfEmailExists(String email) {
		Session session = entityManager.unwrap(Session.class);
		long result=0;
		try {
			Query query = session.createQuery("select count(*) from Person where email=:email").setParameter("email", email);
			 result=  (long) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	@Transactional
	public List<Room> findHomeRooms(int id) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("select homeid from Person where userid=:user_id").setParameter("user_id",
				id);
		int homeid = (int) query.getSingleResult();
		Query query2 = session.createQuery("from Home where homeid=:home_id").setParameter("home_id", homeid);
		Home home = (Home) query2.getSingleResult();
		return home.getRooms();
	}
	
	@Override
	@Transactional
	public Room findHomeRoom(int id,int rid) {
		List<Room> homerooms=findHomeRooms(id);
		for(Room r:homerooms) {
			if(r.getId()==rid) {
				return r;
			}
		}
		return null;
	}

	@Override
	@Transactional
	public List<Room> findRooms(int id) {
		Person user = findById(id);
		return user.getRooms();
	}

	@Override
	@Transactional
	public Room findRoom(int person_id, int room_id) {
		Session session = entityManager.unwrap(Session.class);
		Person p = session.get(Person.class, person_id);
		Room r = session.get(Room.class, room_id);
		if (p.getRooms().contains(r)) {
			return r;
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public boolean addRoom(int id, Room room) {
		Person p = findById(id);
		if (!p.getRooms().contains(room)) {
			p.addRoom(room);
			Session session = entityManager.unwrap(Session.class);
			session.saveOrUpdate(p);
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean removeRoom(int id, Room room) {
		Person p = findById(id);
		if((room!=null)&&p.getRooms().contains(room)) {
			p.removeRoom(room);
			Session session = entityManager.unwrap(Session.class);
			session.saveOrUpdate(p);
			return true;
		}
		return false;
	

	}

	@Override
	@Transactional
	public List<Device> findRoomDevices(int id, int rid) {
		Session session = entityManager.unwrap(Session.class);
		Person p = session.get(Person.class, id);
		List<Device> devices = new ArrayList<Device>();
		for (Device d : p.getDevices()) {

			if (d.getRoomId() == rid) {
				devices.add(d);
			}
		}
		return devices;
	}

	@Override
	@Transactional
	public Device findRoomDevice(int id, int rid, int did) {
		Session session = entityManager.unwrap(Session.class);
		List<Device> devices = findRoomDevices(id, rid);
		Device d = session.get(Device.class, did);
		if (devices.contains(d)) {
			return d;
		} else {
			throw new RuntimeException();
		}
	}

	@Override
	@Transactional
	public List<Person> findUsersIfAdmin(int id) {
		Person p = findById(id);
		if (p.getRole().equals("0")) {
			return p.getUsers();
		}
		return null;
	}

	@Override
	public int findHome(int id) {
		Person p = findById(id);
		return p.getHomeid();
	}
	
	//Remove room from all users
	@Override
	public void removeRoom(int rid) {
		for (Person p : findAll()) {
			for (Room r : findRooms(p.getId())) {
				if (r.getId() == rid) {
					removeRoom(p.getId(), r);
				}
			}
		}

	}

	@Override
	@Transactional 
	public Person findPersonByEmail(String email) {
		Session session = entityManager.unwrap(Session.class);
		try {
			Query query = session.createQuery("from Person where email=:email").setParameter("email", email);
			Person p= (Person) query.getSingleResult();
			return p;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
