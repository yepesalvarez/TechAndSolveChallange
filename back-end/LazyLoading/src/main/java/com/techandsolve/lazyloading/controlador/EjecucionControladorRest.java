package com.techandsolve.lazyloading.controlador;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
												@RequestParam (value = "cedula", required = true) long cedula) throws IOException{
		String archivoInputGuardado = ArchivoUtils.guardarArchivoTxt(archivoInput, rutaGuardadoArchivoInput);
		if(archivoInputGuardado == null || !ArchivoUtils.validarInputLazyLoading(archivoInputGuardado)) {
			return new ResponseEntity<>(getStatusBadRequest(), HttpStatus.BAD_REQUEST);
		}
		Ejecucion ejecucion = new Ejecucion();
		LocalDateTime ldt = LocalDateTime.now();
		String fechaEjecucion = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.getDefault()).format(ldt);
		ejecucion.setFecha(fechaEjecucion);
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
	
	@PostMapping(value = "/archivo")
	public ResponseEntity<?> obtenerArchivoEjecucion(@RequestParam (value = "ruta") String ruta) {
		try{
			File archivoFileSystem = new File(ruta);
			byte[] archivoTxt = Files.readAllBytes(archivoFileSystem.toPath());
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.TEXT_PLAIN);
		    headers.setContentLength(archivoTxt.length);
		    return new ResponseEntity<>(archivoTxt, headers, HttpStatus.OK);
		}catch(IOException e) {
			return new ResponseEntity<>(getStatusBadRequest(), HttpStatus.BAD_REQUEST);
		}
		
		
	}

}
