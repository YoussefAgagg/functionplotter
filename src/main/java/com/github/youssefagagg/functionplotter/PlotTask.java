package com.github.youssefagagg.functionplotter;

import java.util.ArrayList;
import java.util.List;

import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;

import com.github.youssefagagg.functionplotter.graph.Axes;
import com.github.youssefagagg.functionplotter.graph.Plot;
import javafx.concurrent.Task;

public class PlotTask extends Task<Plot>{
	private  Validator validator;
	private double minX;
	private double maxX;
	private String function;//function of x
	private boolean partialFunction;
	//to parse and solve equation
	private final static  ExprEvaluator exprEvaluator = new ExprEvaluator();
	private int loop=10000;//the number of points to to draw in the graph

	public PlotTask(double minx, double maxX, String function) {
		this.minX = minx;
		this.maxX = maxX;
		this.function = function.toLowerCase();
		validator=Validator.getInstance();
	}


	@Override
	protected Plot call() throws Exception {

		return getPlot();
	}

	private Plot getPlot() throws Exception {

	
		ParseFunction expression=new ParseFunction(function);
		

		IExpr result =exprEvaluator.eval(function);
		String exp=result.toString().toLowerCase();
		exp=convertFromReciprocalOfHyperbolic(exp);
		exp=convertFromReciprocalOfTrigonometry(exp);
		
		//list of x values that make The denominator zero of a partial Function
		List<Double>xValuesNotInDomain=notInDomainX(exp);
		xValuesNotInDomain.addAll(notInDomainXLog(exp));

		//calculate the min and max y value that will be used to create the y axis 
		double[]minMaxY=getMinMaxY(xValuesNotInDomain,expression);
		//the maxY value of the Absolute values of minY and maxY
		double maxYAbs=maxAbsoluteValue(minMaxY[0], minMaxY[1]);

		// to make the coordinate looks better
		if(isTrigonometryFunction(exp)) {
			if(partialFunction||exp.contains("tan"))maxYAbs=10;
			else maxYAbs+=2;
		}
		
		
		if(maxYAbs==0.0) {
			maxYAbs+=2;
		}
		
		//the y axis will divided into 20 equals parts 
		double stepY=(maxYAbs*2)/20;
		
		//the height of the Pane that will hold the axis
		int height=600;
		
		
		if(partialFunction&&!xValuesNotInDomain.isEmpty()) {
			height=1000;
			maxYAbs=10;
			stepY=.5;
		}
		
		
		//the value that the minX value will be increment by
		double incX=(maxX-minX)/loop;
		//the maxX value of the Absolute values of minX and maxY
		double maxXAbs=maxAbsoluteValue(minX, maxX);
		double stepX=maxXAbs/20;
		
		System.out.println("maxy " +maxYAbs);
		Axes axes = new Axes(800, height,
							-maxXAbs, maxXAbs, stepX,
							-maxYAbs, maxYAbs, stepY);

		Plot plot = new Plot(expression,
							 xValuesNotInDomain,
							 minX,maxX,incX,
							 axes);
		return plot;

	}




	// for partial fractions return the x values that make The denominator zero as a list
	private List<Double> notInDomainX(String exp) {
		IExpr result;
		List<Double>notInDomainX=new ArrayList<Double>();

		int from=0;
		int index;
		while((index=exp.indexOf("/", from))!=-1) {
			from=index+1;
			String e=parseEqToSolve(exp.substring(from));
			if(!validator.validNumber(e)) {
				partialFunction=true;
			}
			result = exprEvaluator.eval("NSolve({"+e+"==0},{x})");
			validator.matchAndAddRoots(result.toString(),notInDomainX);
		}
		return notInDomainX;
	}
	// for partial fractions return the x values that make The denominator zero as a list
	private List<Double> notInDomainXLog(String exp) {
		IExpr result;
		List<Double>notInDomainX=new ArrayList<Double>();

		int from=0;
		int index;
		while((index=exp.indexOf("log", from))!=-1) {
			from=index+3;
			String e=parseEqToSolve(exp.substring(from));
			// The denominator is not a number so it is a partial function
			if(!validator.validNumber(e)) {
				partialFunction=true;
			}
			//solve the equation
			result = exprEvaluator.eval("NSolve({"+e+"==0},{x})");
			validator.matchAndAddRoots(result.toString(),notInDomainX);
		}
		return notInDomainX;
	}
	//return the equation to solve it
	private  String parseEqToSolve(String eq) {
		String e=""+eq.charAt(0);
		int i=1;
		while(i<eq.length()&&(!validator.isBracketsBlanced(e)||
							   Character.isAlphabetic(e.charAt(i-1))||
							   e.charAt(i-1)=='^'))
		{
			e+=eq.charAt(i);
			i++;
		}
		return e;
	}

	private double[] getMinMaxY(List<Double> notInDomainX,ParseFunction expression) {
		double[]minMax=new double[] {Double.MAX_VALUE,-Double.MAX_VALUE};
		double step=(maxX-minX)/100;
		for(double min=minX;min<maxX;min+=step) {

			if(!checkItInDominList(min, notInDomainX)) {
				setMinMax(minMax, min, expression);
			}
		}
		for(double x:notInDomainX) {
			setMinMax(minMax,x-.001,expression);
			setMinMax(minMax,x+.001,expression);

		}

		if(!checkItInDominList(maxX, notInDomainX))
			setMinMax(minMax, maxX,expression);

		return minMax;

	}
	
	private boolean checkItInDominList(double x, List<Double> notInDomainList) {
		for(int i=0;i<notInDomainList.size();i++) {
			if(x>=notInDomainList.get(i)) {
				return true;
			}
		}
		return false;
	}


	private void setMinMax(double[] minMax, double min,ParseFunction expression)  {
		double r=expression.eval(min);
		if(minMax[0]>r)minMax[0]=r;
		if(minMax[1]<r)minMax[1]=r;
	}

	private double maxAbsoluteValue(double min, double max) {
		// TODO Auto-generated method stub
		return Math.max(Math.abs(min), Math.abs(max));
	}

	private boolean isTrigonometryFunction(String exp) {
		return 	  exp.contains("sin")
				||exp.contains("cos")
				||exp.contains("tan");
	}
	
	
	private String convertFromReciprocalOfTrigonometry(String exp) {
		
		return exp.replace("csc", "1/sin").replace("sec", "1/cos").replace("cot", "1/tan");
	}
	private String convertFromReciprocalOfHyperbolic(String exp) {
		
		return exp.replace("csch", "1/sinh").replace("sech", "1/cosh").replace("coth", "1/tanh");
	}

}
