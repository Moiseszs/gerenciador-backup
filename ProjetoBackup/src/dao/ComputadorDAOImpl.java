package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DatabaseConnector;
import entity.Cliente;
import entity.Computador;
import javafx.collections.ObservableList;

public class ComputadorDAOImpl implements DAO<Computador> {

	private Connection connection; 
	
	public ComputadorDAOImpl() throws ClassNotFoundException, SQLException {
		connection = DatabaseConnector.connectMSSQL();
	}
	
	@Override
	public Computador adicionar(Computador computador) throws SQLException {
		String sql = "INSERT INTO computador(descricao, cliente_id) VALUES (?,?)";
		PreparedStatement statement = 
				connection.prepareStatement(sql);
		statement.setString(1, 
				computador.getDescricao());
		statement.setLong(2, 
				computador.getCliente().getId());
		statement.executeUpdate();
		ResultSet set = statement.getGeneratedKeys();
		if(set.next()) {
			computador.setId(set.getLong("id"));
		}
		return computador;
	}
	
	
	@Override
	public void deletar(long id) throws SQLException {
		// TODO Auto-generated method stub
	}
	@Override
	public Computador pesquisarPorNome(String texto) throws SQLException {
		Computador computador = new Computador();
		String sql = "SELECT computador.id, descricao, nome AS cliente FROM computador, cliente\r\n"
				+ "WHERE computador.cliente_id = cliente.id\r\n"
				+ "AND computador.descricao LIKE ?";
		texto = "%" + texto + "%";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, texto);
		ResultSet set = statement.executeQuery();
		while(set.next()) {
			Cliente cliente = new Cliente();
			cliente.setNome(set.getString("cliente"));
			computador.setDescricao(set.getString("descricao"));
			computador.setId(set.getLong("id"));
			computador.setCliente(cliente);
		}
		return computador;
	}
	@Override
	public List<Computador> pesquisarTodos() throws SQLException {
		List<Computador> pcs = new ArrayList();
		String sql = "SELECT computador.id, descricao, nome AS cliente FROM computador, cliente\r\n"
				+ "WHERE computador.cliente_id = cliente.id";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet set = statement.executeQuery();
		while(set.next()) {
			Computador computador = new Computador();
			Cliente cliente = new Cliente();
			computador.setId(set.getLong("id"));
			computador.setDescricao(set.getString("descricao"));
			cliente.setNome(set.getString("cliente"));
			computador.setCliente(cliente);
			pcs.add(computador);
			
		}
		return pcs;
	}
}