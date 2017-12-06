package com.moliveiralucas.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.moliveiralucas.connect.ConexaoMySQL;
import com.moliveiralucas.modelo.Endereco;
import com.moliveiralucas.modelo.Laboratorio;

public class LaboratorioPersist {
	ConexaoMySQL mConexaoMySQL;
	/**
	 * Retorna o ID de um Laboratorio
	 * @param lab - Objeto do tipo Laboratorio
	 * @return - ID Inteiro de um laboratorio
	 */
	public Integer retornaID(Laboratorio lab) {
		Integer retorno = 0;
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		ResultSet mResultSet = null;
		Statement mStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		String sql = "SELECT labID FROM laboratorio WHERE laboratorio LIKE '"+lab.getLaboratorio()+"'";
		try {
			mStatement = mConnection.createStatement();
			mResultSet = mStatement.executeQuery(sql);
			if(mResultSet.next()) {
				retorno = mResultSet.getInt("labID");
			}
		}catch(SQLException e) {
			System.out.println("RetornaID Laboratorio ERRO: "+e.getMessage());
		}
		return retorno;
	}
	/**
	 * Inclui um novo laboratorio no DB
	 * @param lab - Objeto do tipo Laboratorio
	 * @return 	1 - Cadastrado com Sucesso! 
	 * 			2 - Ja possui cadastro com o nome informado
	 * 			3 - Houve um erro ao cadastrar no banco verificar log
	 */
	public Integer incluir(Laboratorio lab) {
		Integer retorno = 0;
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		ResultSet mResultSet = null;
		Statement mStatement = null;
		PreparedStatement mPreparedStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		String sql = "SELECT * FROM laboratorio WHERE laboratorio LIKE '"+lab.getLaboratorio()+"'";
		try {
			mStatement = mConnection.createStatement();
			mResultSet = mStatement.executeQuery(sql);
			if(!mResultSet.next()) {
				sql = "INSERT INTO laboratorio(laboratorio) VALUES (?)";
				mPreparedStatement = mConnection.prepareStatement(sql);
				mPreparedStatement.setString(1, lab.getLaboratorio());
				mPreparedStatement.executeUpdate();
				mPreparedStatement.close();
				retorno = 1;
			}else {
				retorno = 2;
			}
		}catch(SQLException e) {
			System.out.println("Incluir Laboratorio ERRO: "+e.getMessage());
			retorno = 3;
		}
		return retorno;
	}
	/**
	 * Altera os dados cadastrados no banco de um determinado laboratorio
	 * @param lab
	 * @return True / False
	 */
	public boolean alterar(Laboratorio lab) {
		Boolean retorno = false;
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		PreparedStatement mPreparedStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		String sql = "";
		try {
			sql = "UPDATE laboratorio SET laboratorio = ? WHERE labID = ?";
			mPreparedStatement = mConnection.prepareStatement(sql);
			mPreparedStatement.setString(1, lab.getLaboratorio());
			mPreparedStatement.executeUpdate();
			mPreparedStatement.close();
			retorno = true;
		}catch(SQLException e) {
			System.out.println("Alterar Laboratorio ERRO: "+e.getMessage());
		}
		return retorno;
	}
	/**
	 * Exclui um Laboratorio 
	 * @param lab
	 * @return 	1 - Excluido com Sucesso 
	 * 			2 - Houve um erro ao excluir no banco verificar log
	 */
	public Integer excluir(Laboratorio lab) {
		Integer retorno = 0;
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		PreparedStatement mPreparedStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		String sql = "";
		try {
			sql = "DELETE FROM laboratorio WHERE labID = ?";
			mPreparedStatement = mConnection.prepareStatement(sql);
			mPreparedStatement.setInt(1, retornaID(lab));
			mPreparedStatement.execute();
			mPreparedStatement.close();
			retorno = 1;
		}catch(SQLException e) {
			System.out.println("Excluir Laboratorio ERRO: "+e.getMessage());
			retorno = 2;
		}
		return retorno;
	}
	/**
	 * Realiza a busca por laboratorios 
	 * @param parametroBusca
	 * @return Lista de laboratorios
	 */
	public ArrayList<Laboratorio> listar(String parametroBusca){
		ArrayList<Laboratorio> laboratorio = new ArrayList<Laboratorio>();
		Laboratorio lab;
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		ResultSet mResultSet = null;
		Statement mStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		String sql = "";
		if(parametroBusca.equals("null")) {
			sql = "SELECT * FROM laboratorio ORDER BY laboratorio";
		}else {
			sql = "SELECT * FROM laboratorio WHERE laboratorio LIKE '"+parametroBusca+"%' ORDER BY laboratorio";
		}
		try {
			mStatement = mConnection.createStatement();
			mResultSet = mStatement.executeQuery(sql);
			while(mResultSet.next()) {
				lab = new Laboratorio();
				lab.setLaboratorio(mResultSet.getString("laboratorio"));
				laboratorio.add(lab);
			}
		}catch (SQLException e) {
			System.out.println("Listar laboratorios ERRO: "+e.getMessage());
			laboratorio = null;
		}
		return laboratorio;
	}
	/**
	 * Realiza a pesquisa de um laboratorio e seus endereços
	 * @param parametroBusca
	 * @return Objeto do tipo Laboratorio
	 */
	public Laboratorio consulta(String parametroBusca) {
		ArrayList<Endereco> endereco = new ArrayList<Endereco>();
		Laboratorio lab = new Laboratorio();
		Endereco end = null;
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		ResultSet mResultSet = null, mResultSetEndereco = null;
		Statement mStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		String sql = "SELECT * FROM laboratorio WHERE laboratorio = '"+parametroBusca+"'";
		try {
			mStatement = mConnection.createStatement();
			mResultSet = mStatement.executeQuery(sql);
			if(mResultSet.next()) {
				lab.setLaboratorio(mResultSet.getString(2));
				sql = "SELECT * FROM endereco WHERE labID = "+retornaID(lab);
				mResultSetEndereco = mStatement.executeQuery(sql);
				while(mResultSetEndereco.next()) {
					end = new Endereco();
					end.setEndereco(mResultSetEndereco.getString("laboratorio"));
					endereco.add(end);
				}
				lab.setEndereco(endereco);
			}else {
				lab = null;
			}
		}catch(SQLException e) {
			System.out.println("Consulta Laboratorio ERRO: "+e.getMessage());
		}
		return lab;
	}
//	public Integer retornaEnderecoID(Laboratorio lab) {
//	Integer retorno = 0;
//	mConexaoMySQL = new ConexaoMySQL();
//	Connection mConnection = null;
//	ResultSet mResultSet = null;
//	Statement mStatement = null;
//	mConnection = mConexaoMySQL.abreConexaoBD();
//	String sql = "SELECT endID FROM endereco WHERE endereco LIKE '"+lab.getEndereco()+"'";
//	try {
//		mStatement = mConnection.createStatement();
//		mResultSet = mStatement.executeQuery(sql);
//		if(mResultSet.next()) {
//			retorno = mResultSet.getInt(1);
//		}
//	}catch(SQLException e) {
//		System.out.println("RetornaID Laboratorio ERRO: "+e.getMessage());
//	}
//	return retorno;
//}
}
