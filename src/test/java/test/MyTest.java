package test;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.haozileung.test.domain.system.User;
import com.haozileung.test.infra.DataSourceProvider;
import com.haozileung.test.infra.PropertiesProvider;
import com.haozileung.test.infra.QueryHelper;
import com.haozileung.test.infra.cache.CacheHelper;
import com.haozileung.test.infra.cache.ICacheInvoker;

public class MyTest {

	@Before
	public void before() {
		PropertiesProvider.init();
		DataSourceProvider.init();
		CacheHelper.init();
	}

	@Test
	public void test() {
		for (int i = 0; i < 10; i++) {
			List<User> users = getUser();
			for (User u : users) {
				System.out.println(u.getUsername() + "===" + u.getStatus());
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	@SuppressWarnings("unchecked")
	private List<User> getUser() {
		List<User> users = (List<User>) CacheHelper.get("User", "alluser",
				new ICacheInvoker() {
					@Override
					public Object callback(Object... args) {
						return QueryHelper.query(User.class,
								"select * from t_user where status = ?", args);
					}
				}, 0);
		return users;
	}

	@After
	public void after() {
		DataSourceProvider.destroy();
		PropertiesProvider.destroy();
		CacheHelper.destroy();
	}

}
