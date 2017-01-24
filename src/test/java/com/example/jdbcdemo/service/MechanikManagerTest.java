package com.example.jdbcdemo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.example.jdbcdemo.domain.Mechanik;

public class MechanikManagerTest {
	
	MechanikManager mechanikManager = new MechanikManager();
	
	private final static String NAME_1 = "Jan";
	private final static String NAME_2 = "Mariusz";
	private final static String ABOUT_1 = "Majster";
	
	@Test
	public void checkConnection(){
		assertNotNull(mechanikManager.getConnection());
	}
	
	@Test
	public void checkAdding() throws SQLException{
		Connection connection = mechanikManager.getConnection();
		connection.setAutoCommit(false);
		try
		{
			Mechanik mechanik = new Mechanik(NAME_1,ABOUT_1);
			
			assertEquals(1,mechanikManager.addMechanik(mechanik));
			
			List<Mechanik> mechaniks = mechanikManager.getAllMechaniks();
			
			int size = mechaniks.size();
			Mechanik mechanikRetrieved = mechaniks.get(size -1);
			
			assertEquals(NAME_1,mechanikRetrieved.getImie());
			assertEquals(ABOUT_1,mechanikRetrieved.getStanowisko());
		
		  } finally {
			    connection.rollback();
			    connection.close();
		  }
	}
	@Test
	public void checkEditing() throws SQLException{
		Connection connection = mechanikManager.getConnection();
		connection.setAutoCommit(false);
		try
		{
			Mechanik mechanik = new Mechanik(NAME_1,ABOUT_1);
			assertEquals(1,mechanikManager.addMechanik(mechanik));
			
			List<Mechanik> mechaniks = mechanikManager.getAllMechaniks();
			
			int size = mechaniks.size();
			Mechanik mechanikRetrieved = mechaniks.get(size -1);
			mechaniks.remove(size -1);
			
			mechanikRetrieved.setImie(NAME_2);
			assertEquals(1,mechanikManager.EditMechanik(mechanikRetrieved));
			
			List<Mechanik> mechaniks1 = mechanikManager.getAllMechaniks();
			size = mechaniks1.size();
			Mechanik mechanikRetrieved1 = mechaniks1.get(size -1);
			
			mechaniks1.remove(size- 1);
			assertEquals(mechaniks, mechaniks1);
			assertEquals(NAME_2,mechanikRetrieved1.getImie());
			assertEquals(ABOUT_1,mechanikRetrieved1.getStanowisko());
		  } finally {
			    connection.rollback();
			    connection.close();
		  }
	}
	@Test
	public void checkDeleting() throws SQLException{
		Connection connection = mechanikManager.getConnection();
		connection.setAutoCommit(false);
		try
		{
		
		Mechanik mechanik = new Mechanik(NAME_1,ABOUT_1);
		assertEquals(1,mechanikManager.addMechanik(mechanik));
		
		List<Mechanik> mechaniks = mechanikManager.getAllMechaniks();
		
		int size = mechaniks.size();
		Mechanik mechanikRetrieved = mechaniks.get(size -1);
		
		assertEquals(1,mechanikManager.DeleteMechanik(mechanikRetrieved));
		mechaniks.remove(size -1);
		List<Mechanik> mechaniks1 = mechanikManager.getAllMechaniks();
		assertEquals(mechaniks, mechaniks1);
		} finally {
		    connection.rollback();
		    connection.close();
		}
	}
		
}
