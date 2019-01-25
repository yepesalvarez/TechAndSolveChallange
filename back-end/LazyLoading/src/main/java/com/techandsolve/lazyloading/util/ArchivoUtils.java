package com.techandsolve.lazyloading.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.techandsolve.lazyloading.util.excepciones.EjecucionException;

public class ArchivoUtils {
	
	private ArchivoUtils() {}
	
	public static final String ARCHIVO_NO_VALIDO = "Archivo con formato no valido";
	public static final String RUTA_NO_VALIDA = "No se puede guardar el archivo en ubicación indicada";
	
	public static final Logger LOGGER = LoggerFactory.getLogger(ArchivoUtils.class);
	
	public static String guardarArchivoTxt(MultipartFile archivo, String uri) {
		try {
			byte[] bytes = archivo.getBytes();
			String nombreArchivoOriginal = archivo.getOriginalFilename();
			String extension = obtenerExtensionArchivo(nombreArchivoOriginal);
			String nombreArchivoGuardar = crearRutaNombreArchivo(nombreArchivoOriginal, extension);
			if(nombreArchivoGuardar == null) {return null;}
			String ruta = uri + nombreArchivoGuardar;
			Path path = Paths.get(ruta);
			Files.write(path, bytes);
			return ruta;
		} catch (Exception e) {
			LOGGER.error(new EjecucionException(RUTA_NO_VALIDA).getMessage());
			return null;
		}
		
	}
	
	public static String guardarArchivoTxt(List<String> lista, String uri) {
		String extension = obtenerExtensionArchivo(uri);
		String ruta = crearRutaNombreArchivo(uri, extension);
		if(ruta == null) {return null;}
		try (FileWriter writer = new FileWriter(ruta)){
			for(String linea : lista) {
				writer.write(linea);
				writer.write(String.format("%n"));
			}
			return ruta;
		} catch (Exception e) {
			LOGGER.error(new EjecucionException(RUTA_NO_VALIDA).getMessage());
			return null;
		}
	}
	
	public static String obtenerExtensionArchivo(String nombreArchivo) {
		int posicionExtension = nombreArchivo.lastIndexOf('.');
		if(posicionExtension == -1) {
			return null;
		}
		return nombreArchivo.substring(posicionExtension, nombreArchivo.length());
	}
	
	public static String obtenerNombreArchivoSinExtension(String nombreCompletoArchivo, String extension) {
		int posicionExtension = nombreCompletoArchivo.lastIndexOf(extension);
		if(posicionExtension == -1) {
			return null;
		}
		return nombreCompletoArchivo.substring(0, posicionExtension);
	}
	
	public static String crearRutaNombreArchivo(String nombreOriginal, String extension) {
		if (extension == null || !extension.equalsIgnoreCase(".txt")) {
			LOGGER.error(new EjecucionException(ARCHIVO_NO_VALIDO).getMessage());
			return null;
		}
		return obtenerNombreArchivoSinExtension(nombreOriginal, extension)
				+ "_" + new Date().toString().replaceAll(":", "-") + extension.replace(" ", "");
	}
	
	public static boolean validarInputLazyLoading(String rutaArchivo) {
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(rutaArchivo)))) {
			/* Revisión inicial del archivo: que contenga solo números cada línea 
			 * y que estos se encuentren dentro de los rangos estipulados.
			 * NumberUtils es una clase de la dependencia Apache Commons para validar
			 * si es posible crear un número a partir de un String*/
			String dias = reader.readLine();
			Stream<String> lineasArchivo = reader.lines().filter(x -> !x.equals(""));
			if (!NumberUtils.isCreatable(dias) 
					|| Integer.parseInt(dias) > 500 || Integer.parseInt(dias) < 1 
					|| !lineasArchivo.skip(1).allMatch(x -> (NumberUtils.isCreatable(x)
							&& Integer.parseInt(x) >= 1 && Integer.parseInt(x) <= 100))) {
				LOGGER.error(new EjecucionException(ARCHIVO_NO_VALIDO).getMessage());
				return false;
			}
			return true;
		} catch (IOException e) {
			LOGGER.error(new EjecucionException(ARCHIVO_NO_VALIDO).getMessage());
			return false;
		}
		
	}
	
	public static List<List<Integer>> convertirArchivoAlista(String rutaArchivo) {
		List<List<Integer>> carguePerezoso = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(rutaArchivo)))){
		    int[] lines = reader.lines().filter(x -> !x.equals("")).mapToInt(Integer::parseInt).toArray();
		    int dias = lines[0];		    
		    int i = 1;
		    int contadorDias = 0;
		    while (i < lines.length && contadorDias < dias) {
		    	int cantidadItems = lines [i];
		    	carguePerezoso.add(new ArrayList<Integer>());
		    	i++;
		    	for (int j = 0; j < cantidadItems; j++) {
		    		carguePerezoso.get(contadorDias).add(lines[i]);
		    		i++;
		    	}
		    	contadorDias++;
		    }
		} catch (Exception e) {
			LOGGER.error(new EjecucionException(ARCHIVO_NO_VALIDO).getMessage());
		} 
		return carguePerezoso;
	
	}
	
}
