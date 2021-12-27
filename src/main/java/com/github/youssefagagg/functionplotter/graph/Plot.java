package com.github.youssefagagg.functionplotter.graph;

import java.util.List;

import com.github.youssefagagg.functionplotter.ParseFunction;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
/*
 * plot the function graph
 */

public class Plot extends Pane {
	/*
	 * constructor
	 * @param expression to evaluate the x value and get the y value
	 * @param notInDomainX a list contains x values that not in domain x 
	 * @param minX min x value
	 * @param maxX max x value
	 * @param incX the value that the minX will increment by
	 * @axes    
	 *  
	 */
	public Plot(ParseFunction expression, List<Double> notInDomainX, double minX, double maxX, double incX, Axes axes) {

		super.getChildren().add(axes);

		drawGraph(expression, notInDomainX, minX, maxX, incX, axes);

		super.setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
		super.setPrefSize(axes.getPrefWidth(), axes.getPrefHeight());
		super.setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);


	}



	private void drawGraph(ParseFunction expression, List<Double> notInDomainX, double minX, double maxX, double incX,
			Axes axes) {
		//the graph shape 
		Path path = newPath(axes);
		//to indicate this is the start point of the graph to draw
		boolean firstDotPlotted = false;
		// used this value with partial fraction to indicate the accurate of the graph
		double acccurate=Math.pow(10, -7);
		// the value of x,y that represent a point in the screen 
		double mx,my;
		//the evaluate of x 
		double y;
		
		double x=minX;
		while (x < maxX+1) {
			
			//the value  not in domain x
			Double d;
			
			//not continuous graph so start a new path
			if((d=checkToCreateNewPath(x,notInDomainX))!=null&&d!=minX) {
				mx = mapX(d-acccurate, axes);
				y=expression.eval(d-acccurate);
				my = mapY(y, axes);
				//the end point of the  path
				path.getElements().add(new LineTo(mx,my));
				// add the path to  plot
				super.getChildren().add(path);
				//start new path
				path=newPath(axes);
				mx = mapX(d+acccurate, axes);
				y=expression.eval(d+acccurate);
				my = mapY(y, axes);
				//the start point of the path
				path.getElements().add(new MoveTo(mx,my));

			}
			

			mx = mapX(x, axes);
			y=expression.eval(x);
			my = mapY(y, axes);

			
			if (firstDotPlotted) {
				path.getElements().add(new LineTo(mx,my));
			} else {
				path.getElements().add(new MoveTo(mx,my));
				firstDotPlotted=true;
			}
			

			x += incX;

		}
		super.getChildren().add( path);
	}

/*
 * check if  the x value make the denominator of any fraction zero
 * because the increment of x may not be so accurate to the value that make 
 * the  denominator zero,  so check if the x value greater than or 
 * equal to any value in the notInDomainX list, if  found it remove it from the list and return that value 
 */

	private Double checkToCreateNewPath(double x, List<Double> notInDomainX) {
		for(int i=0;i<notInDomainX.size();i++) {
			if(x>=notInDomainX.get(i)) {
				return notInDomainX.remove(i);

			}
		}
		return null;
	}
	//create new path
	private Path newPath(Axes axes) {
		Path path = new Path();
		path.setStroke(Color.ORANGE.deriveColor(0, 1, 1, 0.6));
		path.setStrokeWidth(2);

		path.setClip(
				new Rectangle(
						0, 0,
						axes.getPrefWidth(),
						axes.getPrefHeight()
						)
				);
		return path;
	}


	// map the x value to it's equivalent x coordinate in the screen  
	private double mapX(double x, Axes axes) {
		double tx = axes.getPrefWidth() / 2;
		double sx = axes.getPrefWidth()
						/ (axes.getXAxis().getUpperBound()
							- axes.getXAxis().getLowerBound());

		return x * sx + tx;
	}
	// map the y value to it's equivalent  y coordinate in the screen 
	private double mapY(double y, Axes axes) {
		double ty = axes.getPrefHeight() / 2;
		double sy = axes.getPrefHeight()
						/ (axes.getYAxis().getUpperBound()
							- axes.getYAxis().getLowerBound());
		return -y * sy + ty;
	}
}