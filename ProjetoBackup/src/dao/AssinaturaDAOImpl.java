package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DatabaseConnector;
import entity.Assinatura;
import entity.Plano;

public class AssinaturaDAOImpl implements DAO<Assinatura> {

	private Connection connection;
	
	public AssinaturaDAOImpl() throws ClassNotFoundException, SQLException {
		connection = DatabaseConnector.connectMYSQL();
	}
	
	@Override
	public Assinatura adicionar(Assinatura assinatura) throws SQLException {
		String sql = "INSERT INTO assinatura(cliente_id, plano_id) "
				+ "VALUES (?,?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, assinatura.getCliente().getId());
		statement.setLong(2, assinatura.getPlano().getId());
		statement.executeUpdate();
		//ResultSet set = statement.getGeneratedKeys();
		
		return assinatura;
	}
	@Override
	public void deletar(long id) throws SQLException {
		String sql = "DELETE FROM assinatura WHERE cliente_id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, id);
		statement.executeUpdate();
		
	}
	@Override
	public Assinatura pesquisarPorNome(String texto) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Assinatura> pesquisarTodos() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void atualizar(Assinatura t) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
