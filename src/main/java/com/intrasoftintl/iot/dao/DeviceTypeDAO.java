package com.intrasoftintl.iot.dao;

import java.util.List;

import com.intrasoftintl.iot.entity.*;

	public interface DeviceTypeDAO {
	
	public List<DeviceType> findAll();
	
	public DeviceType findById(int id);
	
	public void save(DeviceType deviceType);
	
	public void deleteById(int id);
}
