package com.example.jdbcdemo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.example.jdbcdemo.domain.Bus;
import com.example.jdbcdemo.domain.Mechanik;

import junit.framework.Assert;

public class BusManagerTest {
	MechanikManager mechanikManager = new MechanikManager();
	BusManager busManager = new BusManager();
	
	private final static String NAME_1 = "122";
	private final static String NAME_2 = "222";
	private final static String ABOUT_1 = "solaris";
	
	private final static String NAME_SPONSOR_1 = "Jan";
	private final static String SPONSOR_ABOUT_1 = "Starszy Majster";
	
	@Test
	public void checkConnection(){
		assertNotNull(busManager.getConnection());
	}
	
	@Test
	public void checkAdding() throws SQLException{
		Connection connection = busManager.getConnection();
		connection.setAutoCommit(false);
		Connection connMechanik = mechanikManager.getConnection();
		connMechanik.setAutoCommit(false);
		try
		{
			Mechanik mechanik = new Mechanik(NAME_SPONSOR_1,SPONSOR_ABOUT_1);
			mechanikManager.addMechanik(mechanik);
			List<Mechanik> mechaniks = mechanikManager.getAllMechaniks();
			mechanik = mechaniks.get(mechaniks.size()-1);
			
			Bus bus = new Bus(NAME_1,ABOUT_1,mechanik.getId());
			
			assertEquals(1,busManager.addBus(bus));
			
			List<Bus> buses = busManager.getAllBuses();
			
			int size = buses.size();
			Bus busRetrieved = buses.get(size -1);
			
			assertEquals(NAME_1,busRetrieved.getNrlini());
			assertEquals(ABOUT_1,busRetrieved.getMarka());
			assertEquals(bus.getId(),busRetrieved.getMechanik());
		
		  } finally {
			    connection.rollback();
			    connection.close();
			    connMechanik.rollback();
			    connMechanik.close();
		  }
	}
	@Test
	public void checkEditing() throws SQLException{
		Connection connection = busManager.getConnection();
		connection.setAutoCommit(false);
		Connection connMechanik = mechanikManager.getConnection();
		connMechanik.setAutoCommit(false);
		try
		{
			Mechanik mechanik = new Mechanik(NAME_SPONSOR_1,SPONSOR_ABOUT_1);
			mechanikManager.addMechanik(mechanik);
			List<Mechanik> mechaniks = mechanikManager.getAllMechaniks();
			mechanik = mechaniks.get(mechaniks.size()-1);
					
			Bus bus = new Bus(NAME_1,ABOUT_1,mechanik.getId());
			assertEquals(1,busManager.addBus(bus));
			
			List<Bus> buses = busManager.getAllBuses();
			
			int size = buses.size();
			Bus busRetrieved = buses.get(size -1);
			buses.remove(size -1);
			
			busRetrieved.setNrlini(NAME_2);
			assertEquals(1,busManager.EditBus(busRetrieved));
			
			List<Bus> buses1 = busManager.getAllBuses();
			size = buses1.size();
			Bus busRetrieved1 = buses1.get(size -1);
			
			buses1.remove(size- 1);
			assertEquals(buses, buses1);
			assertEquals(NAME_2,busRetrieved1.getNrlini());
			assertEquals(ABOUT_1,busRetrieved1.getMarka());
			assertEquals(mechanik.getId(),busRetrieved.getMechanik());
		  } finally {
			    connection.rollback();
			    connection.close();
			    connMechanik.rollback();
			    connMechanik.close();
		  }
	}
	@Test
	public void checkDeleting() throws SQLException{
		Connection connection = busManager.getConnection();
		connection.setAutoCommit(false);
		Connection connMechanik = mechanikManager.getConnection();
		connMechanik.setAutoCommit(false);
		try
		{
		
		Mechanik mechanik = new Mechanik(NAME_SPONSOR_1,SPONSOR_ABOUT_1);
		mechanikManager.addMechanik(mechanik);
		List<Mechanik> mechaniks = mechanikManager.getAllMechaniks();
		mechanik = mechaniks.get(mechaniks.size()-1);
				
		Bus bus = new Bus(NAME_1,ABOUT_1);
		assertEquals(1,busManager.addBus(bus));
		
		List<Bus> buses = busManager.getAllBuses();
		
		int size = buses.size();
		Bus busRetrieved = buses.get(size -1);
		
		assertEquals(1,busManager.DeleteBus(busRetrieved));
		buses.remove(size -1);
		List<Bus> buses1 = busManager.getAllBuses();
		assertEquals(buses, buses1);
		} finally {
		    connection.rollback();
		    connection.close();
		    connMechanik.rollback();
		    connMechanik.close();
		}
	}
	@Test
	public void checkDeletingMechanik() throws SQLException{
		Connection connection = busManager.getConnection();
		connection.setAutoCommit(false);
		Connection connMechanik = mechanikManager.getConnection();
		connMechanik.setAutoCommit(false);
		try
		{
			Mechanik mechanik = new Mechanik(NAME_SPONSOR_1,SPONSOR_ABOUT_1);
			mechanikManager.addMechanik(mechanik);
			mechanikManager.addMechanik(mechanik);
			List<Mechanik> mechaniks = mechanikManager.getAllMechaniks();
			mechanik = mechaniks.get(mechaniks.size()-1);
			
			Bus bus = new Bus(NAME_1,ABOUT_1,mechanik.getId());
			
			assertEquals(1,busManager.addBus(bus));
			
			List<Bus> buses = busManager.getAllBuses();
			
			int size = buses.size();
			Bus busRetrieved = buses.get(size -1);
			
			assertEquals(1,busManager.DeleteMechanikFromBus(busRetrieved));
			
			List<Bus> buses2 = busManager.getAllBuses();
			Bus busRetrieved2  = buses2.get(buses2.size() - 1);
			
			assertEquals(0,busRetrieved2.getMechanik());
			
			
			
		} finally {
		    connection.rollback();
		    connection.close();
		    connMechanik.rollback();
		    connMechanik.close();
		}
	}
	
	@Test
	public void AddSponsor() throws SQLException{
		Connection connection = busManager.getConnection();
		connection.setAutoCommit(false);
		Connection connMechanik = mechanikManager.getConnection();
		connMechanik.setAutoCommit(false);
		try
		{
			Mechanik mechanik = new Mechanik(NAME_SPONSOR_1,SPONSOR_ABOUT_1);
			mechanikManager.addMechanik(mechanik);
			List<Mechanik> mechaniks = mechanikManager.getAllMechaniks();
			mechanik = mechaniks.get(mechaniks.size()-1);
			
			Bus bus = new Bus(NAME_1,ABOUT_1);
			
			assertEquals(1,busManager.addBus(bus));
			
			List<Bus> buses = busManager.getAllBuses();
			
			int size = buses.size();
			Bus busRetrieved = buses.get(size -1);
			
			assertEquals(1,busManager.AddMechanik(busRetrieved, mechanik));
			
			List<Bus> buses2 = busManager.getAllBuses();
			int size2 = buses2.size();
			Bus busRetrieved2 = buses2.get(size2-1);
			
			assertEquals(mechanik.getId(),busRetrieved2.getMechanik());
			
			
		} finally {
		    connection.rollback();
		    connection.close();
		    connMechanik.rollback();
		    connMechanik.close();
		}
	}
	@Test
	public void checkGettingBusesWithMechanik() throws SQLException
	{
		Connection connection = busManager.getConnection();
		connection.setAutoCommit(false);
		Connection connMechanik = mechanikManager.getConnection();
		connMechanik.setAutoCommit(false);
		try
		{
			Mechanik mechanik = new Mechanik(NAME_SPONSOR_1,SPONSOR_ABOUT_1);
			mechanikManager.addMechanik(mechanik);
			List<Mechanik> mechaniks = mechanikManager.getAllMechaniks();
			mechanik = mechaniks.get(mechaniks.size()-1);
			
			Bus bus = new Bus(NAME_1,ABOUT_1,mechanik.getId());
			assertEquals(1,busManager.addBus(bus));
			
			mechanik = new Mechanik(NAME_SPONSOR_1,SPONSOR_ABOUT_1);
			mechanikManager.addMechanik(mechanik);
			mechaniks = mechanikManager.getAllMechaniks();
			mechanik = mechaniks.get(mechaniks.size()-1);
			
			bus = new Bus(NAME_2,ABOUT_1,mechanik.getId());
			assertEquals(1,busManager.addBus(bus));
			
			List<Bus> buses = busManager.getAllBuses();
			List<Bus> selected = busManager.getAllEventsWithSelectedSponsor(mechanik);
			
			int test = 0;
			for (Bus bu : buses)
			{
				if(bu.getMechanik() != mechanik.getId())
					buses.remove(bu);
				else
					assertEquals(buses.get(test++),bu);
			}
			
			Assert.assertEquals(buses.size(),selected.size());
			
			
		} finally {
		    connection.rollback();
		    connection.close();
		    connMechanik.rollback();
		    connMechanik.close();
		}
	}
}
