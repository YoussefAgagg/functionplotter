package com.github.youssefagagg.functionplotter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;
/*
 *Refer to the answer  https://stackoverflow.com/a/26227947/11286441 I implement this class and change it to suit my use case 
 *
 *
 */
public class ParseFunction {
	@FunctionalInterface
 interface Expression {
		double eval();
	}

	

	private static final Map<String,DoubleUnaryOperator> functions = new HashMap<>();
	static {
	    functions.put("sqrt", x -> Math.sqrt(x));
	    functions.put("sin", x -> Math.sin(x));
	    functions.put("cos", x -> Math.cos(x));
	    functions.put("tan", x -> Math.tan(x));
	    functions.put("sinh", x -> Math.sinh(x));
	    functions.put("cosh", x -> Math.cosh(x));
	    functions.put("tanh", x -> Math.tanh(x));
	    functions.put("abs", x -> Math.abs(x));
	    functions.put("log", x -> Math.log10(x));
	    functions.put("ln", x -> Math.log(x));
	    functions.put("exp", x -> Math.exp(x));

	}
	// the value of the x variable that will be evaluate to the expression
	private double variableX_Value;
	private String str;//the f(x) function that will be parse
	private int pos = -1;
	private int ch;
	private Expression expression;
	
	public ParseFunction(String str) {
		super();
		this.str = str;
		parse();
	}
	public ParseFunction() {

	}
	public void  parseExpression(String str) {
		if (str==null)  throw new RuntimeException("there is no expression to evalulate ");
		this.str=str;
		pos=-1;
		parse();
	}
	private void parse() {
		nextChar();
		expression = parseExpression();
		if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
		
	}
	public  double  eval(double xValue) {
		if (str==null)  throw new RuntimeException("there is no expression to evaluate ");
		variableX_Value=xValue;
		return expression.eval();
	}


	private void nextChar() {
		ch = (++pos < str.length()) ? str.charAt(pos) : -1;
	}

	private boolean eat(int charToEat) {
		while (ch == ' ') nextChar();
		if (ch == charToEat) {
			nextChar();
			return true;
		}
		return false;
	}


    // Grammar:
    // expression = term | expression `+` term | expression `-` term
    // term = factor | term `*` factor | term `/` factor
    // factor = base | base '^' base
    // base = '-' base | number | x | function factor | '(' expression ')'


	private Expression  parseExpression() {
		Expression  e = parseTerm();
		for (;;) {
			if(eat('+')) {// addition
				Expression a = e, b = parseTerm();
				e = ()->a.eval()+b.eval(); 
			}

			else if (eat('-')) {// subtraction
				Expression a = e, b = parseTerm();
				e = ()->a.eval()-b.eval(); 
			}
			else return e;
		}

	}

	private Expression  parseTerm() {
		Expression  e = parseFactor();
		for (;;) {
			if (eat('*')) {// multiplication
				Expression a = e, b = parseFactor();
				e=()->a.eval()*b.eval();
			} 
			else if (eat('/')){// division
				Expression a = e, b = parseFactor();
				e=()->a.eval()/b.eval();
			}  
			else return e;
		}
	}
   private Expression parseFactor(){
        Expression e = parseBase();
        for(;;){
            if (eat('^')){
                Expression a = e, b = parseBase();
                e = (()->Math.pow(a.eval(),b.eval()));
            }else{
                return e;
            }
        }
    }

	private Expression  parseBase() {


		Expression  e;
		int startPos = this.pos;
		   if (eat('-')){
               Expression b = parseBase();
               e = (()-> (-1)*b.eval());
               return e;
           }
		   
		if (eat('(')) { // parentheses
			e = parseExpression();
			eat(')');
		} else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
			while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
			double d=Double.parseDouble(str.substring(startPos, this.pos));
			e =()-> d;
		} else if (ch >= 'a' && ch <= 'z') { // functions
			while (ch >= 'a' && ch <= 'z') nextChar();
			  String name = str.substring(startPos, this.pos);
              if (functions.containsKey(name)) {
                  DoubleUnaryOperator func = functions.get(name);
                  Expression arg = parseFactor();
                  e = () -> func.applyAsDouble(arg.eval());
              } else if(name.equals("x")) {
                  e = () -> variableX_Value;
              }else {
            	  throw new RuntimeException("Unexpected: " + (char)ch);
              }
		} else {
			throw new RuntimeException("Unexpected: " + (char)ch);
		}
	
		

		return e;
	}
}
