package com.onez.sop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Component
@RestController
public class ServiceDiscoveryClient {

	@Autowired
	private DiscoveryClient discoveryClient;

	@GetMapping("/student/{schoolname}")
	public String getStudent(@PathVariable String schoolname) {
		System.out.println("Getting School details for " + schoolname);
		RestTemplate restTemplate = new RestTemplate();
		List<ServiceInstance> instances = discoveryClient.getInstances("studentservice");
		String serviceUri = String.format("%s/student/%s", instances.get(0).getUri().toString(), schoolname);
		System.out.println(serviceUri);
		ResponseEntity<String> restExchange = restTemplate.exchange(serviceUri, HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, schoolname);
		String response = restExchange.getBody();
		return "School Name -  " + schoolname + " \n Student Details " + response;
	}

	@GetMapping("/service-instances/{applicationName}")
	public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
		return this.discoveryClient.getInstances(applicationName);
	}
}