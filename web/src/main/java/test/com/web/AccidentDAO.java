package test.com.web;

import java.util.List;

public interface AccidentDAO {
	
	public int insert(AccidentVO vo);
	public int update(AccidentVO vo);
	public int delete(AccidentVO vo);
	
	public AccidentVO search(AccidentVO vo);
	public List<AccidentVO> select();
	public List<AccidentVO> search(String searchKey, String searchWord);
}
