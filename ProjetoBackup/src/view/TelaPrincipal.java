package view;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TelaPrincipal extends Application implements Executor{

	private Map<String, Tela> telas = new HashMap<>();
	private TabPane tab;
	private Tab tabCliente;
	private Tab tabComputador;
	private Tab tabBackup;
	
	@Override
	public void start(Stage stage) throws Exception {
		BorderPane bPane = new BorderPane();
		gerarTelas();
		
		tab = new TabPane();
		tabCliente = new Tab("Cliente", new Label("Cliente"));
		tabComputador = new Tab("Computador", new Label("Computador"));
		tabBackup = new Tab("Backup", new Label("Backup"));
		
		MenuBar menu = new MenuBar();
		Menu menuPrincipal = new Menu("Telas");
		menu.getMenus().addAll(menuPrincipal);
		
		MenuItem menuCliente = new MenuItem("Cliente");
		MenuItem menuComputador = new MenuItem("Computador");
		MenuItem menuBackup = new MenuItem("Backup");
		menuPrincipal.getItems().addAll(menuCliente, menuComputador, menuBackup);
		
		menuCliente.setOnAction((e) ->{
			executar("Cliente");
		});
		
		menuComputador.setOnAction((e)->{
			executar("Computador");
		});
		
		menuBackup.setOnAction((e)->{
			executar("Backup");
		});
		
		bPane.setTop(menu);
		bPane.setCenter(tab);

			
		bPane.getStylesheets().add(getClass().getResource("principalStyle.css").toExternalForm());
		
		Scene scene = new Scene(bPane, 800, 600);
		stage.setScene(scene);
		stage.setMaximized(true);
		stage.setTitle("Sistema de Backup");
		stage.getIcons().add(
				new Image("static/backup.png"));
		stage.show();
	}
	
	
	public Tela getTela(String nome) {
		return telas.get(nome); 
	}
	
	public void gerarTelas() {
		telas.put("Cliente", new TelaCliente(this));
		telas.put("Computador", new TelaComputador(this));
		telas.put("Backup", new TelaBackup(this));
		for(Tela tela : telas.values()) {
			tela.start();
		}
	}

	@Override
	public void executar(String cmd) {
		if("Cliente".equals(cmd)) {
			tabCliente.setContent(getTela("Cliente").render());
			tab.getTabs().add(tabCliente);
		}
		else if("Computador".equals(cmd)) {
			tabComputador.setContent(getTela("Computador").render());
			tab.getTabs().add(tabComputador);
		}
		else if("Backup".equals(cmd)) {
			tabBackup.setContent(getTela("Backup").render());
			tab.getTabs().add(tabBackup);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
