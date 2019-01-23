package com.techandsolve.lazyloading.controlador;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
public abstract class AbstractControladorRest {
	
	@Value("${controller.status.ok}") private String statusOK;
	@Value("${controller.status.bad_request}") private String statusBadRequest;
	@Value("${controller.status.no_content}") private String statusNoContent;
	@Value("${controller.status.internal_server_error}") private String statusInternalServerErr;
	
	public String getStatusOK() {
		return statusOK;
	}
	public String getStatusBadRequest() {
		return statusBadRequest;
	}
	public String getStatusNoContent() {
		return statusNoContent;
	}
	public String getStatusInternalServerErr() {
		return statusInternalServerErr;
	}

}
