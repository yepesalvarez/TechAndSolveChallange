package com.techandsolve.lazyloading.controlador.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.techandsolve.lazyloading.LazyLoadingApplication;
import com.techandsolve.lazyloading.controlador.EjecucionControladorRest;
import com.techandsolve.lazyloading.dominio.Ejecucion;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = {LazyLoadingApplication.class})
@Transactional
public class EjecucionControladorRestTest {
	
	private EjecucionControladorRest ejecucionControladorRest;
	
	private Ejecucion ejecucion;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Before
	public void setUp() {
		
		ejecucionControladorRest = new EjecucionControladorRest();
		
		this.ejecucion = new Ejecucion();
		this.ejecucion.setCedula(123L);
		this.ejecucion.setFecha("2019-01-24 12:00");
		this.ejecucion.setLinkInput("lazy_loading_example_input_ThuJan2412-00-15COT2019.txt");
		this.ejecucion.setLinkOutput("lazy_loading_output_Thu Jan 24 12-00-15 COT 2019.txt");
		
		entityManager.persistAndFlush(ejecucion);
	
	}

	@Test
	public void testGuardarEjecucion() {
		fail("Not yet implemented");
	}

	@Test
	public void testObtenerTodaEjecucion() {
		
		List<Ejecucion> ejecuciones = new ArrayList<>();
		ejecuciones.add(ejecucion);
		ResponseEntity<?> expected = new ResponseEntity<>(ejecuciones, HttpStatus.OK); 
		ResponseEntity<?> actual = ejecucionControladorRest.obtenerTodaEjecucion();
		assertEquals(expected, actual);
		assertThat(actual).isNotNull();
		assertEquals(actual.getStatusCode(), expected.getStatusCode());
		assertEquals(actual.getStatusCodeValue(), (expected.getStatusCodeValue()));
		assertEquals(actual.getBody(), ejecuciones);
		
	}

	@Test
	public void testObtenerArchivoEjecucion() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStatusOK() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStatusBadRequest() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStatusNoContent() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStatusInternalServerErr() {
		fail("Not yet implemented");
	}

}
