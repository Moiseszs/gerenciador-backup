package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DAOEstrangeiro<T, I> {

	public T adicionar(T t, I i) throws SQLException;
	
	public void deletar(long id) throws SQLException;
	
	public T pesquisarPorNome(String texto) throws SQLException;
	
	public List<T> pesquisarTodos() throws SQLException;
}
