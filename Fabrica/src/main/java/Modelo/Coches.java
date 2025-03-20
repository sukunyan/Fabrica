package Modelo;

import java.time.LocalDate;

public class Coches {
	
	private String marca;
	private String modelo;
	private LocalDate fecha;
	private String matricula;
	private int numChasis;
	
	public Coches(){
		this.marca = "";
		this.modelo = "";
		this.fecha = null;
		this.matricula = "";
		this.numChasis = 0;
	}


	public Coches(String marca, String modelo, LocalDate fecha, String matricula, int numChasis) {
		super();
		this.marca = marca;
		this.modelo = modelo;
		this.fecha = fecha;
		this.matricula = matricula;
		this.numChasis = numChasis;
	}


	/**
	 * @return the marca
	 */
	public String getMarca() {
		return marca;
	}


	/**
	 * @param marca the marca to set
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}


	/**
	 * @return the modelo
	 */
	public String getModelo() {
		return modelo;
	}


	/**
	 * @param modelo the modelo to set
	 */
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}


	/**
	 * @return the fecha
	 */
	public LocalDate getFecha() {
		return fecha;
	}


	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}


	/**
	 * @return the matricula
	 */
	public String getMatricula() {
		return matricula;
	}


	/**
	 * @param matricula the matricula to set
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}


	/**
	 * @return the numChasis
	 */
	public int getNumChasis() {
		return numChasis;
	}


	/**
	 * @param numChasis the numChasis to set
	 */
	public void setNumChasis(int numChasis) {
		this.numChasis = numChasis;
	}
	
	
	
}
