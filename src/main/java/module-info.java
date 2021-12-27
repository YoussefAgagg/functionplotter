module com.github.youssefagagg.functionplotter {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;
	requires java.base;
	requires  matheclipse.core;

	requires hipparchus.core;
	requires jas;


    opens com.github.youssefagagg.functionplotter to javafx.fxml;
    exports com.github.youssefagagg.functionplotter;
}
