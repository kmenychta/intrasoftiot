package com.intrasoftintl.iot.service;

import java.util.List;

import com.intrasoftintl.iot.entity.*;

public interface DeviceTypeService {
	public List<DeviceType> findAll();
	
	public DeviceType findById(int id);
	
	public void save(DeviceType deviceType);
	
	public void deleteById(int id);
	
}
