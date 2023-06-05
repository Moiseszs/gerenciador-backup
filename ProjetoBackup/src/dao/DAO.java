package dao;

import java.sql.SQLException;
import java.util.List;

public interface DAO <T> {

	public T adicionar(T t) throws SQLException;
	
	public void deletar(long id) throws SQLException;
	
	public T pesquisarPorNome(String texto) throws SQLException;
	
	public List<T> pesquisarTodos() throws SQLException;
}
