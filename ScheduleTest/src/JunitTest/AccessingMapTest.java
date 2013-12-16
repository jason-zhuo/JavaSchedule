package JunitTest;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Before;
import org.junit.Test;

import com.AccessingMap;

public class AccessingMapTest {
    private  AccessingMap test2  = new AccessingMap();
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testUseMap() {
		//final Map<String, Integer> map =  new HashMap<String, Integer>();
		final Map<String, Integer> map =  new ConcurrentHashMap<String, Integer>();
		test2.useMap(map);
	}

}
