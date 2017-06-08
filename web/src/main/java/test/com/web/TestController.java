package test.com.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class TestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	@Autowired
	ArduinoService as;
	
	@Autowired
	AccidentService acs;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/arduino.do", method = RequestMethod.GET)
	public String arduino() {
		logger.info("Welcome admin Page");
		logger.info("as"+as);
		//as.insert(null);
		
		return "manager/admin";
	}
	@RequestMapping(value = "/accident.do", method = RequestMethod.GET)
	public String accident() {
		logger.info("Welcome admin Page");
		logger.info("acs"+acs);
		//as.insert(null);
		
		return "manager/admin";
	}
	
	
	
}
