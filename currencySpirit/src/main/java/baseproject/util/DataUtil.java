package baseproject.util;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * createBy Eshel
 * createTime: 2017/10/15 10:32
 * desc: TODO
 */

public class DataUtil {
	public static  <K, V> Map.Entry<K, V> getTailByReflection(LinkedHashMap<K, V> map)
			throws NoSuchFieldException, IllegalAccessException {
		Field tail = map.getClass().getDeclaredField("tail");
		tail.setAccessible(true);
		return (Map.Entry<K, V>) tail.get(map);
	}
}
