package test;

import org.junit.Test;

import com.haozileung.infra.cache.CacheHelper;
import com.haozileung.infra.cache.ICacheInvoker;

public class MyTest {

	@Test
	public void test() {
		ICacheInvoker<String> invoker = new ICacheInvoker<String>() {
			@Override
			public String callback() {
				return "CacheHelper";
			}
		};
		for (int i = 0; i < 20; i++) {
			System.out.println(CacheHelper.get("test", "test", invoker));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
