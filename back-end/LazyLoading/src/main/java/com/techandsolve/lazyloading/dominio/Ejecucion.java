package com.techandsolve.lazyloading.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Persistable;

@Entity
@Table (name = "ejecuciones")
public class Ejecucion implements Persistable<Long> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Long id;
	
	@NotNull
	@Column(name = "fecha")
	String fecha;
	
	@NotNull
	@Column(name = "cedula")
	long cedula;
	
	@Column(name = "link_input")
	String linkInput;
	
	@Column(name = "link_output")
	String linkOutput;

	@Override
	public Long getId() {
		return id;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public long getCedula() {
		return cedula;
	}

	public void setCedula(long cedula) {
		this.cedula = cedula;
	}

	public String getLinkInput() {
		return linkInput;
	}

	public void setLinkInput(String linkInput) {
		this.linkInput = linkInput;
	}

	public String getLinkOutput() {
		return linkOutput;
	}

	public void setLinkOutput(String linkOutput) {
		this.linkOutput = linkOutput;
	}

	@Override
	public boolean isNew() {
		return getId() == null;
	}

}
