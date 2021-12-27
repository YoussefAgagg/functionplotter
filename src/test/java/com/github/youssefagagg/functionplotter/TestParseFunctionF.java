package com.github.youssefagagg.functionplotter;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class TestParseFunctionF {
	double delta=Double.MAX_VALUE;
	@Test
	public void testExpressionEval() {

		ParseFunction f=new ParseFunction();
		f.parseExpression("x^2");
		Assert.assertEquals(4, f.eval( 2), delta);
		f.parseExpression("x^2+x+1");
		Assert.assertEquals(7, f.eval( 2), delta);
		f.parseExpression("1/x^2");
		Assert.assertEquals(1.0/4, f.eval( 2), delta);
		f.parseExpression("1/(x^2+4)");
		Assert.assertEquals(1.0/8, f.eval( 2), delta);
		f.parseExpression("(((1))/(((x^2))+4))");
		Assert.assertEquals(1.0/8, f.eval( 2), delta);
		
		f.parseExpression("1/(x^2)");
		Assert.assertEquals(Double.POSITIVE_INFINITY, f.eval( 0), delta);
		f.parseExpression("-1/(x^2)");
		Assert.assertEquals(Double.NEGATIVE_INFINITY, f.eval( 0), delta);
		f.parseExpression("sqrt(x)");
		Assert.assertEquals(Double.NaN, f.eval(-1), delta);
		f.parseExpression("sqrt(1/2*x)");
		Assert.assertEquals(1, f.eval( 2), delta);
		f.parseExpression("exp(x+5)");
		Assert.assertEquals(2249.09193313, f.eval(Math.E), delta);


	}
	@Test
	public void testPartialFun() {
		long t1=System.nanoTime();
		ParseFunction f=new ParseFunction("x^3+sin(x)/(x^2-6)*exp(x+1)");

		Assert.assertEquals(-1.13186352033, f.eval(2),delta);
		System.out.println("time consumed in mile second: "+(System.nanoTime()-t1)/1000.0/1000);
		f.parseExpression("1/2^x");
		Assert.assertEquals(0.0, f.eval(Double.POSITIVE_INFINITY),delta);
		f.parseExpression("x+1/2^x");
		Assert.assertEquals(Double.POSITIVE_INFINITY, f.eval(Double.POSITIVE_INFINITY),delta);
		
	}
	

}
