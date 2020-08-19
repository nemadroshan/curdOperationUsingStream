package com.rn.controller;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rn.dao.PersonDAO;
import com.rn.dto.Person;

@RestController
public class PersonRestController<R> {

	@Autowired
	private PersonDAO dao;

	//get all person api
	@GetMapping("/persons")
	public List<Person> getAllPerson() {
		System.out.println("inside rest controller");
		List<Person> alPersons = dao.getAllPerson();
		alPersons.stream().forEach(System.out::println);
		return alPersons;
	}

	// save Api
	@PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String savePersons(@RequestBody List<Person> person) {
		int save = dao.save(person);
		if (save < 0)
			return " Unnsuccess Full";
		else
			return save + "updated";
	}

	//update person api
	@PutMapping(path = "/update/{id}/{name}/{age}/{addrs}")
	public String updatePersonByid(@PathVariable("id")int id ,
			@PathVariable("name")String name,
			@PathVariable("age")int age,		
			@PathVariable("addrs")String addrs) {	
		Person person = dao.getPersonById(id).stream().findFirst().get();
		//person.setId(id);
		person.setName(name);
		person.setAge(age);
		person.setAddress(addrs);
		int count = dao.updatePersonById(person);
		if(count >0)
			return count+" row is updated";
		else
			return "something went wrong";
	}

	//delete person api
	@DeleteMapping(path = "/delete/{id}")
	public @ResponseBody String deletePersonById(@PathVariable("id")int id) {
		int count = dao.deletePersonById(id);

		List<Person> personById = dao.getPersonById(id);
		Person person = personById.stream().findFirst().get();
		System.out.println(person);
		if(count>0)
			return count +" row is deleted successfully";
		else
			return "something went wrong";
	}


	//getPersonbByID
	@GetMapping(path="/getperson/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
	public@ResponseBody Person getPersonById(@PathVariable("id") int id) {
		Person person = dao.getPersonById(id).stream().findFirst().get();
		System.out.println("From Api "+person.toString());
		return person;
	}
}
