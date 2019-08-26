package com.intrasoftintl.iot.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.intrasoftintl.iot.entity.DeviceType;

@Repository
public class DeviceTypeDAOimpl implements DeviceTypeDAO{
	
	private EntityManager entityManager;
	
	@Autowired
	public DeviceTypeDAOimpl(EntityManager entityManager) {
		this.entityManager=entityManager;
	}
	
	@Override
	@Transactional
	public List<DeviceType> findAll() {
		Session session=entityManager.unwrap(Session.class);
		List<DeviceType> devicetypes=session.createQuery("from DeviceType",DeviceType.class).getResultList();
		return devicetypes;
	}

	@Override
	@Transactional
	public DeviceType findById(int id) {
		Session session=entityManager.unwrap(Session.class);
		DeviceType d=session.get(DeviceType.class, id);
		return d;
	}

	@Override
	@Transactional
	public void save(DeviceType deviceType) {
		Session session=entityManager.unwrap(Session.class);
		session.saveOrUpdate(deviceType);
		
	}

	@Override
	@Transactional
	public void deleteById(int id) {
		Session session=entityManager.unwrap(Session.class);
		DeviceType d=findById(id);
	if(d==null) {
		throw new RuntimeException("Device with id: "+id+"not found!");
	}else {
		Query query=session.createQuery("delete from DeviceType where typeid=:did").setParameter("did",id);
		query.executeUpdate();
	}

	}

}
