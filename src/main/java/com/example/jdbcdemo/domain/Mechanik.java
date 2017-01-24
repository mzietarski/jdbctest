package com.example.jdbcdemo.domain;


public class Mechanik {
	
	private int id;
	private String imie;
	private String stanowisko;
	
	
	
	
	public Mechanik(String imie, String stanowisko)
	{
		this.imie = imie;
		this.stanowisko = stanowisko;
	}
	
	public Mechanik(){
		
	}
	
	//POBIERANIE
	public int getId() {
		return id;
	}
	
	public String getImie() {
		return imie;
	}
	
	public String getStanowisko() {
		return stanowisko;
	}
	
	//USTAWIANEI
	public void setId(int id) {
		this.id = id;
	}
	
	public void setImie(String imie) {
		this.imie = imie;
	}
	
	public void setStanowisko(String stanowisko) {
		this.stanowisko = stanowisko;
	}
	

}

