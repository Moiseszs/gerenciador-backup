package view;

import java.sql.SQLException;
import java.text.Format;

import controller.ClienteControl;
import controller.ComputadorControl;
import entity.Cliente;
import entity.Computador;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.css.converter.StringConverter;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javafx.util.*;
import javafx.stage.Stage;
import javafx.util.converter.LongStringConverter;

public class TelaComputadores extends Application {

	private ComboBox<Cliente> comboClientes = new ComboBox<>();
	private TextField txtDescricacao = new TextField("");
	private TextField txtId = new TextField("");
	private Label lblIdDesc = new Label("ID");
	private Label lblDescricao = new Label("Descrição");
	private Label lblNomeCliente = new Label("Cliente");
	private ComputadorControl control;
	private ClienteControl clienteControl;
	private Button btnSalvar = new Button("Salvar");
	private Button btnAtualizar = new Button("Atualizar");
	private Button btnDeletar = new Button("Deletar");
	private Button btnPesquisar = new Button("Pesquisar");
	private TableView<Computador> tabela = new TableView<Computador>();
	
	
	public void ligaCampos() {
		Bindings.bindBidirectional(txtDescricacao.textProperty(), control.getDescricao());
		Bindings.bindBidirectional(comboClientes.valueProperty(), control.getCliente());
		
		Bindings.bindBidirectional(txtId.textProperty(), control.getId(), new javafx.util.StringConverter<Number>() {
			@Override
			public String toString(Number object) {
				return object.toString();
			}

			@Override
			public Number fromString(String string) {
				return Long.parseLong(string);
			}
		});
		}
	
	public void preparaTabela() {
		TableColumn<Computador, Long> colId = new TableColumn<>("ID");
		colId.setCellValueFactory(new PropertyValueFactory<Computador, Long>("id"));
		TableColumn<Computador, String> colNome = new TableColumn<Computador, String>("Descição");
		colNome.setCellValueFactory(new PropertyValueFactory<Computador, String>("descricao"));
		TableColumn<Computador, String> colCliente = new TableColumn<Computador, String>("cliente");
		colCliente.setCellValueFactory(new PropertyValueFactory<Computador, String>("clienteNome"));
		tabela.getColumns().addAll(colId, colNome, colCliente);
		tabela.setItems(control.getComputadores());
	}
	
	public void salvar() {
		try {
			control.adicionar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void setComboContent() {
		comboClientes.setItems(clienteControl.getClientes());
	}


	private void preparaComboBox() {
		comboClientes.setPromptText("Selecione um cliente");
		comboClientes.setConverter(new javafx.util.StringConverter<Cliente>() {
			
			@Override
			public String toString(Cliente object) {
				if(object == null) {
					return "";
				}
				return object.getNome();
			}
			
			@Override
			public Cliente fromString(String string) {
				return null;
			}
		});
	}
	
	
	@Override
	public void start(Stage stage) throws Exception {
		
		try {
			control = new ComputadorControl();
			clienteControl = new ClienteControl();
			clienteControl.pesquisarTodos();
			control.pesquisarTodos();
		}
		catch (Exception e) {
			System.out.println("erro");
		}
		ligaCampos();
		setComboContent();
		preparaTabela();
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(15));
		Scene scene = new Scene(borderPane);
		GridPane gridPane = new GridPane();
		borderPane.setTop(gridPane);
		borderPane.setBottom(tabela);
		tabela.setPrefSize(100,300);
		gridPane.setHgap(10);
		gridPane.setVgap(5);
		gridPane.add(lblIdDesc, 0, 0);
		gridPane.add(txtId, 0, 1);
		txtId.setEditable(false);
		gridPane.add(lblDescricao, 0, 2);
		gridPane.add(lblNomeCliente, 0, 4);
		gridPane.add(txtDescricacao, 0, 3);
		gridPane.add(comboClientes, 0, 5);
		FlowPane flowPane = new FlowPane();
		flowPane.setHgap(20);
		flowPane.getChildren().addAll(btnSalvar, btnAtualizar, 
				btnDeletar, btnPesquisar);
		gridPane.add(flowPane, 0, 10);
		stage.setScene(scene);
		stage.setWidth(1072);
		stage.setHeight(600);
		preparaComboBox();
		btnAtualizar.setOnAction((e) ->{
			try {
				control.pesquisar();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		btnSalvar.setOnAction((e) ->{
			try {
				control.adicionar();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		stage.show();
	}
	public static void main(String[] args) {
		
		launch(args);
	}
}
