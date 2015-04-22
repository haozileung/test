package test;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.junit.Before;
import org.junit.Test;

import com.haozileung.test.domain.system.User;
import com.haozileung.test.infra.DataSourceProvider;
import com.haozileung.test.infra.PropertiesProvider;
import com.haozileung.test.infra.cache.CacheHelper;

public class MyTest {

	@Before
	public void before() {

	}

	@Test
	public void test() {
		PropertiesProvider.init();
		DataSourceProvider.init();
		CacheHelper.init();
		QueryRunner run = new QueryRunner();
		try {
			run.insert(DataSourceProvider.getConnection(),
					"insert into sys_user", new ResultSetHandler<User>() {
						@Override
						public User handle(ResultSet rs) throws SQLException {
							return null;
						}
					});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DataSourceProvider.destroy();
		PropertiesProvider.destroy();
		CacheHelper.destroy();

	}

}
