package com.example.jdbcdemo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.jdbcdemo.domain.Bus;
import com.example.jdbcdemo.domain.Mechanik;

public class BusManager {
	
		private Connection connection;

		private String url = "jdbc:hsqldb:hsql://localhost/workdb";

		private String createTableBus = "CREATE TABLE Bus(id INTEGER IDENTITY PRIMARY KEY, nrlini varchar(20), mechanik BIGINT, marka varchar(100))";

		private PreparedStatement addBusStmt;
		private PreparedStatement editBusStmt;
		private PreparedStatement deleteBusStmt;
		private PreparedStatement selectBusByIdStmt;
		private PreparedStatement getBusesWithMechanikStmt;
		private PreparedStatement deleteMechanikStmt;
		private PreparedStatement addMechanikStmt; 
		private PreparedStatement getAllBusesStmt;

		private Statement statement;

		public BusManager() {
			try {
				connection = DriverManager.getConnection(
				          "jdbc:hsqldb:file:/tmp/testdb;ifexists=false;shutdown=true;readonly=true", "SA", "");
						//DriverManager.getConnection(url);
				statement = connection.createStatement();

				ResultSet rs = connection.getMetaData().getTables(null, null, null,
						null);
				boolean tableExists = false;
				while (rs.next()) {
					if ("Bus".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
						tableExists = true;
						break;
					}
				}

				if (!tableExists)
					statement.executeUpdate(createTableBus);

				getAllBusesStmt = connection
						.prepareStatement("SELECT * FROM Bus");
				deleteBusStmt = connection
						.prepareStatement("DELETE FROM Bus Where id = ?");
				editBusStmt = connection
						.prepareStatement("UPDATE Bus SET nrlini = ?, marka = ?,  mechanik = ? WHERE id = ?");
				deleteMechanikStmt = connection
						.prepareStatement("UPDATE Bus Set mechanik= NULL WHERE id= ?");
				addBusStmt = connection
						.prepareStatement("INSERT INTO Bus (nrlini, marka, mechanik ) VALUES (?, ?, ?)");
				addMechanikStmt = connection
						.prepareStatement("UPDATE Bus SET mechanik= ?  WHERE id = ?");
				getBusesWithMechanikStmt = connection
						.prepareStatement("SELECT * FROM Bus Where mechanik= ?");
				selectBusByIdStmt = connection
						.prepareStatement("SELECT * FROM Bus WHERE id = ?");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		Connection getConnection() {
			return connection;
		}

		public int DeleteBus(Bus bus) {
			int count = 0;
			try {
				deleteBusStmt.setInt(1, bus.getId());
				count = deleteBusStmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return count;
		}
		
		int EditBus(Bus bus){
			int count = 0;
			try {
				editBusStmt.setInt(4, bus.getId());
				editBusStmt.setString(1, bus.getNrlini());
				editBusStmt.setString(2, bus.getMarka());
				editBusStmt.setInt(3, bus.getMechanik());
				count  = editBusStmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return count;
		}
		
	     int DeleteMechanikFromBus(Bus bus) {
			int count = 0;
			try {
				deleteMechanikStmt.setInt(1, bus.getId());
				count  = deleteMechanikStmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return count;
		} 
	    public int AddMechanik(Bus bus, Mechanik mechanik)
	    {
	    	int count = 0;
			try {
				addMechanikStmt.setInt(1, mechanik.getId());
				addMechanikStmt.setInt(2, bus.getId());

				count = addMechanikStmt.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return count;
	    }

		public int addBus(Bus bus) {
			int count = 0;
			try {
				addBusStmt.setString(1, bus.getNrlini());
				addBusStmt.setString(2, bus.getMarka());
				addBusStmt.setInt(3, bus.getMechanik());

				count = addBusStmt.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return count;
		}
		public List<Bus> selectBusById(int id)
		{
			List<Bus> buses = new ArrayList<Bus>();

			try {
				selectBusByIdStmt.setInt(1,id);
				ResultSet rs = selectBusByIdStmt.executeQuery();

				while (rs.next()) {
					Bus bu = new Bus();
					bu.setId(rs.getInt("id"));
					bu.setNrlini(rs.getString("nrlini"));
					bu.setMarka(rs.getString("marka"));
					bu.setMechanik(rs.getInt("mechanik"));
					buses.add(bu);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return buses;
		}
		
		public List<Bus> getAllEventsWithSelectedSponsor(Mechanik mechanik) {
			List<Bus> buses = new ArrayList<Bus>();

			try {
				getBusesWithMechanikStmt.setInt(1,mechanik.getId());
				ResultSet rs = getBusesWithMechanikStmt.executeQuery();

				while (rs.next()) {
					Bus bu = new Bus();
					bu.setId(rs.getInt("id"));
					bu.setNrlini(rs.getString("nrlini"));
					bu.setMarka(rs.getString("marka"));
					bu.setMechanik(rs.getInt("mechanik"));
					buses.add(bu);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return buses;
		}

		public List<Bus> getAllBuses() {
			List<Bus> buses = new ArrayList<Bus>();

			try {
				ResultSet rs = getAllBusesStmt.executeQuery();

				while (rs.next()) {
					Bus bu = new Bus();
					bu.setId(rs.getInt("id"));
					bu.setNrlini(rs.getString("nrlini"));
					bu.setMarka(rs.getString("marka"));
					bu.setMechanik(rs.getInt("mechanik"));
					buses.add(bu);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return buses;
		} 
}
