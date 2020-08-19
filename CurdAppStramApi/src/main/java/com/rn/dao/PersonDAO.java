package com.rn.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rn.dto.Person;

@Repository
public class PersonDAO {
	private static final String GET_ALL_PERSON = "SELECT P.ID , P.NAME,P.AGE,P.ADDRESS FROM PERSON P ";
	private static final String SAVE_ALL_PERSON = "INSERT INTO PERSON (ID,NAME,AGE,ADDRESS) VALUES(?,?,?,?)";
	private static final String GET_PERSON_BY_ID ="SELECT  ID,NAME,AGE,ADDRESS FROM PERSON  WHERE ID=  ?";
	private static final String DELETE_PERSON_BY_ID = "DELETE FROM PERSON P WHERE P.ID=?";
	private static final String UPDATE_PERSON_BY_ID = "UPDATE PERSON P SET P.NAME =?,P.AGE= ?,P.ADDRESS= ? WHERE P.ID = ?";

	@Autowired
	private JdbcTemplate jt;

	public List<Person> getAllPerson() {
		List<Person> list = null;
		list = jt.query(GET_ALL_PERSON, (rs) -> {
			List<Person> ps = new ArrayList<>();
			while (rs.next()) {
				Person p = new Person();
				p.setId(rs.getInt(1));
				p.setName(rs.getString(2));
				p.setAge(rs.getInt(3));
				p.setAddress(rs.getString(4));
				ps.add(p);
			}
			return ps;
		});
		return list;
	}

	public int save(List<Person> list) {
		int count =0;
		for(Person p :list) {
		count =jt.update(SAVE_ALL_PERSON,p.getId(),p.getName(),p.getAge(),p.getAddress());
		count++;
		System.out.println(count);
		}
		return count;
	}

	public List<Person> getPersonById(int id) throws DataAccessException{
		List<Person> list = new ArrayList<>();
		System.out.println("PersonDAO.getPersonById()");	
		Person queryForObject = jt.queryForObject(GET_PERSON_BY_ID, (rs,rowNum)->{
			Person p = new Person();
			p.setId(rs.getInt(1));
			p.setName(rs.getString(2));
			p.setAge(rs.getInt(3));
			p.setAddress(rs.getString(4));
			return p;
		}, id);
		list.add(queryForObject);
		return list;
	}
	public int updatePersonById(Person person) {
		System.out.println("PersonDAO.updatePersonById()");
		int count=0;
		count = jt.update(UPDATE_PERSON_BY_ID,person.getName(),person.getAge(),person.getAddress(),person.getId());
		return count;
	}


	public int deletePersonById(int id ) {
		System.out.println("PersonDAO.deletePersonById()");
		int count=0;
		count = jt.update(DELETE_PERSON_BY_ID, id);
		return count;
	}

}
