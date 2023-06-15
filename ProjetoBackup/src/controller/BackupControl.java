package controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.BackupDAOImpl;
import dao.ComputadorDAOImpl;
import entity.Backup;
import entity.Computador;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BackupControl {

	private LongProperty id = new SimpleLongProperty();
	private StringProperty descricao = new SimpleStringProperty();
	private ObjectProperty<LocalDate> dataInicio = 
			new SimpleObjectProperty<>(LocalDate.now());
	private ObjectProperty<LocalDate> dataFim = 
			new SimpleObjectProperty<>();
	private ObjectProperty<Computador> computadorProp =
			new SimpleObjectProperty<>();
	
	BackupDAOImpl backupDAO;
	ComputadorDAOImpl computadorDAO;
	
	ObservableList<Backup> backups = 
			FXCollections.observableArrayList();
	
	public BackupControl() throws ClassNotFoundException, SQLException {
		backupDAO = new BackupDAOImpl();
		computadorDAO = new ComputadorDAOImpl();
	}
	
	public void salvar() throws SQLException {
		Backup backup = new Backup();
		Computador computador = computadorProp.get();
		
		backup.setComputador(computador);
		backup.setDescricao(descricao.get());
		backup.setDataInicio(dataInicio.get());
		backup.setDataFim(dataFim.get());
		backupDAO.adicionar(backup);
		backups.add(backup);
	}
	
	public void pesquisarTodos() throws SQLException {
		List<Backup> lista = backupDAO.pesquisarTodos();
		backups.clear();
		for(Backup backup : lista) {
			backups.add(backup);
		}
	}
	
	public void excluir(Backup b) {
		
	}
	

	public LongProperty getId() {
		return id;
	}

	public StringProperty getDescricao() {
		return descricao;
	}

	public ObjectProperty<LocalDate> getDataInicio() {
		return dataInicio;
	}

	public ObjectProperty<LocalDate> getDataFim() {
		return dataFim;
	}

	public ObjectProperty<Computador> getComputadorProp() {
		return computadorProp;
	}
	
	public ObservableList<Backup> getBackups() {
		return backups;
	}
	
}
