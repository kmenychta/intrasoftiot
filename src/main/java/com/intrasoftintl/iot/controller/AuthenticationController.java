package com.intrasoftintl.iot.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.intrasoftintl.iot.entity.Person;
import com.intrasoftintl.iot.service.PersonService;
import com.intrasoftintl.iot.validator.RegisterValidator;

@Controller
public class AuthenticationController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private PersonService personservice;

	@Autowired
	private RegisterValidator userValidator;

	public AuthenticationController(PersonService personservice) {
		this.personservice = personservice;

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLogin(Model model) {
		return "login";
	}

	@RequestMapping(value = "/afterlogin", method = RequestMethod.POST)
	public void getDev(HttpServletResponse response) throws IOException {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = user.getUsername();
		Person loggedUser = personservice.findPersonByEmail(email);
		response.addCookie(new Cookie("id", String.valueOf(loggedUser.getId())));
		response.sendRedirect("/app/users/rooms?id=" + loggedUser.getId());

	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("user", new Person());
		return "registration";
	}

	// save new user
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registration(@ModelAttribute("user") Person user, BindingResult bindingResult, Model model,
			HttpServletRequest request) {

		userValidator.validate(user, bindingResult);

		if (bindingResult.hasErrors()) {
			return "registration";
		}
		model.addAttribute("user", user);
		user.setId(0);
		user.setRole("1");
		String notEncryptedPassword = user.getPassword();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setConfirmpassword(passwordEncoder.encode(user.getPassword()));
		user.setHomeid(1);
		personservice.saveUser(user);
		try {
			request.login(user.getEmail(), notEncryptedPassword);
		} catch (ServletException e) {
			e.printStackTrace();
		}
		return "redirect:/users/rooms?id=" + user.getId();
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login?logout";
	}

}