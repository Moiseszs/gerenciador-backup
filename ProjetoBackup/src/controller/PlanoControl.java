package controller;

import java.sql.SQLException;
import java.util.List;

import dao.PlanoDAOImpl;
import entity.Plano;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PlanoControl {

	private ObservableList<Plano> planos = FXCollections.observableArrayList();
	private PlanoDAOImpl planoDAO;
	
	public PlanoControl() throws ClassNotFoundException, SQLException {
		planoDAO = new PlanoDAOImpl();
	}
	
	public void pesquisarTodos() throws SQLException {
		List<Plano> planosLista = planoDAO.pesquisarTodos();
		for(Plano plano : planosLista) {
			planos.add(plano);
		}
	}
	
	public ObservableList<Plano> getPlanos() {
		return planos;
	}
}
