package test.com.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccidentDAOimpl implements AccidentDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(AccidentDAOimpl.class);
	
	@Autowired
	SqlSession sqlSession;

	@Override
	public int insert(AccidentVO vo) {
		System.out.println("AccidentDAOimpl:insert()...");
		System.out.println(vo.getNum());
		System.out.println(vo.getStatus());
		System.out.println(vo.getSerialnum());
		
		int flag = 0;
		
		flag = sqlSession.insert("insertAccident",vo);
		
		
		
		return flag;
	}

	@Override
	public int update(AccidentVO vo) {
		System.out.println("AccidentDAOimpl:update()...");
//		System.out.println(vo.getNum());
		System.out.println(vo.getStatus());
		System.out.println(vo.getSerialnum());
		
		int flag = 0;
		
		flag = sqlSession.update("updateAccident",vo);
		
		return flag;
	}

	@Override
	public int delete(AccidentVO vo) {
		System.out.println(vo.getNum());
		
		int flag = 0;
		
		flag =sqlSession.delete("deleteAccident",vo);
		
		return flag;
	}

	@Override
	public AccidentVO search(AccidentVO vo) {
		logger.info("AccidentDAOimpl:search()..."+vo.getNum());
		AccidentVO avo = sqlSession.selectOne("searchAccident", vo);
		
		return avo;
	}

	@Override
	public List<AccidentVO> select() {
		logger.info("AccidentDAOimpl:select()...");
		List<AccidentVO> list = null;
		list = sqlSession.selectList("selectAccident");
		
		return list;
	}

	@Override
	public List<AccidentVO> search(String searchKey, String searchWord) {
		logger.info("AccidentDAOimpl:search()...searchKey"+searchKey);
		logger.info("AccidentDAOimpl:search()...searchWord"+searchWord);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("searchKey", searchKey);
		map.put("searchWord", "%" + searchWord +"%");
		
		List<AccidentVO> list = sqlSession.selectList("searchAccidentList",map);
		
		return list;
	}

}
