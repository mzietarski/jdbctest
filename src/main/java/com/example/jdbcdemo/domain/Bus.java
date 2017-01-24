package com.example.jdbcdemo.domain;


public class Bus {
	
	private int id;
	private String nrlini;
	private String marka;
	
	private int mechanik;
	
	
	
	public Bus(String nrlini, String marka, int mechanik)
	{
		this.nrlini = nrlini;
		this.marka = marka;
		this.mechanik = mechanik;
	}
	
	public Bus(String nrlini, String marka)
	{
		this.nrlini = nrlini;
		this.marka = marka;
	}
	
	public Bus(){
		
	}

	//METODY POBIERAJ�CE
	public int getId() {
		return id;
	}
	
	public String getNrlini() {
		return nrlini;
	}
	
	public String getMarka() {
		return marka;
	}	
	
	public int getMechanik() {
		return mechanik;
	}
	
	//METODY USTAWIAJ�CE
	public void setId(int id) {
		this.id = id;
	}
	
	public void setNrlini(String nrlini) {
		this.nrlini = nrlini;
	}
	
	public void setMarka(String marka) {
		this.marka = marka;
	}
	
	public void setMechanik(int mechanik) {
		this.mechanik = mechanik;
	}
	
}
