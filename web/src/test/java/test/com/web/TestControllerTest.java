package test.com.web;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)

//@ContextConfiguration(locations="file:src/main/webapp/WEB-INF/spring/root-context.xml") 
//변한 root-context.xml 과 servlet-context.xml 을 
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml","file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
public class TestControllerTest {
	

	private static final Logger logger = LoggerFactory.getLogger(TestControllerTest.class);
	@Autowired
	private ArduinoService as;
	
	@Autowired
	private AccidentService acs;

	@Test
	public void asTest() {

		logger.info("asTest()..." + as);
		// assertNull(as);
		assertNotNull(as);
		ArduinoVO vo = new ArduinoVO(11, "aaaa3", "bbbb2", "dfdfdf", "dfdf");
		assertNotNull(as.insert(vo));
	}
	
	@Test
	public void acsTest(){
		logger.info("acsTest()..."+acs);
		//assertNull(acs);
		assertNotNull(acs);
		AccidentVO vo = new AccidentVO(1, "occured","SA1255");
		assertNotNull(acs.insert(vo));
	}
	
}
