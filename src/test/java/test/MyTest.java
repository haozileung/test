package test;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.haozileung.test.domain.security.User;
import com.haozileung.test.infra.DataSourceProvider;
import com.haozileung.test.infra.PropertiesProvider;
import com.haozileung.test.infra.QueryHelper;
import com.haozileung.test.infra.cache.CacheManager;

public class MyTest {

	@Before
	public void before() {
		PropertiesProvider.init();
		DataSourceProvider.init();
		CacheManager.init(null);
	}

	@Test
	public void test() {
		List<User> users = QueryHelper.query(User.class,
				"select * from t_user where username = ?",
				new Object[] { "admin" });
		for (User u : users) {
			System.out.println(u.getUsername() + "===" + u.getStatus());
		}
	}

	@After
	public void after() {
		DataSourceProvider.destroy();
		PropertiesProvider.destroy();
		CacheManager.destroy();
	}

}
