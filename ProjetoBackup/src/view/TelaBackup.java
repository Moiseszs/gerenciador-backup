package view;

import java.sql.SQLException;

import controller.BackupControl;
import controller.ComputadorControl;
import entity.Backup;
import entity.Computador;
import entity.Plano;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.LongStringConverter;

public class TelaBackup implements Tela{

	private BorderPane bPane = new BorderPane();
	private TextField txtID = new TextField();
	private TextField txtDescricao = new TextField();
	private DatePicker dataInicio = new DatePicker();
	private DatePicker dataFim = new DatePicker();
	private ComboBox<Computador> comboComputador = new ComboBox<>();
	
	private Label lblID = new Label("ID");
	private Label lblDescricao = new Label("Descricao");
	private Label lblDataInicio = new Label("Data de Inicio");
	private Label lblDataFim = new Label("Data de Finalização");
	private Label lblComputador = new Label("Computador");
	
	private Button btnSalvar = new Button("Salvar");
	private Button btnPesquisar = new Button("Pesquisar");
	
	
	private BackupControl backupControl;
	private ComputadorControl computadorControl;
	
	private TableView<Backup> tabela = new TableView<>();
	
	private Executor executor;
	
	public TelaBackup(Executor executor) {
		this.executor = executor;
	}
	
	@Override
	public Pane render() {
		return bPane;
	}
	
	private void adicionar() {
		try {
			backupControl.salvar();
		} catch (SQLException e) {
			
			Alert alert = new Alert(AlertType.ERROR, 
					"Erro no backend");
			alert.showAndWait();
			e.printStackTrace();
		}		
	}
	
	public void setBind() {
		Bindings.bindBidirectional(txtID.textProperty(), backupControl.getId(), 
				(StringConverter) new LongStringConverter());
		Bindings.bindBidirectional(txtDescricao.textProperty(), backupControl.getDescricao());
		Bindings.bindBidirectional(dataInicio.valueProperty(), backupControl.getDataInicio());
		Bindings.bindBidirectional(dataFim.valueProperty(), backupControl.getDataFim());
		Bindings.bindBidirectional(comboComputador.valueProperty(), backupControl.getComputadorProp());
	}
	
	public void setComboBox() {
		comboComputador.setItems(computadorControl.getComputadores());
		comboComputador.setConverter(new javafx.util.StringConverter<Computador>() {
			
			@Override
			public String toString(Computador object) {
				if(object == null) {
					return "";
				}
				return object.getDescricao();
			}
			
			@Override
			public Computador fromString(String string) {
				return null;
			}

		});
		
	}
	
	
	public void setTabela() {
		TableColumn<Backup, Long> colID = new TableColumn<Backup, Long>();
		TableColumn<Backup, String> colDesc = new TableColumn<Backup, String>();
	}
	
	@Override
	public void start() {
		try {
			backupControl = new BackupControl();
			computadorControl = new ComputadorControl();
			computadorControl.pesquisarTodos();
		} 
		catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, 
					"Erro no backend");
			alert.showAndWait();
			System.exit(1);
		}
		
		Scene scene = new Scene(bPane);
		bPane.setPadding(new Insets(15));
		GridPane gridPane = new GridPane();
		bPane.setTop(gridPane);
		bPane.setBottom(tabela);
		tabela.setPrefSize(100,300);
		gridPane.setHgap(10);
		gridPane.setVgap(5);
		gridPane.add(lblID, 0, 0);
		gridPane.add(txtID, 0, 1);
		gridPane.add(lblDescricao, 0, 3);
		gridPane.add(txtDescricao, 0, 4);
		gridPane.add(lblComputador, 0, 6);
		gridPane.add(comboComputador, 0, 7);
		gridPane.add(lblDataInicio, 10, 0);
		gridPane.add(dataInicio, 10, 1);
		gridPane.add(lblDataFim, 10, 3);
		gridPane.add(dataFim, 10, 4);
		
		btnSalvar.setOnAction((e) ->{
			adicionar();
		});
		
		FlowPane flowPane = new FlowPane();
		flowPane.setHgap(20);
		gridPane.add(flowPane, 0, 10);
		
		flowPane.getChildren().addAll(btnSalvar, btnPesquisar);
		setBind();
		setComboBox();
	}

	

}
