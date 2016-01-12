package com.haozileung.infra.dao;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.haozileung.infra.cache.CacheHelper;
import com.haozileung.infra.cache.CacheModule;
import com.haozileung.infra.dal.JdbcDao;
import com.haozileung.web.domain.User;

public class JdbcDaoTest {

	@Inject
	private CacheHelper cacheHelper;

	@Inject
	private JdbcDao dao;

	@Test
	public void testGuiceIOC() {
		System.out.println(dao.queryList(User.class));
		for (int i = 0; i < 10; i++) {
			String j = cacheHelper.get("test", "test", () -> {
				return "112";
			});
			System.out.println(j);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Before
	public void setUp() {
		Injector injector = Guice.createInjector(new CacheModule(), new DaoModule());
		injector.injectMembers(this);
	}
}
