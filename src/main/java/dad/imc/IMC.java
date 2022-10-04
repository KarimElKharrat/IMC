package dad.imc;

import javafx.application.Application;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class IMC extends Application {

	private Label imcResultado;
	private Label imcLabel;
	private TextField pesoTextField;
	private TextField alturaTextField;
	private HBox pesoHBox;
	private HBox alturaHBox;
	private HBox imcHBox;
	private VBox root;
	private DoubleProperty peso = new SimpleDoubleProperty();
	private DoubleProperty altura = new SimpleDoubleProperty();
	private DoubleExpression imcDoubleExpression;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		imcLabel = new Label();
		imcResultado = new Label();
		
		pesoTextField = new TextField();
		alturaTextField = new TextField();
		
		pesoHBox = new HBox();
		pesoHBox.setAlignment(Pos.CENTER);
		pesoHBox.getChildren().addAll(new Label("Peso: "), pesoTextField, new Label("kg"));
		pesoHBox.setSpacing(5);
		
		alturaHBox = new HBox();
		alturaHBox.setAlignment(Pos.CENTER);
		alturaHBox.getChildren().addAll(new Label("Altura: "), alturaTextField, new Label("cm"));
		alturaHBox.setSpacing(5);
		
		imcHBox = new HBox();
		imcHBox.setAlignment(Pos.CENTER);
		imcHBox.getChildren().addAll(new Label("IMC: "), imcLabel);
		imcHBox.setSpacing(5);
		
		root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(pesoHBox, alturaHBox, imcHBox, imcResultado);
		root.setFillWidth(false);
		root.setSpacing(5);
		
		primaryStage.setTitle("IMC");
		primaryStage.setScene(new Scene(root, 300, 250));
		primaryStage.show();
		
		//bindear string a doubleProperty
		pesoTextField.textProperty().bindBidirectional(peso, new NumberStringConverter());
		alturaTextField.textProperty().bindBidirectional(altura, new NumberStringConverter());
		
		DoubleExpression cmtom = altura.divide(100);
		imcDoubleExpression = peso.divide(cmtom.multiply(cmtom));
		imcDoubleExpression.addListener((o, ov, nv) -> onCambioExpression(nv));
		
		imcLabel.textProperty().bind(imcDoubleExpression.asString("%.2f"));

	}

	private void onCambioExpression(Number nv) {
		if(nv.doubleValue() < 18.5)
			imcResultado.setText("Bajo peso.");
		else if(nv.doubleValue() < 25)
			imcResultado.setText("Normal.");
		else if(nv.doubleValue() < 30)
			imcResultado.setText("Sobrepeso.");
		else
			imcResultado.setText("Obeso.");
	}

	public static void main(String[] args) {
		launch(args);
	}

}
