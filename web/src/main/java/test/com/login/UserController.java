package test.com.login;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Resource(name="shaEncoder")
	private ShaEncoder encoder;
	
	@Resource(name="userDaoService")
	private UserDaoService dao;
	
	@RequestMapping("/user/loginPage")
	public String loginPage(){
		return "/user/loginPage";
	}
	@RequestMapping("/user/jquery-3.1.1.js")
	public String jquery(){
		logger.info("Welcome jquery Page");
		return "/user/jquery-3.1.1.js";
	}
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/user/admin", method = RequestMethod.GET)
	public String admin() {
		logger.info("Welcome admin Page");
		
		return "/user/admin";
	}
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/user/member", method = RequestMethod.GET)
	public String member() {
		logger.info("Welcome memeber Page");
		
		return "/user/member";
	}
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/user/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		logger.info(encoder.encoding("test"));
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "/user/home";
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/checkAuth", method = RequestMethod.GET)
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	public String checkAuth(Locale locale, Model model, Authentication auth) {
		UserDetailsVO vo = (UserDetailsVO) auth.getPrincipal();
		logger.info("Welcome checkAuth! Authentication is {}.", auth);
		logger.info("UserDetailsVO == {}.", vo);
		model.addAttribute("auth", auth );
		model.addAttribute("vo", vo );
		return "checkAuth";
	}
	
	@RequestMapping("/user/denied")
	public String denied(Model model, Authentication auth, HttpServletRequest req){
		AccessDeniedException ade = (AccessDeniedException) req.getAttribute(WebAttributes.ACCESS_DENIED_403);
		logger.info("ex : {}",ade);
		model.addAttribute("auth", auth);
		model.addAttribute("errMsg", ade);
		return "/user/denied";
	}
	
	@RequestMapping(value = "/user/insertUser",method=RequestMethod.POST)
	public String insertUser(@RequestParam("email")String email, @RequestParam("passwd")String passwd, @RequestParam("authority")String authority){
		String dbpw = encoder.saltEncoding(passwd, email);
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("email", email);
		paramMap.put("passwd", dbpw);
		paramMap.put("authority", authority);
		int result = dao.insertUser(paramMap);
		logger.info("result ===> {}", result);
		return "/user/loginPage";
	}
	

}
