package com.onez.sop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceConfig {
	@Value("${microservice.minBalance}")
	private String exampleProperty;

	public String getExampleProperty() {
		return exampleProperty;
	}

}