package test.com.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArduinoService {
	
	@Autowired
	private ArduinoDAO dao;
	
	public int insert(ArduinoVO vo){
		return dao.insert(vo);
	}
	
	public int update(ArduinoVO vo){
		return dao.update(vo);
	}
	
	public int delete(ArduinoVO vo){
		return dao.delete(vo);
	}
	
	public ArduinoVO search(ArduinoVO vo){
		return dao.search(vo);
	}
	
	public List<ArduinoVO> select(){
		return dao.select();
	}
	
	public List<ArduinoVO> search(String searchKey, String searchWord){
		return dao.search(searchKey, searchWord);	
	}
}
