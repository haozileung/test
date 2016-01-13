package com.haozileung.infra.dao;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.haozileung.infra.dal.JdbcDao;
import com.haozileung.infra.dal.build.Criteria;
import com.haozileung.web.domain.User;

public class JdbcDaoTest {

	@Inject
	private JdbcDao dao;

	@Test
	public void testGuiceIOC() {
		System.out.println(
				dao.update(Criteria.update(User.class).set("{name}", "'haozi4'").where("id", new Object[] { 5 })));
		System.out.println(dao.queryList(Criteria.select(User.class).where("id", new Object[] { 5 })));
	}

	@Before
	public void setUp() {
		Injector injector = Guice.createInjector(new DaoModule());
		injector.injectMembers(this);
	}
}
