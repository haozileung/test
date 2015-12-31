package com.haozileung.infra.dao;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.haozileung.infra.dal.JdbcDao;
import com.haozileung.infra.dal.build.Criteria;
import com.haozileung.web.domain.User;

public class JdbcDaoTest {

	private static JdbcDao dao;

	@BeforeClass
	public static void setUp() {
		dao = new JdbcDaoDbUtilsImpl();
	}

	@Test
	public void testInsertObject() {
		User u = new User();
		u.setUserName("test");
		u.setEmail("yamchaleung@gmail.com");
		u.setPassword("test");
		u.setStatus(1);
		Long id = dao.insert(u);
		Assert.assertTrue(id != null && id != 0);
	}

	@Test
	@Ignore
	public void testInsertCriteria() {
		Criteria c = Criteria.insert(User.class).into("name", "test2").into("email", "t").into("password", "t")
				.into("status", 1);
		Long id = dao.insert(c);
		Assert.assertTrue(id != null && id != 0);
	}

	@Test
	@Ignore
	public void testSaveObject() {
		User u = new User();
		u.setUserName("test");
		u.setEmail("yamchaleung@gmail.com");
		u.setPassword("test");
		u.setStatus(1);
		dao.save(u);
	}

	@Test
	public void testSaveCriteria() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateCriteria() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateObjectBoolean() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteCriteria() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteClassOfQLong() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteAll() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testQueryListCriteria() {
		List<User> users = dao.queryList(Criteria.select(User.class).tableAlias("t").addSelectFunc("id+1 id"));
		users.forEach(u -> {
			System.out.println(u);
		});
	}

	@Test
	public void testQueryListClassOfQ() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryListT() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryListTCriteria() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryCountObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryCountCriteria() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryCountObjectCriteria() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetClassOfTLong() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCriteriaLong() {
		fail("Not yet implemented");
	}

	@Test
	public void testQuerySingleResultT() {
		fail("Not yet implemented");
	}

	@Test
	public void testQuerySingleResultCriteria() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryForObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryForList() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryForSqlString() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryForSqlStringObjectArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryForSqlStringStringObjectArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateForSqlString() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateForSqlStringObjectArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateForSqlStringStringObjectArray() {
		fail("Not yet implemented");
	}

}
