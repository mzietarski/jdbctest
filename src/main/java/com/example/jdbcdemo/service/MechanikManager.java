package com.example.jdbcdemo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.jdbcdemo.domain.Mechanik;

public class MechanikManager {

	private Connection connection;

	private String url = "jdbc:hsqldb:hsql://localhost/workdb";

	private String createTableMechanik = "CREATE TABLE Mechanik(id INTEGER IDENTITY PRIMARY KEY, imie varchar(20), stanowisko varchar(100))";

	private PreparedStatement addMechanikStmt;
	private PreparedStatement editMechanikStmt;
	private PreparedStatement deleteMechanikStmt;
	private PreparedStatement getAllMechaniksStmt;

	private Statement statement;

	public MechanikManager() {
		try {
			connection = DriverManager.getConnection(
			          "jdbc:hsqldb:file:/tmp/testdb;ifexists=false", "SA", "");
					//DriverManager.getConnection(url);
			statement = connection.createStatement();

			ResultSet rs = connection.getMetaData().getTables(null, null, null,
					null);
			boolean tableExists = false;
			while (rs.next()) {
				if ("Mechanik".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableExists = true;
					break;
				}
			}

			if (!tableExists)
				statement.executeUpdate(createTableMechanik);

			addMechanikStmt = connection
					.prepareStatement("INSERT INTO Mechanik (imie, stanowisko) VALUES (?, ?)");
			editMechanikStmt = connection
					.prepareStatement("UPDATE Mechanik SET imie= ? ,stanowisko= ? WHERE id = ?;");
			deleteMechanikStmt = connection
					.prepareStatement("DELETE FROM Mechanik WHERE id=?");
			getAllMechaniksStmt = connection
					.prepareStatement("SELECT * FROM Mechanik");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	Connection getConnection() {
		return connection;
	}

	int DeleteMechanik(Mechanik mechanik) {
		int count = 0;
		try {
			deleteMechanikStmt.setInt(1, mechanik.getId());
			count  =  deleteMechanikStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	int EditMechanik(Mechanik mechanik){
		int count = 0;
		try {
			editMechanikStmt.setInt(3, mechanik.getId());
			editMechanikStmt.setString(1, mechanik.getImie());
			editMechanikStmt.setString(2, mechanik.getStanowisko());
			count  = editMechanikStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int addMechanik(Mechanik mechanik) {
		int count = 0;
		try {
			addMechanikStmt.setString(1, mechanik.getImie());
			addMechanikStmt.setString(2, mechanik.getStanowisko());

			count = addMechanikStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public List<Mechanik> getAllMechaniks() {
		List<Mechanik> mechaniks = new ArrayList<Mechanik>();

		try {
			ResultSet rs = getAllMechaniksStmt.executeQuery();

			while (rs.next()) {
				Mechanik p = new Mechanik();
				p.setId(rs.getInt("id"));
				p.setImie(rs.getString("imie"));
				p.setStanowisko(rs.getString("stanowisko"));
				mechaniks.add(p);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mechaniks;
	}

}
