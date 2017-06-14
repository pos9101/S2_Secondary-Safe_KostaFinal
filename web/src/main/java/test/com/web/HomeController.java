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
import org.springframework.web.bind.annotation.RequestParam;

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
	public String arduinoIn(HttpServletRequest request,Model model) {
		
		logger.info("Arduino.in().."+request.getParameter("latitude")+" "+request.getParameter("longitude")+" "+request.getParameter("serialnum"));
		
		Date now = new Date();

		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd/HH:mm");
		logger.info(format.toString());
		logger.info(format.format(now));
		
		
		Integer flag=0;
		
		if((request.getParameter("serialnum"))!= null ){
			logger.info("if...");
			ArduinoVO ardu_vo = new ArduinoVO(request.getParameter("serialnum"), Double.parseDouble(request.getParameter("latitude")), Double.parseDouble(request.getParameter("longitude")),format.format(now));
			AccidentVO acci_vo = new AccidentVO("occured",request.getParameter("serialnum"));
			as.insert(ardu_vo);
			flag = acs.insert(acci_vo);
			
			//socket...
			for(int i=0 ; i<FinalServer.m_outputList.size() ; i++ ){
				FinalServer.m_outputList.get(i).println("placeAccident");
				FinalServer.m_outputList.get(i).flush();
				System.out.println("placeAccident");
			}
			
		}else{
			logger.info("else...");
		}
	
		model.addAttribute(flag);
		
		return "jsonView";
		
	}
	@RequestMapping(value = "/startsocket",method = RequestMethod.GET)
	public void startSocket(){
		FinalServer server = new FinalServer();
		server.start();
	}
	@RequestMapping(value = "/manage.do",method = RequestMethod.POST)
	public void accidentUpdate(@RequestParam String serialnum,@RequestParam String status){
		logger.info("accidentUpdate()...");
		
		System.out.println(serialnum + " "+ status);
		
		
		if(serialnum != null ){
			logger.info("if...");
			AccidentVO acci_vo = new AccidentVO(status, serialnum);
			
			int flag= acs.update(acci_vo);
			System.out.println("aaa");
			System.out.println("FinalServer.m_outputList.size()"+FinalServer.m_outputList.size());
			//socket...
			for(int i=0 ; i<FinalServer.m_outputList.size() ; i++ ){
				FinalServer.m_outputList.get(i).println("statusChanged");
				FinalServer.m_outputList.get(i).flush();
				System.out.println("statusChanged");
			}
			
		}else{
			logger.info("else...");
		}
		
	}
	
	@RequestMapping(value = "/webUpdate.in",method = RequestMethod.POST)
	public String webUpdate(@RequestParam String serialnum,@RequestParam String status){
		logger.info("accidentUpdate()...");
		
		System.out.println(serialnum + " "+ status);
		
		if(serialnum != null ){
			logger.info("if...");
			AccidentVO acci_vo = new AccidentVO(status, serialnum);
			int flag= acs.update(acci_vo);
			
		}else{
			logger.info("else...");
		}
		return "user/admin";
	}
	
	
	@RequestMapping(value = "/admin.do", method = RequestMethod.GET)
	public String admin() {
		return "user/admin";
	}
}
