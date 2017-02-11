package com.cloud;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanAccessor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Controller {

	@Autowired
	SpanAccessor spanAccessor;
	@Autowired
	RestTemplate restTemplate;
	int port = 8080;
	//Log LOGGER = LogFactory.getLog(Controller.class);
	private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
	
	@RequestMapping("/")
	public String hi() throws InterruptedException {
		LOGGER.debug("you called hi");
		debug();
		HttpEntity<String> entity = new HttpEntity<String>("");
		ResponseEntity<String> response = this.restTemplate
				.exchange("http://localhost:" + this.port + "/cloud/hi2",HttpMethod.GET, entity, String.class);
		return "hi/" + response.getBody();
	}

	@RequestMapping("/hi2")
	public String hi2() throws InterruptedException {
		LOGGER.debug("you called hi2");
		debug();
		HttpEntity<String> entity = new HttpEntity<String>("");
		ResponseEntity<String> response = this.restTemplate
				.exchange("http://localhost:" + this.port + "/cloud/hi3",HttpMethod.GET, entity, String.class);
		return response.getBody();
	}
	
	@RequestMapping("/hi3")
	public String hi3() throws InterruptedException {
		LOGGER.debug("you called hi2");
		debug();
		HttpEntity<String> entity = new HttpEntity<String>("");
		ResponseEntity<String> response = this.restTemplate
				.exchange("http://localhost:" + this.port + "/cloud/test",HttpMethod.GET, entity, String.class);
		return response.getBody();
	}
	
	public void debug(){
		Span span = spanAccessor.getCurrentSpan();
		List<Long> parents = span.getParents();
		LOGGER.info("parents = " + parents);
		LOGGER.info("span id is "+span.getSpanId()+" and trace id is "+span.getTraceId());
	}
	
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
