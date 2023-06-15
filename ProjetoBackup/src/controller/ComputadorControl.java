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
		pc.setIP(Ip.get());
		pc.setCliente(cliente);
		computadorDAO.adicionar(pc);
		pesquisarTodos();
	}
	
	public void pesquisar() throws SQLException {
		Computador computador = computadorDAO.pesquisarPorNome(descricao.get());
		clienteProperty.set(computador.getCliente());
		id.set(computador.getId());
		descricao.set(computador.getDescricao());
	}
	
	public void pesquisarTodos() throws SQLException {
		List<Computador> pcs = computadorDAO.pesquisarTodos();
		computadores.clear();
		for(Computador pc : pcs) {
				computadores.add(pc);
		}
		
		
	}
	
	public void excluir(Computador c) throws SQLException {
		computadorDAO.deletar(c.getId());
		computadores.clear();
		pesquisarTodos();
	}
	
	public void atualizar() throws SQLException {
		Computador pc = new Computador();
		pc.setCliente(clienteProperty.get());
		pc.setDescricao(descricao.get());
		pc.setId(id.get());
		pc.setIP(Ip.get());
		computadorDAO.atualizar(pc);
		computadores.clear();
		pesquisarTodos();
	}
	
	public void setCampos(Computador c) {
		id.set(c.getId());
		descricao.set(c.getDescricao());
		Ip.set(c.getIP());
		clienteProperty.set(c.getCliente());
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

	public StringProperty getIp() {
		return Ip;
	}
	
	public void setClienteProperty(ObjectProperty<Cliente> clienteProperty) {
		this.clienteProperty = clienteProperty;
	}
	
}
