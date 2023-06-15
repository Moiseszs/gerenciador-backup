package view;

import java.sql.SQLException;
import java.text.Format;

import controller.ClienteControl;
import controller.ComputadorControl;
import entity.Cliente;
import entity.Computador;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.css.converter.StringConverter;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javafx.util.*;
import javafx.stage.Stage;
import javafx.util.converter.LongStringConverter;

public class TelaComputador implements Tela{

	private ComboBox<Cliente> comboClientes = new ComboBox<>();
	private TextField txtDescricacao = new TextField("");
	private TextField txtId = new TextField("");
	private TextField txtIP = new TextField("");
	private Label lblIdDesc = new Label("ID");
	private Label lblDescricao = new Label("Descrição");
	private Label lblNomeCliente = new Label("Cliente");
	private Label lblIP = new Label("IP");
	private ComputadorControl control;
	private ClienteControl clienteControl;
	private Button btnSalvar = new Button("Salvar");
	private Button btnAtualizar = new Button("Atualizar");
	private Button btnDeletar = new Button("Deletar");
	private Button btnPesquisar = new Button("Pesquisar");
	private TableView<Computador> tabela = new TableView<Computador>();
	private BorderPane borderPane = new BorderPane();
	private Executor executor;
	
	@Override
	public Pane render() {
		try {
			control.pesquisarTodos();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return borderPane;
	}

	public TelaComputador(Executor executor) {
		this.executor = executor;
	}
	
	public TelaComputador() {
		
	}
	
	public void ligaCampos() {
		Bindings.bindBidirectional(txtDescricacao.textProperty(), control.getDescricao());
		Bindings.bindBidirectional(comboClientes.valueProperty(), control.getCliente());
		Bindings.bindBidirectional(txtIP.textProperty(), control.getIp());
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
		TableColumn<Computador, String> colCliente = new TableColumn<Computador, String>("Cliente");
		colCliente.setCellValueFactory(new PropertyValueFactory<Computador, String>("clienteNome"));
		
		TableColumn<Computador, String> colIP = new TableColumn<Computador, String>("IP");
		colIP.setCellValueFactory(new PropertyValueFactory<Computador, String>("IP"));
		TableColumn<Computador, Void> colExcluir = new TableColumn<>("Ações");
		
		Callback<TableColumn<Computador, Void>, TableCell<Computador, Void>> 
		acoes = new Callback<>() {

		@Override
		public TableCell<Computador, Void> call(TableColumn<Computador, Void> param) {
			final TableCell<Computador, Void> cell = new TableCell<>() {
				final Button btn = new Button("Excluir");
				
				{
					btn.setMaxSize(100, 2);
					btn.setOnAction(e ->{
						Computador pc = getTableView().getItems().get(getIndex());
						try {
							control.excluir(pc);
							tabela.refresh();
						} catch (Exception e2) {
							Alert alert = new Alert(AlertType.WARNING, 
									"Exclua primeiro as dependecias");
							alert.showAndWait();
							//e2.printStackTrace();						
							}
					});
				}
				@Override
			protected void updateItem(Void item, boolean empty) {
					super.updateItem(item, empty);
					if(empty) {
						setGraphic(null);
					}
					else {
						setGraphic(btn);
					}
				};
			};
			
			
			return cell;
		}};
		colExcluir.setCellFactory(acoes);
		
		
		
		
		tabela.getColumns().addAll(colId, colNome, colCliente, colIP, colExcluir);
		
		tabela.getSelectionModel().getSelectedItems().addListener(
				new ListChangeListener<Computador>() {
					@Override
					public void onChanged(Change<? extends Computador> c) {
						if(!c.getList().isEmpty()) {
							control.setCampos(c.getList().get(0));
						}
						
					}
				});
		
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
	public void start() {
		
		try {
			control = new ComputadorControl();
			clienteControl = new ClienteControl();
			//clienteControl.pesquisarTodos();
			//control.pesquisarTodos();
		}
		catch (Exception e) {
			System.out.println("erro");
		}
		ligaCampos();
		setComboContent();
		preparaTabela();
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
		gridPane.add(lblNomeCliente, 10, 2);
		gridPane.add(lblIP, 10, 0);
		gridPane.add(txtDescricacao, 0, 3);
		gridPane.add(comboClientes, 10, 3);
		gridPane.add(txtIP, 10, 1);
		FlowPane flowPane = new FlowPane();
		flowPane.setHgap(20);
		flowPane.getChildren().addAll(btnSalvar, btnAtualizar,  btnPesquisar);
		gridPane.add(flowPane, 0, 10);
		
		preparaComboBox();
		borderPane.getStyleClass().add("pane");
		borderPane.getStylesheets().add(getClass().getResource("style/compStyle.css").toExternalForm());
		btnAtualizar.setOnAction((e) ->{
			try {
				control.pesquisar();
			} catch (SQLException e1) {
				
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
		btnAtualizar.setOnAction(e ->{
			try {
				control.atualizar();
			} catch (Exception e2) {
				
			}
		});
	}

}
