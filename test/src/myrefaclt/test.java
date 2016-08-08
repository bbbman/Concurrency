package myrefaclt;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Persion p = new Persion();
		p.setId(1);
		p.setName("ake");
		p.setPassword("123");
		Field[] fields = p.getClass().getDeclaredFields();		
		 try {
			Method m;
			try {
				m = p.getClass().getMethod("get" + "Name");
				String value = (String) m.invoke(p);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
