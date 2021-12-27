package com.github.youssefagagg.functionplotter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import org.junit.Before;
import org.junit.Test;

public class TestValidator {
	Validator validator;
@Before
public void initValidator() {
	validator=Validator.getInstance();
}
@Test
public void validNumberTest() {
	assertTrue(validator.validNumber("111"));
	assertTrue(validator.validNumber(".1"));
	assertTrue(validator.validNumber("1.0"));
	assertTrue(validator.validNumber("0.1"));
	assertTrue(validator.validNumber("-10.1"));
	assertTrue(validator.validNumber("-10"));
	assertTrue(validator.validNumber("-.025"));
	assertTrue(validator.validNumber("983.1234"));
	
	
	assertFalse(validator.validNumber("156."));
	assertFalse(validator.validNumber(""));
	assertFalse(validator.validNumber("2si45"));
	assertFalse(validator.validNumber("1+"));
	assertFalse(validator.validNumber("+221"));
}
@Test
public void validMinMaxTest() {
	assertTrue(validator.validMinMaxX("-123", "100"));
	assertTrue(validator.validMinMaxX("10", "100"));
	assertTrue(validator.validMinMaxX("0", "5"));

	
	
	assertFalse(validator.validMinMaxX("123", "100"));
	assertFalse(validator.validMinMaxX("-", "100"));
	assertFalse(validator.validMinMaxX("", ""));
	assertFalse(validator.validMinMaxX("10", "10"));
	assertFalse(validator.validMinMaxX(null, null));
	
}
@Test
public void isBracketsBlancedTest() {
	assertTrue(validator.isBracketsBlanced("((()))"));
	assertTrue(validator.isBracketsBlanced("()()(())"));
	assertTrue(validator.isBracketsBlanced("(56+(x*(5)))34"));
	assertTrue(validator.isBracketsBlanced(""));
	assertTrue(validator.isBracketsBlanced("123"));
	
	assertFalse(validator.isBracketsBlanced("(()))"));
	assertFalse(validator.isBracketsBlanced("((())"));
	assertFalse(validator.isBracketsBlanced("(("));
	assertFalse(validator.isBracketsBlanced("))"));
	
	
	
}
//@Test
//public void validFunctionExpressionTest() {
//	assertTrue(validator.validFunctionExpression("x+5/100"));
//	assertTrue(validator.validFunctionExpression("(x+5)/100"));
//	assertTrue(validator.validFunctionExpression("-10*x+5/100"));
//	assertTrue(validator.validFunctionExpression("-x+10"));
//	assertTrue(validator.validFunctionExpression("1/(x-10)"));
//	assertTrue(validator.validFunctionExpression("(x+5)/(100+10*x-x^2)"));
//	assertTrue(validator.validFunctionExpression("x^2-2*X-1"));
//	assertTrue(validator.validFunctionExpression("((x))*2+(1+2)"));
//	
//	assertFalse(validator.validFunctionExpression(null));
//	assertFalse(validator.validFunctionExpression(""));
//	assertFalse(validator.validFunctionExpression("2+3x"));
//	assertFalse(validator.validFunctionExpression("x^"));
//	assertFalse(validator.validFunctionExpression("x-"));
//	assertFalse(validator.validFunctionExpression("123*y"));
//	assertFalse(validator.validFunctionExpression("x+(1-2"));
//	
//	
//	
//	
//}
}


