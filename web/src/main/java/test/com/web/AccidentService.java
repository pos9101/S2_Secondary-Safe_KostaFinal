      package test.com.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccidentService {
	
	@Autowired
	private AccidentDAO dao;
	
	public int insert(AccidentVO vo){
		return dao.insert(vo);
	}
	
	public int update(AccidentVO vo){
		return dao.update(vo);
	}
	
	public int delete(AccidentVO vo){
		return dao.delete(vo);
	}
	
	public AccidentVO search(AccidentVO vo){
		return dao.search(vo);
	}
	
	public List<AccidentVO> select(){
		return dao.select();
	}
	
	public List<AccidentVO> search(String searchKey, String searchWord){
		return dao.search(searchKey, searchWord);
	}
}
