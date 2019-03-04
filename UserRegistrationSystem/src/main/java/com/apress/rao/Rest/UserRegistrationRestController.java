package com.apress.rao.Rest;

	
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apress.rao.Exception.CustomErrorType;
import com.apress.rao.dto.UsersDTO;
import com.apress.rao.repository.UserJpaRepository;

@RestController
@RequestMapping("/api/user")
public class UserRegistrationRestController {
	public static final Logger logger = LoggerFactory.getLogger(UserRegistrationRestController.class);
	
	private UserJpaRepository userJpaRepository;
	
	@Autowired
	public void setUserJpaRepository(UserJpaRepository userJpaRepository){
		this.userJpaRepository  = userJpaRepository;
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UsersDTO>> listAllUsers(){
		List<UsersDTO> users = null;
		
		try{
			users = userJpaRepository.findAll();
		}catch(java.util.NoSuchElementException e){
			logger.info("Error for All Users " + e.getMessage());
		}
		
		if(users.isEmpty()){
			return new  ResponseEntity<List<UsersDTO>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<UsersDTO>>(users, HttpStatus.OK);
	}
	
	@PostMapping(value="/",  consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsersDTO> createUser(@Valid @RequestBody final UsersDTO user){
		if (userJpaRepository.findByName(user.getName()) != null) {
			return new ResponseEntity<UsersDTO>(
					new CustomErrorType(
							"Unable to create new User. A user with name " + user.getName() + " already exists."),
					HttpStatus.CONFLICT);
		}
		
		userJpaRepository.save(user);
		return new ResponseEntity<UsersDTO>(user, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UsersDTO> getUserById(@PathVariable("id") final Long id){
		UsersDTO user  = null;
		try{
			user = userJpaRepository.findById(id).get();
		}catch(java.util.NoSuchElementException e){
			logger.info("Error get with ID " + e.getMessage());
		}
		if(user == null){
			return new ResponseEntity<UsersDTO> (new CustomErrorType("User with Id " + id + " not found" ),HttpStatus.NOT_FOUND);
		}
		
		
		return new ResponseEntity<UsersDTO>(user, HttpStatus.OK);
	}
	
	
	@PutMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsersDTO> updateUser(@PathVariable("id") final Long id, @RequestBody UsersDTO user){
		
		UsersDTO currentUser = null;
		
		//fetch user based on id and set it to currentUser object of type UsersDTO
		try{
			currentUser = userJpaRepository.findById(id).get();
		}catch(java.util.NoSuchElementException e){
			logger.info("Error PutMapping " + e.getMessage());
		}
		
		if (currentUser == null) {
			return new ResponseEntity<UsersDTO>(
					new CustomErrorType("Unable to update. User with id " + id + " not found."), HttpStatus.NOT_FOUND);
		}
		
		
		//update currentUser object data with user object data
		currentUser.setName(user.getName());
		currentUser.setAddress(user.getAddress());
		currentUser.setEmail(user.getEmail());
		
		//save CurrentUser Object
		userJpaRepository.saveAndFlush(currentUser);
		
		//return ResponseEntity Object
		return new ResponseEntity<UsersDTO>(currentUser, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<UsersDTO> deleteUser(@PathVariable("id") final Long id){
		
		UsersDTO user = null;
		
		//fetch user based on id and set it to currentUser object of type UsersDTO
		try{
			user = userJpaRepository.findById(id).get();
		}catch(java.util.NoSuchElementException e){
			logger.info("Error Delete " + e.getMessage());
		}
		
		if (user == null) {
			return new ResponseEntity<UsersDTO>(
					new CustomErrorType("Unable to delete. User with id " + id + " not found."), HttpStatus.NOT_FOUND);
		}
		
		
		userJpaRepository.deleteById(id);
		return new ResponseEntity<UsersDTO>(HttpStatus.NO_CONTENT);
	}
	
}
