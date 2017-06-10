package test.com.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ArduinoService as;
	
	@Autowired
	private AccidentService acs;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = {"/","/index.do"}, method = RequestMethod.GET)
	public String index(Locale locale, Model model) {
		logger.info("Welcome index! The client locale is {}.", locale);
		
		return "index";
	}
	@RequestMapping(value = "/arduino.in", method = RequestMethod.GET)
	public void arduinoIn(HttpServletRequest request) {
		
		logger.info("Arduino.in().."+request.getParameter("latitude")+" "+request.getParameter("longitude")+" "+request.getParameter("serialnum"));
		
		Date now = new Date();

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm");
		logger.info(format.toString());
		logger.info(format.format(now));
		
		if((request.getParameter("serialnum"))!= null ){
			logger.info("if...");
			ArduinoVO vo = new ArduinoVO(request.getParameter("serialnum"), Double.parseDouble(request.getParameter("latitude")), Double.parseDouble(request.getParameter("longitude")),format.format(now));
			as.insert(vo);
		}else{
			logger.info("else...");
		}
	}
}
