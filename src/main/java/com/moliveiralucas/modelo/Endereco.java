package com.moliveiralucas.modelo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Endereco")
public class Endereco {
	private Integer endID;
	private Integer labID;
	private String logradouro;
	private String numero;
	private Integer cidade;
	private Integer estado;
	
	public Integer getEndID() {
		return endID;
	}

	public void setEndID(Integer endID) {
		this.endID = endID;
	}

	public Integer getCidade() {
		return cidade;
	}

	public void setCidade(Integer cidade) {
		this.cidade = cidade;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Integer getLabID() {
		return labID;
	}

	public void setLabID(Integer labID) {
		this.labID = labID;
	}
}
