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

public class EnderecoPersist {
	ConexaoMySQL mConexaoMySQL;
	LaboratorioPersist mLaboratorioPersist = new LaboratorioPersist();
	/**
	 * Consulta Endereco de um determinado Laboratorio
	 * @param lab - Objeto do tipo Laboratorio
	 * @return Lista de endereços 
	 */
	public ArrayList<Endereco> consultarEndereco(Laboratorio lab){
		ArrayList<Endereco> endereco = new ArrayList<Endereco>();
		Endereco end;
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		ResultSet mResultSet = null;
		Statement mStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		String sql = "SELECT * FROM endereco WHERE labID = "+lab.getLabID();
		try {
			mStatement = mConnection.createStatement();
			mResultSet = mStatement.executeQuery(sql);
			while(mResultSet.next()) {
				end = new Endereco();	
				end.setLogradouro(mResultSet.getString("logradouro"));
				end.setCidade(mResultSet.getInt("cidadeID"));
				end.setEstado(mResultSet.getInt("ufID"));
				end.setNumero(mResultSet.getString("numero"));
				endereco.add(end);
			}
		}catch(SQLException e) {
			System.out.println("Consulta Endereco ERRO: "+ e.getMessage());
			endereco = null;
		}
		return endereco;
	}
	/**
	 * Inclui endereços (filiais) para laboratorios ja cadastrados
	 * @param lab - Objeto do tipo Laboratorio
	 */
	public Integer incluir(Laboratorio lab, Endereco end) {
		Integer retorno = 0;
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		ResultSet mResultSet = null;
		Statement mStatement = null;
		PreparedStatement mPreparedStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		String sql = "SELECT * FROM endereco WHERE labID = "+lab.getLabID()+" AND rua LIKE '"+end.getLogradouro()+"'";
		try {
			mStatement = mConnection.createStatement();
			mResultSet = mStatement.executeQuery(sql);
			if(!mResultSet.next()) {
				sql = "INSERT INTO endereco (labID, rua, cidadeID, ufID, numero) VALUES (?, ?, ?, ?, ?)";
				mPreparedStatement = mConnection.prepareStatement(sql);
				mPreparedStatement.setInt(1, lab.getLabID());
				mPreparedStatement.setString(2, end.getLogradouro());
				mPreparedStatement.setInt(3, end.getCidade());
				mPreparedStatement.setInt(4, end.getEstado());
				mPreparedStatement.setString(5, end.getNumero());
				mPreparedStatement.executeUpdate();
				mPreparedStatement.close();
				retorno = 1;
			}else {
				retorno = 2;
			}
		}catch(SQLException e) {
			System.out.println("Incluir Endereco ERRO: "+e.getMessage());
		}
		return retorno;
	}

	/**
	 * exclui um endereço (filial) de um laboratorio
	 * @param id - ID do laboratorio
	 */
	public void excluir(Integer id) {
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		PreparedStatement mPreparedStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		try {
			String sql = "DELETE FROM endereco WHERE endID = ?";
			mPreparedStatement = mConnection.prepareStatement(sql);
			mPreparedStatement.setInt(1, id);
			mPreparedStatement.execute();
			mPreparedStatement.close();
		}catch(SQLException e) {
			System.out.println("Excluir Endereco ERRO: "+e.getMessage());
		}
	}
	/**
	 * Exclui todos os endereços de um determinado laboratorio
	 * @param id - ID do laboratorio
	 */
	public Integer excluirEnderecoLaboratorio(Integer id) {
		Integer retorno = 0;
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		PreparedStatement mPreparedStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		try {
			String sql = "DELETE FROM endereco WHERE labID = ?";
			mPreparedStatement = mConnection.prepareStatement(sql);
			mPreparedStatement.setInt(1, id);
			mPreparedStatement.execute();
			mPreparedStatement.close();
			retorno = 1;
		}catch(SQLException e) {
			System.out.println("Excluir Endereco ERRO: "+e.getMessage());
		}
		return retorno;
	}
	
	public ArrayList<Endereco> buscarLaboratorioCidade(Integer cidadeID){
		ArrayList<Endereco> mArrayEndereco = new ArrayList<Endereco>();
		Endereco end = new Endereco();
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		ResultSet mResultSet = null;
		Statement mStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		try {
			String sql = "SELECT * FROM endereco WHERE cidadeID = "+cidadeID;
			mStatement = mConnection.createStatement();
			mResultSet = mStatement.executeQuery(sql);
			while(mResultSet.next()) {
				end = new Endereco();
				end.setEndID(mResultSet.getInt("endID"));
				end.setLabID(mResultSet.getInt("labID"));
				end.setCidade(mResultSet.getInt("cidadeID"));
				end.setLogradouro(mResultSet.getString("rua"));
				end.setNumero(mResultSet.getString("numero"));
				end.setEstado(mResultSet.getInt("ufID"));
				mArrayEndereco.add(end);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mArrayEndereco;
	}
}
