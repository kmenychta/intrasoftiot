package com.intrasoftintl.iot.service;

import com.intrasoftintl.iot.entity.Person;

public interface AuthenticationService {
	
	public int logIn(String email,String password);
	
	public int Registration(Person p);
}
