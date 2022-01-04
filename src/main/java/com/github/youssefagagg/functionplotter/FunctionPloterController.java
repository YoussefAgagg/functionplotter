package com.github.youssefagagg.functionplotter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.github.youssefagagg.functionplotter.graph.Plot;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/*
 * A controller class whose object is created by FXMLLoader
 *  and used to initialize the UI elements.
 */
public class FunctionPloterController {

	@FXML
	private HBox graphContainerHBox;//the graph of the function will show here

	@FXML
	private TextField functionTextField;//the f(x) input text 

	@FXML
	private TextField minXTextField;

	@FXML
	private TextField maxXTextField;


	@FXML
	private Button plotButton;


	private Validator validateInputs;
	
	
	private ExecutorService executorService=Executors.newSingleThreadExecutor();
	
	@FXML // This method is called by the FXMLLoader when initialization is complete
	public void initialize() {
		validateInputs=Validator.getInstance();
		plotFunction();
		
	}
	@FXML
	void plotFunctionButtonClick(ActionEvent event) {
		
		graphContainerHBox.getChildren().clear();
		if(validateInputs()) {
			plotButton.setDisable(true);
	
			plotFunction();



		}else {
			showErrorDialog(null, "Please enter valid inputs.\n"
					+ "Example of f(x): 2*x+3*(5.5*x^2)-100\n"
					+ "And the minX should less than maxX");
		}

	}
	private void plotFunction() {
		long t1=System.nanoTime();
		double minX=Double.parseDouble(minXTextField.getText());
		double maxX=Double.parseDouble(maxXTextField.getText());

		String function=functionTextField.getText();
		Task<Plot>task=new PlotTask(minX,maxX,function);
		executorService.execute(task);

		task.setOnSucceeded(e->{

			graphContainerHBox.getChildren().add(task.getValue());
			plotButton.setDisable(false);
			System.out.println("the total time: "+(System.nanoTime()-t1));

		});
		task.setOnFailed(e->{
			plotButton.setDisable(false);
			showErrorDialog(task.getException(), task.getException().getMessage());
		});
	}

	private boolean validateInputs() {
		
		return validateInputs.validMinMaxX(minXTextField.getText(),maxXTextField.getText())
				&& validateInputs.validFunctionExpression(functionTextField.getText());
	}
	private void  showErrorDialog(Throwable throwable,String message) {



		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Exception Dialog");
		alert.setHeaderText("Look, an Exception Happend");
		alert.setContentText(message);

		// Create expandable Exception.
		if(throwable!=null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			throwable.printStackTrace(pw);
			String exceptionText = sw.toString();

			Label label = new Label("The exception stacktrace was:");

			TextArea textArea = new TextArea(exceptionText);
			textArea.setEditable(false);
			textArea.setWrapText(true);

			textArea.setMaxWidth(Double.MAX_VALUE);
			textArea.setMaxHeight(Double.MAX_VALUE);
			GridPane.setVgrow(textArea, Priority.ALWAYS);
			GridPane.setHgrow(textArea, Priority.ALWAYS);

			GridPane expContent = new GridPane();
			expContent.setMaxWidth(Double.MAX_VALUE);
			expContent.add(label, 0, 0);
			expContent.add(textArea, 0, 1);

			// Set expandable Exception into the dialog pane.
			alert.getDialogPane().setExpandableContent(expContent);
		}
		alert.showAndWait();

	}
	
	public void close() {
		executorService.shutdownNow();
	}



}
