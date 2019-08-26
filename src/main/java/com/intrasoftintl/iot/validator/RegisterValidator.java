package com.intrasoftintl.iot.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.intrasoftintl.iot.dao.PersonDAO;
import com.intrasoftintl.iot.entity.Person;


@Component
public class RegisterValidator implements Validator {
	
	@Autowired
	private PersonDAO personDAO;
	
	 
	
	@Override
	public boolean supports(Class<?> clazz) {
		  return clazz == Person.class;
	}

	 @Override
	    public void validate(Object target, Errors errors) {
	        Person p = (Person) target;
	 
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
	        if (!p.getEmail().contains("@") || !p.getEmail().contains(".")) {
	            errors.rejectValue("email", "InvalidEmail");
	        }
	        if (personDAO.findIfEmailExists(p.getEmail()) != 0) {
	            errors.rejectValue("email", "Duplicate");
	        }

	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
	        if (p.getPassword().length() < 8 || p.getPassword().length() > 32) {
	            errors.rejectValue("password", "Size");
	        }
	        
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty");
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty");
	        
	        if (!p.getConfirmpassword().equals(p.getPassword())) {
	            errors.rejectValue("confirmpassword", "DifferentPassword");
	        }
	 
	    }
	 

}
