package com.github.youssefagagg.functionplotter;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validator {
	 // private static final Pattern equationPattern=Pattern.compile("^(\\(*-?(((\\d+(\\.\\d+)?)|(\\.\\d+))|x)\\)*[-+*\\/^)])*(\\(*-?(((\\d+(\\.\\d+)?)|(\\.\\d+))|x)\\)*)$");
	  //regex to validate numbers ex: 1.3, 123, 1.14, .12
	  private static final Pattern numberPattern=Pattern.compile("-?((\\d+(\\.\\d+)?)|(\\.\\d+))");
	  
	  //regex to validate the roots values of solving equation that using NSolve method of symja libarary
	  private static final Pattern rootPattern=Pattern.compile("x->(-?\\d+(\\.\\d+)?|\\.\\d+)");
	  private static  Validator validator;
	  private Validator() {
		// TODO Auto-generated constructor stub
	}
		  public static Validator getInstance() {
			  if(validator==null)
				  validator=new Validator();
			  return validator;
		  } 

	public boolean validMinMaxX(String minX,String maxX) {
		return  validNumber(minX)
				&&validNumber(maxX)
				&&Double.parseDouble(minX)<Double.parseDouble(maxX);
	}
	public boolean validNumber(String num) {
		if(isNull(num)||num.isBlank())return false;
		Matcher m=numberPattern.matcher(num);
		return m.matches();
	}

	public boolean validFunctionExpression(String f) {
		if(isNull(f)||f.isBlank())return false;
//		f=f.toLowerCase();
//		Matcher m=equationPattern.matcher(f);
//		return m.matches()&&isBracketsBlanced(f);
	return true;
	}
	
	public boolean isBracketsBlanced  (String s) {
		Deque<Character> stack = new ArrayDeque<Character>();
	    for (char c : s.toCharArray()) {
	        if (c == '(')
	            stack.push('(');
	        else if (c==')'&&stack.poll()==null)
	        	return false;
	            
	        
	    }
	    return stack.isEmpty();
	}
	private boolean isNull(Object o) {
		return o==null;
	}
	
	public void matchAndAddRoots(String root, List<Double> notInDomainX) {
		
		Matcher m=rootPattern.matcher(root);
		while(m.find()) {
			System.out.println("find "+m.group(1));
			notInDomainX.add(Double.parseDouble(m.group(1)));
		}
		
	}
}
