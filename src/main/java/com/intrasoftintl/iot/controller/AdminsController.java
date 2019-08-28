package com.intrasoftintl.iot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.intrasoftintl.iot.entity.Device;
import com.intrasoftintl.iot.entity.Home;
import com.intrasoftintl.iot.entity.Person;
import com.intrasoftintl.iot.entity.Room;
import com.intrasoftintl.iot.service.DeviceService;
import com.intrasoftintl.iot.service.HomeService;
import com.intrasoftintl.iot.service.PersonService;
import com.intrasoftintl.iot.service.RoomService;

@RestController
@RequestMapping("/admin")
public class AdminsController {

	private PersonService personservice;

	private RoomService roomservice;

	private DeviceService deviceservice;

	private HomeService homeservice;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	public AdminsController(PersonService personservice, RoomService roomservice, DeviceService deviceservice,
			HomeService homeservice) {
		this.personservice = personservice;
		this.roomservice = roomservice;
		this.deviceservice = deviceservice;
		this.homeservice = homeservice;
	}

	// Get all users
	@GetMapping("/users")
	@ResponseBody
	public ResponseEntity<Object> getUsers(@RequestParam int id) {
		try {
			return new ResponseEntity<Object>(personservice.findUsersIfAdmin(id), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("Users not found!", HttpStatus.NOT_FOUND);
		}
	}

	// Get user with id {id}
	@GetMapping("/user")
	@ResponseBody
	public ResponseEntity<Object> getUser(@RequestParam int id, @RequestParam int uid) {

		if (personservice.findUsersIfAdmin(id).contains(personservice.findById(uid))) {
			try {
				return new ResponseEntity<Object>(personservice.findById(uid), HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Object>("User not found!", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<Object>("Not allowed to see user!", HttpStatus.NOT_FOUND);
		}
	}

	// Get all rooms
	@GetMapping("/rooms")
	@ResponseBody
	public ResponseEntity<Object> getRooms(@RequestParam int id) {

		try {
			return new ResponseEntity<Object>(personservice.findHomeRooms(id), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("Rooms not found!", HttpStatus.NOT_FOUND);
		}
	}

	// Get room with id {rid}
	@GetMapping("/room")
	@ResponseBody
	public ResponseEntity<Object> getRoom(@RequestParam int id, @RequestParam int rid) {

		try {
			return new ResponseEntity<Object>(personservice.findHomeRoom(id, rid), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("Room not found!", HttpStatus.NOT_FOUND);
		}

	}

	// not needed
	// Get room device with id {did}
	@GetMapping("/rooms/device")
	@ResponseBody
	public ResponseEntity<Object> getRoomDevice(@RequestParam int rid, @RequestParam int did) {
		try {
			return new ResponseEntity<Object>(roomservice.findDevice(rid, did), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("Device not found!", HttpStatus.NOT_FOUND);
		}
	}

	// Get all devices
	@GetMapping("/devices")
	@ResponseBody
	public ResponseEntity<Object> getDevices(@RequestParam int id) {

		try {
			return new ResponseEntity<Object>(homeservice.findDevicesByroom(personservice.findHome(id)), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("Devices not found!", HttpStatus.NOT_FOUND);
		}

	}

	// Get All Device Types
	@GetMapping("/devicetypes")
	@ResponseBody
	public ResponseEntity<Object> getDeviceTypes() {
		try {
			return new ResponseEntity<Object>(deviceservice.getDeviceTypes(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("Not found!", HttpStatus.NOT_FOUND);
		}
	}

	// Get device with id {id}
	@GetMapping("/device")
	@ResponseBody
	public ResponseEntity<Object> getDevice(@RequestParam int id, @RequestParam int did) {
		try {
			return new ResponseEntity<Object>(homeservice.findHomeDevice(personservice.findHome(id), did),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("Device not found!", HttpStatus.NOT_FOUND);
		}
	}

	// Get devices that user with {uid} can interact with
	@GetMapping("/users/devices")
	@ResponseBody
	public ResponseEntity<Object> getUserDevices(@RequestParam int id, @RequestParam int uid) {
		if (personservice.findUsersIfAdmin(id).contains(personservice.findById(uid))) {
			try {
				return new ResponseEntity<Object>(personservice.findDevices(uid), HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Object>("Devices not found!", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<Object>("Not allowed to see user!", HttpStatus.UNAUTHORIZED);
		}

	}

	// Get rooms that user with {id} can interact with
	@GetMapping("/users/rooms")
	@ResponseBody
	public ResponseEntity<Object> getUserRooms(@RequestParam int id, @RequestParam int uid) {
		if (personservice.findUsersIfAdmin(id).contains(personservice.findById(uid))) {
			try {
				return new ResponseEntity<Object>(personservice.findRooms(uid), HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Object>("Rooms not found!", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<Object>("Not allowed to see user!", HttpStatus.UNAUTHORIZED);
		}
	}

	// Save Device to user
	@PutMapping("/users/devices")
	@ResponseBody
	public ResponseEntity<Object> saveDeviceToUser(@RequestParam int id, @RequestParam int uid, @RequestParam int did) {
		if (personservice.findUsersIfAdmin(id).contains(personservice.findById(uid))) {
			try {

				// check if user cant see the room of the device
				if (!personservice.findById(uid).getRooms()
						.contains(roomservice.findById(deviceservice.findById(did).getRoomId()))) {
					return new ResponseEntity<Object>("User can't see the room of the device",
							HttpStatus.NOT_ACCEPTABLE);

					// check if user already has asked device
				} else if (!personservice.addDevice(uid, did)) {
					return new ResponseEntity<Object>("Device is already saved to user", HttpStatus.NOT_ACCEPTABLE);
				} else {
					return new ResponseEntity<Object>("Saved device with id " + did + " to user with id " + uid,
							HttpStatus.OK);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Object>("Couldn't add device!", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<Object>("Not allowed to add device!", HttpStatus.UNAUTHORIZED);
		}

	}

	// Save Room to user
	@PutMapping("/users/rooms")
	@ResponseBody
	public ResponseEntity<Object> saveRoomToUser(@RequestParam int id, @RequestParam int uid, @RequestParam int rid) {
		if (personservice.findUsersIfAdmin(id).contains(personservice.findById(uid))) {
			try {
				if (personservice.addRoom(uid, rid)) {
					return new ResponseEntity<Object>("Saved room with id " + rid + " to user with id " + uid,
							HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>("Room is already saved to user", HttpStatus.NOT_ACCEPTABLE);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Object>("Couldn't add room!", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<Object>("Not allowed to add room!", HttpStatus.UNAUTHORIZED);
		}

	}

	// Delete user
	@DeleteMapping("/users")
	@ResponseBody
	public ResponseEntity<Object> DeleteUser(@RequestParam int id, @RequestParam int uid) {
		if (personservice.findUsersIfAdmin(id).contains(personservice.findById(uid))) {
			try {
				personservice.deleteById(uid);
				return new ResponseEntity<Object>("Deleted user with id " + uid, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Object>("Couldn't delete user!", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<Object>("Not allowed to delete user!", HttpStatus.UNAUTHORIZED);
		}
	}

	// Delete device from user
	@DeleteMapping("users/devices")
	@ResponseBody
	public ResponseEntity<Object> DeleteUserDevice(@RequestParam int id, @RequestParam int uid, @RequestParam int did) {
		if (personservice.findUsersIfAdmin(id).contains(personservice.findById(uid))) {
			try {
				if (personservice.removeDevice(uid, did)) {
					return new ResponseEntity<Object>("Deleted device with id " + did + " from user with id " + uid,
							HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>("User can't interact with this device", HttpStatus.NOT_FOUND);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Object>("Couldn't remove device!", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<Object>("Not allowed to remove device!", HttpStatus.NOT_FOUND);
		}
	}

	// Delete room from user
	@DeleteMapping("users/rooms")
	@ResponseBody
	public ResponseEntity<Object> DeleteUserRoom(@RequestParam int id, @RequestParam int uid, @RequestParam int rid) {
		if (personservice.findUsersIfAdmin(id).contains(personservice.findById(uid))) {
			try {
				if (personservice.removeRoom(uid, rid)) {
					return new ResponseEntity<Object>("Deleted room with id " + rid + " from user with id " + uid,
							HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>("User can't see this room", HttpStatus.NOT_FOUND);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Object>("Couldn't remove room!", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<Object>("Not allowed to remove room!", HttpStatus.NOT_FOUND);
		}
	}

	// Delete device from house(and from all users)
	@DeleteMapping("/devices")
	@ResponseBody
	public ResponseEntity<Object> DeleteDevice(@RequestParam int id, @RequestParam int did) {
		try {
			deviceservice.deleteById(did);
			return new ResponseEntity<Object>("Deleted device with id " + did, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("Couldn't delete device", HttpStatus.NOT_FOUND);
		}
	}

	// Delete room
	@DeleteMapping("/rooms")
	@ResponseBody
	public ResponseEntity<Object> DeleteRoom(@RequestParam int id, @RequestParam int rid) {
		try {
			roomservice.deleteById(rid);
			return new ResponseEntity<Object>("Deleted room with id " + rid, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("Couldn't delete room", HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/devices/move")
	@ResponseBody
	public ResponseEntity<Object> moveDevice(@RequestParam int id, @RequestParam int did, @RequestParam int rid) {
		int hid = personservice.findHome(id);
		Home h = homeservice.findById(hid);
		if ((h.getRooms().contains(roomservice.findById(rid))
				&& (!roomservice.findById(rid).getDevices().contains(deviceservice.findById(did))))) {
			try {
				deviceservice.moveDevice(did, rid);
				return new ResponseEntity<Object>("Moved device", HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Object>("Couldn't move device", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<Object>("Not allowed to move device", HttpStatus.FORBIDDEN);
		}
	}

	// Save Room
	@PostMapping("/rooms")
	@ResponseBody
	public ResponseEntity<Object> saveRoom(@RequestBody Room r, @RequestParam int id) {
		r.setId(0);
		int hid = personservice.findHome(id);
		r.setHomeid(hid);
		if (r.getName() != null) {
			roomservice.save(r);
			homeservice.addRoom(hid, r.getId());
			personservice.addRoom(id, r.getId());
			return new ResponseEntity<Object>("Added room", HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("Couldn't add room", HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}

	// Save Device
	@PostMapping("/devices")
	@ResponseBody
	public ResponseEntity<Object> saveDevice(@RequestBody Device d, @RequestParam int id) {
		d.setId(0);
		d.setStatus("off");
		Room r = roomservice.findById(d.getRoomId());
		int hid = personservice.findHome(id);
		if (d.isValid() && (r != null) && (homeservice.findById(hid).getRooms().contains(r))) {
			deviceservice.save(d);
			personservice.addDevice(id, d.getId());
			return new ResponseEntity<Object>("Added device", HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("Couldn't add device", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	/*---------------------------------------------TODO validator?----------------------------------------------------*/

	// update user
	@PutMapping("/users")
	public ResponseEntity<Object> updateUser(@RequestBody Person p, @RequestParam int id) {
		int userid = p.getId();
		Person dbuser = personservice.findById(userid);
		if (personservice.findUsersIfAdmin(id).contains(dbuser)) {
			try {
				dbuser.setFirstName(p.getFirstName());
				dbuser.setLastName(p.getLastName());
				dbuser.setEmail(passwordEncoder.encode(p.getEmail()));
				dbuser.setPassword(passwordEncoder.encode(p.getPassword()));
				dbuser.setConfirmpassword(p.getPassword());
				personservice.saveUser(dbuser);
				return new ResponseEntity<Object>("Updated user", HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Object>("Couldn't update user", HttpStatus.CONFLICT);
			}
		} else {
			return new ResponseEntity<Object>("Not allowed to update user", HttpStatus.FORBIDDEN);
		}

	}

	// update existing device
	@PutMapping("/devices")
	public ResponseEntity<Object> updateDevice(@RequestBody Device d, @RequestParam int id) {
		int deviceid = d.getId();
		Device dbdevice = deviceservice.findById(deviceid);
		if (personservice.findById(id).getDevices().contains(dbdevice)) {
			try {
				dbdevice.setName(d.getName());
				dbdevice.setInformation(d.getInformation());
				dbdevice.setApi_key(d.getApi_key());
				deviceservice.save(dbdevice);
				return new ResponseEntity<Object>("Updated device", HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Object>("Couldn't update device", HttpStatus.CONFLICT);
			}
		} else {
			return new ResponseEntity<Object>("Not allowed to update device", HttpStatus.FORBIDDEN);
		}

	}

	// update room
	@PutMapping("/rooms")
	public ResponseEntity<Object> updateRoom(@RequestBody Room r, @RequestParam int id) {
		int roomid = r.getId();
		Room dbroom = roomservice.findById(roomid);
		if (personservice.findById(id).getRooms().contains(dbroom)) {
			try {
				dbroom.setName(r.getName());
				roomservice.save(dbroom);
				return new ResponseEntity<Object>("Updated room", HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Object>("Couldn't update device", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<Object>("Not allowed to update room", HttpStatus.FORBIDDEN);
		}
	}

}
