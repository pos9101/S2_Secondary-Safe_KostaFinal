package test.com.web.login;

import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

@Service("userDaoService")
public class UserDaoServiceImpl extends SqlSessionDaoSupport implements UserDaoService {

	@Override
	public Map<String, Object> selectUser(String username) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("test.com.web.selectUser",username);
	}

}
