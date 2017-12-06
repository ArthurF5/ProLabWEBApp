package com.moliveiralucas.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.moliveiralucas.connect.ConexaoMySQL;
import com.moliveiralucas.modelo.Exame;

public class ExamePersist {
	ConexaoMySQL mConexaoMySQL;
	/**
	 * Metodo criado para retornar ID do exame cadastrado no DB
	 * @param exame - Objeto do tipo Exame
	 * @return - Retorna o numero do ID de um determinado tipo de exame
	 */
	public Integer retornarID(Exame exame) {
		Integer retorno = 0;
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		ResultSet mResultSet = null;
		Statement mStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		String sql = "SELECT exameID FROM exame WHERE exame like '"+exame.getExame()+"'";
		try {
			mStatement = mConnection.createStatement();
			mResultSet = mStatement.executeQuery(sql);
			if(mResultSet.next()) {
				retorno = mResultSet.getInt("exameID");
			}
		}catch(SQLException e) {
			System.out.println("Retornar ID Exame ERRO: "+e.getMessage());
		}
		return retorno;
	}
	/**
	 * Metodo criado para inclusão de um Exame no DB
	 * @param exame
	 * @return	1 - Cadastrado com Sucesso! 
	 * 			2 - Ja possui cadastro com o nome informado
	 * 			3 - Houve um erro ao cadastrar no banco verificar log
	 */
	public Integer incluir(Exame exame) {
		Integer retorno = 0;
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		ResultSet mResultSet = null;
		Statement mStatement = null;
		PreparedStatement mPreparedStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		String sql = "SELECT * FROM exame WHERE exame = '"+exame.getExame()+"'";
		try {
			mStatement = mConnection.createStatement();
			mResultSet = mStatement.executeQuery(sql);
			if(!mResultSet.next()) {
				sql = "INSERT INTO exame(exame) VALUES(?)";
				mPreparedStatement = mConnection.prepareStatement(sql);
				mPreparedStatement.setString(1, exame.getExame());
				mPreparedStatement.executeUpdate();
				mPreparedStatement.close();
				retorno = 1;
			}else {
				retorno = 2;
			}
		}catch(SQLException e) {
			System.out.println("Incluir Exame ERRO: "+e.getMessage());
		}
		return retorno;
	}
	/**
	 * Metodo criado para alterar um cadastro de Exame
	 * @param exame
	 * @return True / False
	 */
	public boolean alterar(Exame exame) {
		Boolean retorno = false;
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		PreparedStatement mPreparedStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		String sql = "";
		try {
			sql = "UPDATE exame SET exame = ? WHERE exameID = ?";
			mPreparedStatement = mConnection.prepareStatement(sql);
			mPreparedStatement.setString(1, exame.getExame());
			mPreparedStatement.setInt(2, exame.getExameID());
			mPreparedStatement.executeUpdate();
			mPreparedStatement.close();
			retorno = true;
		}catch(SQLException e) {
			System.out.println("Exame ERRO: "+e.getMessage());
		}
		return retorno;
	}
	/**
	 * Metodo para excluir o cadastro de um exame
	 * @param id
	 * @return 	1 - Excluido com sucesso
	 * 			2 - Houve um erro ao excluir o cadastro, verificar log
	 */
	public Integer excluir(Integer id) {
		Integer retorno = 0;
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		PreparedStatement mPreparedStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		String sql = "";
		try {
			sql = "DELETE FROM exame WHERE exameID = ?";
			mPreparedStatement = mConnection.prepareStatement(sql);
			mPreparedStatement.setInt(1, id);
			mPreparedStatement.execute();
			mPreparedStatement.close();
			retorno = 1;
		}catch(SQLException e) {
			System.out.println("Exame ERRO: "+e.getMessage());
		}
		return retorno;
	}
	/**
	 * Metodo para listar todos os exames ou uma lista de exames
	 * @param parametroBusca
	 * @return Uma lista de exames indicados por um trecho do nome ou todos os exames
	 */
	public ArrayList<Exame> listar(String parametroBusca){
		ArrayList<Exame> exames = new ArrayList<Exame>();
		Exame exame;
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		ResultSet mResultSet = null;
		Statement mStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		String sql = "";
		try {
			if(!parametroBusca.equals("")) {
				sql = "SELECT * FROM exame WHERE exame LIKE '"+parametroBusca+"%' ORDER BY exame";
			}else {
				sql = "SELECT * FROM exame ORDER BY exame";
			}
			mStatement = mConnection.createStatement();
			mResultSet = mStatement.executeQuery(sql);
			while(mResultSet.next()) {
				exame = new Exame();
				exame.setExame(mResultSet.getString("exame"));
				exames.add(exame);
			}
		}catch(SQLException e) {
			System.out.println("Exame ERRO: "+e.getMessage());
			exames = null;
		}
		return exames;
	}
	/**
	 * Consultar se possui um determinado exame no banco
	 * @param parametroBusca
	 * @return Objeto do tipo Exame
	 */
	public Exame consulta(String parametroBusca) {
		Exame exame = new Exame();
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		ResultSet mResultSet = null;
		Statement mStatement = null;
		String sql = "SELECT * FROM exame WHERE exame = '"+parametroBusca+"'";
		mConnection = mConexaoMySQL.abreConexaoBD();
		try {
			mStatement = mConnection.createStatement();
			mResultSet = mStatement.executeQuery(sql);
			if(mResultSet.next()) {
				exame.setExame(mResultSet.getString("exame"));
			}else {
				exame = null;
			}
		}catch(SQLException e) {
			System.out.println("Consulta Exame ERRO: "+e.getMessage());
			exame = null;
		}
		return exame;
	}
}
