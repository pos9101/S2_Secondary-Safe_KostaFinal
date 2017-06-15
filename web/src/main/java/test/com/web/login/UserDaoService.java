package test.com.web.login;

import java.util.Map;

public interface UserDaoService {
	
	public Map<String, Object> selectUser(String username);
}
