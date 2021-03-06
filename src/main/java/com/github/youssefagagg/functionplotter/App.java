package com.github.youssefagagg.functionplotter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

 

    @SuppressWarnings("exports")
	@Override
    public void start(Stage stage) throws IOException {
        var fxmlLoader=loadFXML("functionPloterFXML2");
        Scene scene = new Scene(fxmlLoader.load());
        FunctionPloterController controller= fxmlLoader.getController();
        stage.setOnCloseRequest(e->{
        	controller.close();
        });
        stage.setTitle("Function Plotter");
        stage.setScene(scene);
        stage.show();
    }

    private static FXMLLoader loadFXML(String fxml) {
        return new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    }


    public static void main(String[] args) {
        launch();
    }

}