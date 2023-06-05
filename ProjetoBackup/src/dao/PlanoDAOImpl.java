package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DatabaseConnector;
import entity.Cliente;
import entity.Plano;

public class PlanoDAOImpl implements DAO<Plano> {

	private Connection connection;
	
	public PlanoDAOImpl() throws ClassNotFoundException, SQLException {
		connection = 
				DatabaseConnector.connectMSSQL();
	}
	
	@Override
	public Plano adicionar(Plano plano) throws SQLException {
		String sql = "INSERT INTO plano(nome,limite) VALUES (?,?)";
		
		return null;
	}
	@Override
	public void deletar(long id) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Plano pesquisarPorNome(String texto) throws SQLException {
		Plano plano = new Plano();
		String sql = "SELECT * FROM plano WHERE nome = ?";
		PreparedStatement statement = 
				connection.prepareStatement(sql);
		texto = "%" + texto + "%";
		statement.setString(1, texto);
		ResultSet set = statement.executeQuery();
		if(set.next()) {
			plano.setId(set.getLong("id"));
			plano.setNome(set.getString("nome"));
			plano.setLimite(set.getInt("limite"));
		}
		return plano;
	}
	@Override
	public List<Plano> pesquisarTodos() throws SQLException {
		List<Plano> planos = new ArrayList<>();
		String sql = "SELECT * FROM plano";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet set = statement.executeQuery();
		while(set.next()) {
			Plano plano = new Plano();
			plano.setId(set.getLong("id"));
			plano.setNome(set.getString("nome"));
			plano.setLimite(set.getInt("limite"));
			planos.add(plano);
			
		}
		return planos;
	}
}
