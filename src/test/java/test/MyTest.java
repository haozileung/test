package test;

import com.haozileung.test.domain.system.User;
import com.haozileung.test.infra.DataSourceProvider;
import com.haozileung.test.infra.PropertiesProvider;
import com.haozileung.test.infra.QueryHelper;
import com.haozileung.test.infra.cache.CacheHelper;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class MyTest {

    @Before
    public void before() {

    }

    @Test
    public void test() {
        PropertiesProvider.init();
        DataSourceProvider.init();
        CacheHelper.init();
        for (int i = 0; i < 10; i++) {
            List<User> users = CacheHelper.get(User.class.getName(),
                    "users", () -> QueryHelper.query(User.class,
                            "select * from sys_user where status = ?", 1));
            if (null != users) {
                for (User u : users) {
                    System.out.println(u.getUsername() + "===" + u.getStatus());
                }
            }
        }
        DataSourceProvider.destroy();
        PropertiesProvider.destroy();
        CacheHelper.destroy();

    }

}
