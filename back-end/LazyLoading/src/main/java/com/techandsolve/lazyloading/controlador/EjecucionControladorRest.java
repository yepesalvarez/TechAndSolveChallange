package com.techandsolve.lazyloading.controlador;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techandsolve.lazyloading.dominio.Ejecucion;
import com.techandsolve.lazyloading.servicio.EjecucionServicio;
import com.techandsolve.lazyloading.util.ArchivoUtils;

@RestController
@RequestMapping(value = "/api")
public class EjecucionControladorRest extends AbstractControladorRest {
	
	@Value("${ruta.filesystem.inputfile}") 
	private String rutaGuardadoArchivoInput;
			
	@Autowired
	EjecucionServicio ejecucionServicio;
	
	@PostMapping(value = "/ejecucion")
	public ResponseEntity<?> guardarEjecucion(@RequestParam (value = "input", required = true) MultipartFile archivoInput,
												@RequestParam (value = "cedula", required = true) long cedula) throws ParseException{
		String archivoInputGuardado = ArchivoUtils.guardarArchivoTxt(archivoInput, rutaGuardadoArchivoInput);
		if(archivoInputGuardado == null || !ArchivoUtils.validarInputLazyLoading(archivoInputGuardado)) {
			return new ResponseEntity<>(getStatusBadRequest(), HttpStatus.BAD_REQUEST);
		}
		Ejecucion ejecucion = new Ejecucion();
		ejecucion.setFecha(new Date());
		ejecucion.setCedula(cedula);
		ejecucion.setLinkInput(archivoInputGuardado);
		if(ejecucionServicio.guardarEjecucion(ejecucion) == null) {
			return new ResponseEntity<>(getStatusBadRequest(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(ejecucion, HttpStatus.OK);
				
	}
	
	@GetMapping(value = "/ejecucion")
	public ResponseEntity<?> obtenerTodaEjecucion(){
		List<Ejecucion> ejecuciones = ejecucionServicio.obtenerTodaEjecucion();
		if (ejecuciones.isEmpty()) {
			return new ResponseEntity<>(getStatusNoContent(), HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(ejecuciones, HttpStatus.OK);
	}

}
