package test;

import org.junit.After;
import org.junit.Test;

import com.haozileung.infra.cache.MemcacheManager;

public class MyTest {

	@Test
	public void test() {
		String test = "test";
		MemcacheManager.set("test", "1", test);
		String v = (String) MemcacheManager.get("test", "1");
		System.out.println("+++++" + v);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String v2 = (String) MemcacheManager.get("test", "1");
		System.out.println("+++++" + v2);
	}

	@After
	public void after() {
		MemcacheManager.destroy();
	}

}
