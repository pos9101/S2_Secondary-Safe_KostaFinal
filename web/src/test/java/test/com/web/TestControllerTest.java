package test.com.web;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
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
	
	@Autowired
	SqlSession sqlSession;

	@Test
	public void asTest() {

		logger.info("asTest()..." + as);
		// assertNull(as);
		assertNotNull(as);
		ArduinoVO vo = new ArduinoVO(1, "SF17060800", "bbbb2", "dfdfdf", "dfdf");
		assertNotNull(as.insert(vo));
	}
	
	@Test
	public void acsTest(){
		logger.info("acsTest()..."+acs);
		//assertNull(acs);
		assertNotNull(acs);
		AccidentVO vo = new AccidentVO(1, "occured","SF17060800");
		assertNotNull(acs.insert(vo));
	}
	
	@Test
	public void selectArduino(){
		logger.info("selectArduino()...");
		assertNotNull(as);
		List<ArduinoVO> list = as.select();
		
		
		Iterator<ArduinoVO> iter = list.iterator();
		while(iter.hasNext()){
			ArduinoVO vo = iter.next();
			iter.remove();
			logger.info(vo.getNum()+" "+vo.getLatitude()+" "+vo.getLongitude()+" "+vo.getSerialnum()+ " " + vo.getAtime());
		}
	}
	
	@Test
	public void selectAccident(){
		logger.info("selectAccident()...");
		assertNotNull(acs);
		List<AccidentVO> list = acs.select();
		
		Iterator<AccidentVO> iter = list.iterator();
		while(iter.hasNext()){
			AccidentVO vo = iter.next();
			iter.remove();
			logger.info(vo.getNum()+" "+ vo.getStatus()+ " "+ vo.getSerialnum());
		}
	}
	
	@Test
	public void selectData(){
		logger.info("selectData()...");
		List<DataVO> list = null;
		list = sqlSession.selectList("selectDatas");
		
		Iterator<DataVO> iter = list.iterator();
		while(iter.hasNext()){
			DataVO vo = iter.next();
			iter.remove();
			logger.info(vo.getSerialnum()+" "+vo.getLatitude()+" "+vo.getLongitude()+" "+vo.getStatus()+" "+vo.getAtime());
		}
	}
	
}
