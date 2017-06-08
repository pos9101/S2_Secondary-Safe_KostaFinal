package test.com.web;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
public class JsonController {

	private static final Logger logger = LoggerFactory.getLogger(JsonController.class);
	@Autowired
	private ArduinoService as;
	
	@Autowired
	private AccidentService acs;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = { "/arduinos.json" }, method = RequestMethod.GET)
	public String arduinos(Model model) {
		logger.info("arduinos.json");

		Map<String, List<ArduinoVO>> map = new HashMap<String, List<ArduinoVO>>();
		List<ArduinoVO> list = as.select();

		map.put("arduinos", list);
		
		model.addAllAttributes(map);

		return "jsonView";
	}
	
	@RequestMapping(value = { "/accidents.json" }, method = RequestMethod.GET)
	public String accidents(Model model) {
		logger.info("accidents.json");

		Map<String, List<AccidentVO>> map = new HashMap<String, List<AccidentVO>>();
		List<AccidentVO> list = acs.select();

		map.put("accidents", list);
		
		model.addAllAttributes(map);

		return "jsonView";
	}

}
