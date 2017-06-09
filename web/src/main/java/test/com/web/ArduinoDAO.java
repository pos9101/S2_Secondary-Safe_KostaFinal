package test.com.web;

import java.util.List;

public interface ArduinoDAO {
	
	public int insert(ArduinoVO vo);
	public int update(ArduinoVO vo);
	public int delete(ArduinoVO vo);
	
	public ArduinoVO search(ArduinoVO vo);
	
	public List<ArduinoVO> select();
	public List<ArduinoVO> search(String searchKey, String searchWord);
	
}
