package com.intrasoftintl.iot.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.intrasoftintl.iot.dao.PersonDAO;
import com.intrasoftintl.iot.entity.Person;

public class AuthenticationServiceimpl implements AuthenticationService {

	@Autowired
	private PersonDAO personDAO;
	
	public AuthenticationServiceimpl(PersonDAO personDAO) {
		this.personDAO=personDAO;
	}
	@Override
	public int logIn(String email, String password) {
		return personDAO.logIn(email, password);
	}

	@Override
	public int Registration(Person p) {
		// TODO Auto-generated method stub
		return 0;
	}

}
