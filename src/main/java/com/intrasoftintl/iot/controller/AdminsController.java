package com.intrasoftintl.iot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
			return new ResponseEntity<Object>("Not allowed to see user!", HttpStatus.FORBIDDEN);
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
			return new ResponseEntity<Object>("Not allowed to see user!", HttpStatus.FORBIDDEN);
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
			return new ResponseEntity<Object>("Not allowed to see user!", HttpStatus.FORBIDDEN);
		}
	}

	// Save Device to user
	@PutMapping("/users/devices")
	@ResponseBody
	public ResponseEntity<Object> saveDeviceToUser(@RequestParam int id, @RequestParam int uid, @RequestParam int did) {
		if (personservice.findUsersIfAdmin(id).contains(personservice.findById(uid))) {
			try {
				if (personservice.addDevice(uid, did)) {
					return new ResponseEntity<Object>("Saved device with id " + did + " to user with id " + uid,
							HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>("Device is already saved to user", HttpStatus.OK);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Object>("Couldn't add device!", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<Object>("Not allowed to add device!", HttpStatus.FORBIDDEN);
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
					return new ResponseEntity<Object>("Room is already saved to user", HttpStatus.OK);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Object>("Couldn't add room!", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<Object>("Not allowed to add room!", HttpStatus.FORBIDDEN);
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
			return new ResponseEntity<Object>("Not allowed to delete user!", HttpStatus.FORBIDDEN);
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
			return new ResponseEntity<Object>("Not allowed to remove device!", HttpStatus.FORBIDDEN);
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
		Device d = deviceservice.findById(did);
		if (personservice.findDevices(id).contains(d)) {
			try {
				deviceservice.deleteById(did);
				return new ResponseEntity<Object>("Deleted device with id " + did, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Object>("Couldn't delete device", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<Object>("Not allowed to delete device", HttpStatus.FORBIDDEN);
		}
	}

	// Delete room
	@DeleteMapping("/rooms")
	@ResponseBody
	public ResponseEntity<Object> DeleteRoom(@RequestParam int id, @RequestParam int rid) {
		Room r = roomservice.findById(rid);
		if (personservice.findHomeRooms(id).contains(r)) {
			try {
				roomservice.deleteById(rid);
				return new ResponseEntity<Object>("Deleted room with id " + rid, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Object>("Couldn't delete room", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<Object>("Not allowed to delete room", HttpStatus.FORBIDDEN);
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
			return new ResponseEntity<Object>("Couldn't add room", HttpStatus.NOT_FOUND);
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
			return new ResponseEntity<Object>("Couldn't add device", HttpStatus.NOT_FOUND);
		}
	}

	// Save Device to room not needed
	@PutMapping("/rooms/devices")
	@ResponseBody
	public ResponseEntity<Object> saveDeviceToRoom(@RequestParam int rid, @RequestParam int did) {
		try {
			roomservice.addDevice(rid, did);
			return new ResponseEntity<Object>("Added device", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("Couldn't add device", HttpStatus.NOT_FOUND);
		}
	}

	// update user
	@PutMapping("/users")
	public ResponseEntity<Object> updateUser(@RequestBody Person p) {
		try {
			personservice.saveUser(p);
			return new ResponseEntity<Object>("Updated user", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("Couldn't update user", HttpStatus.CONFLICT);
		}

	}

	// update existing device
	@PutMapping("/devices")
	public ResponseEntity<Object> updateDevice(@RequestBody Device d) {
		try {
			deviceservice.save(d);
			return new ResponseEntity<Object>("Updated device", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("Couldn't update device", HttpStatus.CONFLICT);
		}

	}

	// update room
	@PutMapping("/rooms")
	public ResponseEntity<Object> updateRoom(@RequestBody Room r) {
		try {
			roomservice.save(r);
			return new ResponseEntity<Object>("Updated room", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("Couldn't update device", HttpStatus.NOT_FOUND);
		}
	}

}
