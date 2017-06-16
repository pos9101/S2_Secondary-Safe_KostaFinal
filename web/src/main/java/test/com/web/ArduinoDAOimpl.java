package test.com.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public class ArduinoDAOimpl implements ArduinoDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(ArduinoDAOimpl.class);

	@Autowired
	SqlSession sqlSession;
	
	@Override
	public int insert(ArduinoVO vo) {
		System.out.println("ArduinoDAOimpl:insert()...");
		System.out.println(vo.getSerialnum());
		System.out.println(vo.getLatitude());
		System.out.println(vo.getLongitude());
		System.out.println(vo.getAtime());
		
		int flag = 0;
		
		flag = sqlSession.insert("insert", vo);
		
		return flag;
	}

	@Override
	public int update(ArduinoVO vo) {
		System.out.println(vo.getSerialnum());
		System.out.println(vo.getLatitude());
		System.out.println(vo.getLongitude());
		System.out.println(vo.getAtime());
		
		int flag = 0;
		
		flag = sqlSession.update("update", vo);
		
		return flag;
	}

	@Override
	public int delete(ArduinoVO vo) {
		System.out.println(vo.getSerialnum());
		
		int flag = 0;
		
		flag = sqlSession.delete("delete",vo.getSerialnum());
		
		return flag;
	}

	@Override
	public ArduinoVO search(ArduinoVO vo) {
		logger.info("search()..." + vo.getSerialnum());
		ArduinoVO avo = sqlSession.selectOne("search", vo);
		
		return avo;
	}

	@Override
	public List<ArduinoVO> select() {
		List<ArduinoVO> list = null;
		
		list = sqlSession.selectList("select");
		
		return list;
	}

	@Override
	public List<ArduinoVO> search(String searchKey, String searchWord) {
		logger.info("search()...searchKey"+searchKey);
		logger.info("search()...searchWord"+searchWord);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("searchKey", searchKey);
		map.put("searchWord", "%" + searchWord +"%");
		
		List<ArduinoVO> list = sqlSession.selectList("searchList", map);
		
		return list;
	}

}
