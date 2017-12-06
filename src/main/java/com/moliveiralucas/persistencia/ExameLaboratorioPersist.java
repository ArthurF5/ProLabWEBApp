package com.moliveiralucas.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.moliveiralucas.connect.ConexaoMySQL;
import com.moliveiralucas.modelo.Exame;
import com.moliveiralucas.modelo.ExameLaboratorio;
import com.moliveiralucas.modelo.Laboratorio;

public class ExameLaboratorioPersist {
	ConexaoMySQL mConexaoMySQL;
	/**
	 * 
	 * @param id
	 * @return
	 */
	public ArrayList<ExameLaboratorio> buscarExameLaboratorio(Integer id){
		ArrayList<ExameLaboratorio> mExameLaboratorio = new ArrayList<ExameLaboratorio>();
		ExameLaboratorio mExame;
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		ResultSet mResultSet = null;
		Statement mStatement = null;
		String sql = "SELECT * FROM exameLaboratorio WHERE exameID = "+id;
		mConnection = mConexaoMySQL.abreConexaoBD();
		try {
			mStatement = mConnection.createStatement();
			mResultSet = mStatement.executeQuery(sql);
			while(mResultSet.next()) {
				mExame = new ExameLaboratorio();
				mExame.setLabID(mResultSet.getInt("labID"));
				mExame.setExameID(mResultSet.getInt("exameID"));
				mExame.setValor(mResultSet.getDouble("valor"));
				mExameLaboratorio.add(mExame);
			}
		}catch(SQLException e) {
			System.out.println("Buscar Exames de um Laboratorio ERRO: "+e.getMessage());
			mExameLaboratorio = null;
		}
		return mExameLaboratorio;
	}
	
	/**
	 * Vincular um exame cadastrado a um Laboratorio cadastrado
	 * @param exame
	 * @param laboratorio
	 * @return 	1 - Cadastrado com Sucesso! 
	 * 			2 - Ja possui cadastro com o nome informado
	 * 			3 - Houve um erro ao cadastrar no banco verificar log
	 */
	public Integer cadastrarExameLaboratorio(Exame exame, Laboratorio laboratorio) {
		Integer retorno = 0;
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		ResultSet mResultSet = null;
		Statement mStatement = null;
		PreparedStatement mPreparedStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		String sql = "SELECT * FROM exameLaboratorio WHERE exameID = "+exame.getExameID()+" AND labID = "+laboratorio.getLabID();
		try {
			mStatement = mConnection.createStatement();
			mResultSet = mStatement.executeQuery(sql);
			if(!mResultSet.next()) {
				sql = "INSERT INTO exameLaboratorio(labID, exameID, valor) VALUES(?, ?, ?)";
				mPreparedStatement = mConnection.prepareStatement(sql);
				mPreparedStatement.setInt(1, laboratorio.getLabID());
				mPreparedStatement.setInt(2, exame.getExameID());
				mPreparedStatement.setDouble(3, exame.getValor());
				mPreparedStatement.executeUpdate();
				mPreparedStatement.close();
				retorno = 1;
			}else {
				retorno = 2;
			}
		}catch(SQLException e) {
			System.out.println("Incluir Exame ERRO: "+e.getMessage());
			retorno = 3;
		}
		return retorno;
	}
	/**
	 * 
	 * @param exame
	 * @param laboratorio
	 * @return
	 */
	public Integer excluirExameLaboratorio(Exame exame,Laboratorio laboratorio) {
		Integer retorno = 0;
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		PreparedStatement mPreparedStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		try {
			String sql = "DELETE FROM exameLaboratorio WHERE exameID = ? AND labID = ";
			mPreparedStatement = mConnection.prepareStatement(sql);
			mPreparedStatement.setInt(1, exame.getExameID());
			mPreparedStatement.setInt(2, laboratorio.getLabID());
			mPreparedStatement.execute();
			mPreparedStatement.close();
			retorno = 1;
		}catch(SQLException e) {
			System.out.println("Excluir Exame do Laboratorio ERRO: "+e.getMessage());
		}
		return retorno;
	}
}
