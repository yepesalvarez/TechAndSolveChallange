package com.techandsolve.lazyloading.servicio;

import java.util.List;

import com.techandsolve.lazyloading.dominio.Ejecucion;

public interface EjecucionServicio {
	
	Ejecucion guardarEjecucion(Ejecucion ejecucion);
	List<Ejecucion> obtenerTodaEjecucion();

}
