package view;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import controller.ClienteControl;
import controller.ComputadorControl;
import controller.PlanoControl;
import entity.Cliente;
import entity.Plano;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableObjectValue;
import javafx.geometry.Insets;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import net.sourceforge.jtds.jdbc.DateTime;

public class TelaCliente extends Application {

	private TextField txtNome = new TextField();
	//private TextField txtDataNasc = new TextField();
	private TextField txtId = new TextField();
	private Label lblId = new Label("ID");
	private Label lblNome = new Label("Nome");
	private Label lblDataNasc = new Label("Data de Nascimento");
	private DatePicker dataNascPicker = new DatePicker();
	private Label lblPlano = new Label("Plano");
	private ComboBox<Plano> comboPlano = new ComboBox<>();
	private Pane pane = new Pane();
	private ClienteControl control;
	private PlanoControl planoControl;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private TableView<Cliente> tabela = new TableView<Cliente>();
	private Button btnSalvar = new Button("Salvar");
	private Button btnPesquisar = new Button("Pesquisar");
	private Button btnAtualizar = new Button("Atualizar");
	
	public void ligaCampos() {
		Bindings.bindBidirectional(txtNome.textProperty(), 
				control.getNome());
		Bindings.bindBidirectional(dataNascPicker.valueProperty(), 
				control.getDataNascimento());
		Bindings.bindBidirectional(comboPlano.valueProperty(), 
				control.getPlano());
	}
	
	public void preparaComboBox() {
		
		comboPlano.setItems(planoControl.getPlanos());
		comboPlano.setPromptText("Selecione um plano");
		comboPlano.setConverter(new javafx.util.StringConverter<Plano>() {
			
			@Override
			public String toString(Plano object) {
				if(object == null) {
					return "";
				}
				return object.getNome();
			}
			
			@Override
			public Plano fromString(String string) {
				return null;
			}

		});
	}
	
	public void adicionar() {
		try {
			control.salvar();
		} catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	
	public void isFiled() {
		BooleanBinding isTxtNomeEmpty = control.getNome().isEmpty(); 
		BooleanBinding isPlanoNull = control.getPlano().isNull();
		btnSalvar.disableProperty().bind(isPlanoNull.or(isTxtNomeEmpty));
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		try {
			control = new ClienteControl();
			planoControl = new PlanoControl();
			planoControl.pesquisarTodos();
		}
		catch(SQLException | ClassNotFoundException e) {
			Alert alert = new Alert(AlertType.ERROR, 
					"Erro no backend");
			alert.showAndWait();
			System.exit(1);
		}
		Scene sc = new Scene(pane);
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(15));
		Scene scene = new Scene(borderPane);
		GridPane gridPane = new GridPane();
		borderPane.setTop(gridPane);
		borderPane.setBottom(tabela);
		tabela.setPrefSize(100,300);
		gridPane.setHgap(10);
		gridPane.setVgap(5);
		gridPane.add(lblId, 0, 0);
		gridPane.add(txtId, 0, 1);
		gridPane.add(lblNome, 0, 3);
		gridPane.add(txtNome, 0, 4);
		gridPane.add(lblDataNasc, 10, 0);
		gridPane.add(dataNascPicker, 10, 1);
		gridPane.add(lblPlano, 10, 3);
		gridPane.add(comboPlano, 10, 4);
		txtId.setEditable(false);
		FlowPane flowPane = new FlowPane();
		gridPane.add(flowPane, 0, 10);
		flowPane.getChildren().addAll(btnSalvar, btnPesquisar);
		btnSalvar.setOnAction((e) -> {
			adicionar();
		});
		stage.setScene(scene);
		stage.setWidth(1024);
		stage.setHeight(600);
		stage.setTitle("Cadastro de Cliente");
		isFiled();
		ligaCampos();
		preparaComboBox();
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
