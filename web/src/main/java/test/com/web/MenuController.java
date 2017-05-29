package test.com.web;


import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MenuController {
	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
	
	@RequestMapping(value = "/about.do", method = RequestMethod.GET)
	public String about() {
		logger.info("about.do");
		
		return "menu/about";
	}
	@RequestMapping(value = "/references.do", method = RequestMethod.GET)
	public String references() {
		logger.info("references.do");
		
		return "menu/references";
	}
	@RequestMapping(value = "/map.do", method = RequestMethod.GET)
	public String map() {
		logger.info("map.do");
		
		return "menu/map";
	}
}
