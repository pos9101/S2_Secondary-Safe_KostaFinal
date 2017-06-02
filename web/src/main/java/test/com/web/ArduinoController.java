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
public class ArduinoController {
	
	private static final Logger logger = LoggerFactory.getLogger(ArduinoController.class);
	@Autowired
	ArduinoService as;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/arduino.do", method = RequestMethod.GET)
	public String index() {
		logger.info("Welcome admin Page");
		logger.info("as"+as);
		//as.insert(null);
		
		return "manager/admin";
	}
	
}
