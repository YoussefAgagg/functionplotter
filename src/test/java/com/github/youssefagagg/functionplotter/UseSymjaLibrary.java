package com.github.youssefagagg.functionplotter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.matheclipse.core.eval.EvalUtilities;

import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.INumber;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.parser.client.SyntaxError;
import org.matheclipse.parser.client.math.MathException;

import com.mitchellbosecke.pebble.node.expression.Expression;

import org.matheclipse.core.eval.EvalUtilities;
import org.matheclipse.core.expression.F;
import org.hipparchus.analysis.solvers.LaguerreSolver;
public class UseSymjaLibrary {
	private static  Validator validator=Validator.getInstance();
	public static void main(String[] args) {
		double t1=System.nanoTime();

		try {
			
			
            ExprEvaluator util = new ExprEvaluator();
//           
//            System.out.println(util.eval("1/sin(x)"));
//            System.out.println(util.eval("1/cos(x)"));
//            System.out.println(util.eval("1/tan(x)"));
//            System.out.println(util.eval("1/sinh(x)"));
//            System.out.println(util.eval("1/cosh(x)"));
//            System.out.println(util.eval("1/tanh(x)"));
//            IExpr result = util.eval("e^(1/x)*10/((x-8x^2)+5/(x-1))+4/(x^2-4)");
//           
//            System.out.println(result);
//         //   result = util.eval("Solve({"+result.toString()+"==0},{x})");
//            System.out.println(result);
//            List<Double>notInDomian=new ArrayList<Double>();
//            String sb=result.toString();
//            int from=0;
//            int index;
//            while((index=sb.indexOf("/", from))!=-1) {
//            	from=index+1;
//            	String e=parseEq(sb.substring(from));
//            	System.out.println(e);
//            	
//            	result = util.eval("NRoots("+e+"==0)");
//            	List<Double>l=parseRoots(result.toString());
//            	notInDomian.addAll(l);
//            	System.out.println(result.toString());
//            }
//
//            System.out.println(notInDomian);
           System.out.println("start");
         //   System.out.println(util.eval("NRoots(x^3-6==0)"));
            System.out.println(util.eval("NSolve(log(x)==0, {x})"));
            
            System.out.println(util.eval("2^5+8"));
          
            	IExpr d=util.eval("-Sqrt(10)");
				//System.out.println(d.get);
//            	LaguerreSolver solver = new LaguerreSolver(1.0e-5);
//                double[] coefficients = new double[] {-100.0, 0.0, 0.0, 0.0, 1.0};
//                org.hipparchus.complex.Complex[] roots = solver.solveAllComplex(coefficients, 0);
//                for (int j = 0; j < roots.length; j++) {
//                  System.out.println(roots[j]);
//                }
//   
        
        } catch (Exception e) {
           System.out.println("parse failed");
        }
		System.out.println(System.nanoTime()-t1);
    }

	
}