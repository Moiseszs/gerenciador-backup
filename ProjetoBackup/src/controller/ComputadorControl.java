package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ClienteDAOImpl;
import dao.ComputadorDAOImpl;
import entity.Cliente;
import entity.Computador;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ComputadorControl {

	private ComputadorDAOImpl computadorDAO;
	private ClienteDAOImpl clienteDAO;
	
	private LongProperty id = new SimpleLongProperty();
	private StringProperty descricao = new SimpleStringProperty("");
	private ObjectProperty<Cliente> clienteProperty = new SimpleObjectProperty<>();
	private StringProperty Ip = new SimpleStringProperty("");
	
	private ObservableList<Computador> computadores =
			FXCollections.observableArrayList();

		
	public ComputadorControl() throws ClassNotFoundException, SQLException {
		computadorDAO = new ComputadorDAOImpl();
		clienteDAO = new ClienteDAOImpl();
		
	}
	
	public void adicionar() throws Exception{
		Cliente cliente = clienteProperty.get();
		Computador pc = new Computador();
		pc.setDescricao(descricao.get());
		pc.setCliente(cliente);
		computadorDAO.adicionar(pc);
	}
	
	public void pesquisar() throws SQLException {
		Computador computador = computadorDAO.pesquisarPorNome(descricao.get());
		clienteProperty.set(computador.getCliente());
		id.set(computador.getId());
		descricao.set(computador.getDescricao());
	}
	
	public void pesquisarTodos() throws SQLException {
		List<Computador> pcs = computadorDAO.pesquisarTodos();
		for(Computador pc : pcs) {
			computadores.add(pc);
		}
	}
	
	public void print() {
		System.out.println(clienteProperty.get().getId());
	}
	
	
	public StringProperty getDescricao() {
		return descricao;
	}
	public ObjectProperty<Cliente> getCliente() {
		return clienteProperty;
	}
	public ObservableList<Computador> getComputadores() {
		return computadores;
	}
	public LongProperty getId() {
		return id;
	}

	public void setClienteProperty(ObjectProperty<Cliente> clienteProperty) {
		this.clienteProperty = clienteProperty;
	}
	
}
