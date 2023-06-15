package entity;

import java.sql.Date;
import java.time.LocalDate;

public class Cliente {

	private long id;
	private String nome;
	private LocalDate dataNascimento;
	private Plano plano;
	private String planoNome;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public void setPlano(Plano plano) {
		this.plano = plano;
	}
	public Plano getPlano() {
		return plano;
	}
	
	public String getPlanoNome() {
		return plano.getNome();
	}
}
