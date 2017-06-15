package test.com.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
		
		return "user/admin";
	}
	@RequestMapping(value = "/accident.do", method = RequestMethod.GET)
	public String accident() {
		logger.info("Welcome admin Page");
		logger.info("acs"+acs);
		//as.insert(null);
		
		return "user/admin";
	}
	@RequestMapping(value = {"/ajaxtest.do"}, method = RequestMethod.GET)
	public @ResponseBody String ajaxTest(Model model) {
		String temp = "{\"totalAccidents\":["
				+ "{\"serialnum\":\"SF17060800\",\"latitude\":0.0,\"longitude\":0.0,\"atime\":\"2017.06.12/18:55\",\"status\":\"occured\"},"
				+ "{\"serialnum\":\"SF17061056\",\"latitude\":37.40443263,\"longitude\":127.11633334,\"atime\":\"2017.06.12/18:58\",\"status\":\"occured\"},"
				+ "{\"serialnum\":\"SF17061220\",\"latitude\":31.12554,\"longitude\":53.12666,\"atime\":\"2017.06.12/18:49\",\"status\":\"occured\"},"
				+ "{\"serialnum\":\"SF17061221\",\"latitude\":81.12554,\"longitude\":83.12666,\"atime\":\"2017.06.12/18:52\",\"status\":\"occured\"}"
				+ "]}";
		
		return temp;
	}
	
	
}
