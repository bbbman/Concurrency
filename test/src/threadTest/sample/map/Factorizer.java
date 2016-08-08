package threadTest.sample.map;

import java.io.IOException;
import java.math.BigInteger;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class Factorizer implements Servlet{	
	private final Computable<BigInteger, BigInteger[]> c = new Computable<BigInteger, BigInteger[]>(){
		@Override
		public BigInteger[] compute(BigInteger arg) throws InterruptedException {
			// TODO Auto-generated method stub
			return null;
		}		
	};
	private final Computable<BigInteger, BigInteger[]> cache = new Memoizer<BigInteger, BigInteger[]>(c);
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		try{
//			BigInteger i = 
//		} catch () {
//			
//		}
	}
	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
}
