package com.intrasoftintl.iot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intrasoftintl.iot.dao.DeviceTypeDAO;
import com.intrasoftintl.iot.entity.DeviceType;

@Service
public class DeviceTypeServiceimpl implements DeviceTypeService {
	
	@Autowired
	private DeviceTypeDAO deviceTypeDAO;
	
	public DeviceTypeServiceimpl(DeviceTypeDAO deviceTypeDAO) {
		this.deviceTypeDAO=deviceTypeDAO;
	}
	@Override
	public List<DeviceType> findAll() {
		return deviceTypeDAO.findAll();
	}

	@Override
	public DeviceType findById(int id) {
		return deviceTypeDAO.findById(id);
	}

	@Override
	public void save(DeviceType deviceType) {
		deviceTypeDAO.save(deviceType);

	}

	@Override
	public void deleteById(int id) {
		deviceTypeDAO.deleteById(id);

	}

}
