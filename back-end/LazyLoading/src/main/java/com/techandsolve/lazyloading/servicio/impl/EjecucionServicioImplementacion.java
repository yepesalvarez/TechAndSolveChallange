package com.techandsolve.lazyloading.servicio.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.techandsolve.lazyloading.dominio.Ejecucion;
import com.techandsolve.lazyloading.repositorio.EjecucionRepositorio;
import com.techandsolve.lazyloading.servicio.EjecucionServicio;
import com.techandsolve.lazyloading.util.ArchivoUtils;
import com.techandsolve.lazyloading.util.PesosItemsUtils;

@PropertySource("classpath:application.properties")
@Service
public class EjecucionServicioImplementacion implements EjecucionServicio {

	@Autowired
	EjecucionRepositorio ejecucionRepositorio;
	
	@Value("${ruta.filesystem.outputfile}")
	private String rutaGuardadoArchivoOutput;
	
	@Value("${nombre.filesystem.outputfile}") 
	private String nombreArchivoOutput;
	
	public static final Logger LOGGER = LoggerFactory.getLogger(EjecucionServicioImplementacion.class);
	
	@Override
	public Ejecucion guardarEjecucion(Ejecucion ejecucion) {
		List<List<Integer>> carguePerezoso = ArchivoUtils.convertirArchivoAlista(ejecucion.getLinkInput());
		List<String> carguePerezosoOutput = new ArrayList<>();
		for(int i = 0; i < carguePerezoso.size(); i++) {
			//Se ordena ascendentemente el grupo de items para analizar desde el más pesado al menos pesado
			List<Integer >dia = carguePerezoso.get(i).stream().sorted().collect(Collectors.toList());
			int viajes = calcularNroViajes(dia);
			int caso = i + 1;
			carguePerezosoOutput.add("Case #" + caso + ": " + viajes);
		}
		String archivoOutputGuardado = ArchivoUtils.guardarArchivoTxt(carguePerezosoOutput, rutaGuardadoArchivoOutput+nombreArchivoOutput);
		if (archivoOutputGuardado == null) {
			return null;
		}else {
			ejecucion.setLinkOutput(archivoOutputGuardado);
			return ejecucionRepositorio.save(ejecucion);
		}
	}
	
	private int calcularNroViajes(List<Integer> dia) {
		int itemsDisponibles = dia.size();
		int viajes = 0;
		int posicionItemDerecha = dia.size() - 1;
		while (itemsDisponibles > 0) {
			int pesoItemActual = dia.get(posicionItemDerecha);
			int itemsNecesarios = 0;
			int k = 0; 
			boolean matchPeso = false;
			while (!matchPeso && k < PesosItemsUtils.PESO_POR_ITEM.length) {
				if (pesoItemActual >= PesosItemsUtils.PESO_POR_ITEM[k][0]) {
					itemsNecesarios = PesosItemsUtils.PESO_POR_ITEM[k][1];
					matchPeso = true;
				}else {
					k++;
				}
			}
			if(itemsDisponibles >= itemsNecesarios) {
				viajes++;
				itemsDisponibles -= itemsNecesarios;
				posicionItemDerecha -= 1;
			}else {
				itemsDisponibles = 0;
			}
		}
		return viajes;
	}
	
	@Override
	public List<Ejecucion> obtenerTodaEjecucion() {
		return (List<Ejecucion>) ejecucionRepositorio.findAll();
	}

}
