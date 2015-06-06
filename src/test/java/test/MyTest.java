package test;

import com.haozileung.infra.cache.CacheHelper;
import com.haozileung.infra.utils.DataSourceUtils;
import com.haozileung.infra.utils.PropertiesUtils;
import com.haozileung.test.domain.system.User;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MyTest {

	@Before
	public void before() {

	}

	@Test
	public void test() {
		PropertiesUtils.init();
		DataSourceUtils.init();
		CacheHelper.init();
		QueryRunner run = new QueryRunner();
		try {
			run.insert(DataSourceUtils.getConnection(),
					"insert into user", new ResultSetHandler<User>() {
						@Override
						public User handle(ResultSet rs) throws SQLException {
							return null;
						}
					});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DataSourceUtils.destroy();
		PropertiesUtils.destroy();
		CacheHelper.destroy();

	}

}
