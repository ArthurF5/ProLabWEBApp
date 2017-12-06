package com.moliveiralucas.modelo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Laboratorio")
public class Laboratorio {
	private Integer labID;
	private String laboratorio;
	private ArrayList<Endereco> endereco;
	
	public Integer getLabID() {
		return labID;
	}
	public void setLabID(Integer labID) {
		this.labID = labID;
	}
	public String getLaboratorio() {
		return laboratorio;
	}
	public void setLaboratorio(String laboratorio) {
		this.laboratorio = laboratorio;
	}
	public ArrayList<Endereco> getEndereco() {
		return endereco;
	}
	public void setEndereco(ArrayList<Endereco> endereco) {
		this.endereco = endereco;
	}
}
