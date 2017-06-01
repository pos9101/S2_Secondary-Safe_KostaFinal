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
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})

public class ArduinoTest {
	private static final Logger logger = LoggerFactory.getLogger(ArduinoTest.class);
	@Autowired
	private ArduinoService as;
	
	@Test
	public void asTest() {
		logger.info("asTest()..."+as);
//		assertNull(as);
		assertNotNull(as);
		ArduinoVO vo  = new ArduinoVO(11,"aaaa2", "bbbb2","dfdfdf","dfdf");
		assertNotNull(as.insert(vo));
	}

}
