package com.intrasoftintl.iot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.intrasoftintl.iot.service.*;


@RestController
@RequestMapping("/users")
public class UsersController {

	private PersonService personservice;

	private DeviceService deviceservice;
	
	private HomeService homeservice;

	@Autowired
	public UsersController(PersonService personservice, DeviceService deviceservice,HomeService homeservice) {
		this.personservice = personservice;
		this.deviceservice = deviceservice;
		this.homeservice=homeservice;
	}

	// Get user with id {id}
	@GetMapping
	@ResponseBody
	public ResponseEntity<Object> getUser(@RequestParam int id) {
		try {
			return new ResponseEntity<Object>(homeservice.findRooms(personservice.findHome(id)), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("User not found!", HttpStatus.NOT_FOUND);
		}
	}

	// Get rooms that user with {id} can interact with
	@GetMapping("/rooms")
	@ResponseBody
	public ResponseEntity<Object> getUserRooms(@RequestParam int id) {
		try {
			return new ResponseEntity<Object>(personservice.findRooms(id), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("User not found!", HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/rooms/devices/status")
	@ResponseBody
	public ResponseEntity<Object> changeStatus(@RequestParam int id, @RequestParam int rid, @RequestParam int did) {
		try {
			deviceservice.changeStatus(did);
			return new ResponseEntity<Object>(personservice.findRoomDevice(id, rid, did), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("Couldn't change status!", HttpStatus.NOT_FOUND);
		}
	}

	// Get room devices that user with {id} can interact with
	@GetMapping("/rooms/devices")
	@ResponseBody
	public ResponseEntity<Object> getUserRoomDevices(@RequestParam int id, @RequestParam int rid) {
		try {
			return new ResponseEntity<Object>(personservice.findRoomDevices(id, rid), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("Room not found!", HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * Get room device with {did} that user with {id} can interact with
	 * 
	 * @GetMapping("/rooms/device")
	 * 
	 * @ResponseBody public ResponseEntity<Object> getUserRoomDevice(@RequestParam
	 * int id, @RequestParam int rid, @RequestParam int did) { try { //return new
	 * ResponseEntity<Object>(personservice.findRoomDevice(id, rid,
	 * did),HttpStatus.OK); return new
	 * ResponseEntity<Object>(acunitservice.findById(did) ,HttpStatus.OK); } catch
	 * (Exception e) { e.printStackTrace(); return new
	 * ResponseEntity<Object>("Device not found!", HttpStatus.NOT_FOUND); } }
	 */

	// get user device with id {did}
	@GetMapping("/rooms/device")
	@ResponseBody
	public ResponseEntity<Object> getUserDevice(@RequestParam int id, @RequestParam int did) {
		try {
			// return new ResponseEntity<Object>(personservice.findDevice(id, did),
			// HttpStatus.OK);
			return new ResponseEntity<Object>(deviceservice.findInterfaceById(did), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("Device not found!", HttpStatus.NOT_FOUND);
		}

	}

}
