package com.cloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanAccessor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class Controller1 {

	@Autowired
	SpanAccessor spanAccessor;
	private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
	
	@RequestMapping("/test")
	public String test() throws InterruptedException {
		LOGGER.debug("you called test");
		debug();
		return "from test";
	}
	
	public void debug(){
		Span span = spanAccessor.getCurrentSpan();
		LOGGER.info("span id is "+span.getSpanId()+" and trace id is "+span.getTraceId());
	}
	
}
