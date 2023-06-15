package controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import dao.AssinaturaDAOImpl;
import dao.ClienteDAOImpl;
import dao.PlanoDAOImpl;
import entity.Assinatura;
import entity.Cliente;
import entity.Computador;
import entity.Plano;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClienteControl {

	private LongProperty id = new SimpleLongProperty();
	private StringProperty nome = new SimpleStringProperty("");
	private ObjectProperty<LocalDate> dataNascimento =
			new SimpleObjectProperty<>();
	private ObjectProperty<Plano> planoProp = new SimpleObjectProperty<Plano>();
	private ObservableList<Cliente> clientes = 
			FXCollections.observableArrayList();
	
	ClienteDAOImpl clienteDAO;
	PlanoDAOImpl planoDAO;
	AssinaturaDAOImpl assinaturaDAO;
	
	public ClienteControl() throws ClassNotFoundException, SQLException {
		clienteDAO = new ClienteDAOImpl();
		planoDAO = new PlanoDAOImpl();
		assinaturaDAO = new AssinaturaDAOImpl();
	}
	
	public void addTeste() {
		for(int i = 0; i < 5; i++) {
			Cliente cliente = new Cliente();
			cliente.setNome("ze");
			cliente.setId(2);
			clientes.add(cliente);
		}
		
	}
	
	
	public void salvar() throws ClassNotFoundException, SQLException {
		Cliente cliente = new Cliente();
		Plano plano = planoProp.get();
		cliente.setNome(nome.get());
		cliente.setDataNascimento(dataNascimento.get());
		cliente = clienteDAO.adicionar(cliente);
		clientes.add(cliente);
		salvarAssinatura(cliente, plano);
	}
	
	public void salvarAssinatura(Cliente cliente, Plano plano) throws SQLException {
		Assinatura assinatura = new Assinatura();
		assinatura.setCliente(cliente);
		assinatura.setPlano(plano);
		assinaturaDAO.adicionar(assinatura);
	}
	
	public void pesquisarTodos() throws SQLException {
		List<Cliente> lista = clienteDAO.pesquisarTodos();
		for(Cliente cliente : lista) {
			clientes.add(cliente);
		}
	}
	
	public void pesquisar() throws SQLException {
		Cliente cliente = clienteDAO.pesquisarPorNome(nome.get());
		setCampos(cliente);
	}
	
	
	public void setCampos(Cliente c) {
		id.set(c.getId());
		nome.set(c.getNome());
		planoProp.set(c.getPlano());
		dataNascimento.set(c.getDataNascimento());
	}
	

	public void excluir(Cliente c) throws SQLException {
		assinaturaDAO.deletar(c.getId());
		clienteDAO.deletar(c.getId());
		clientes.remove(c);
	}
	
	public LongProperty getId() {
		return id;
	}

	public StringProperty getNome() {
		return nome;
	}

	public ObjectProperty<LocalDate> getDataNascimento() {
		return dataNascimento;
	}
	
	public ObjectProperty<Plano> getPlano() {
		return planoProp;
	}
	
	public ObservableList<Cliente> getClientes() {
		return clientes;
	}
	
}
