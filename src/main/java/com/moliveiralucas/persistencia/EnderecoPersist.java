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
	public Endereco geraEndereco(Endereco end) {
		end.setEndereco(end.getLogradouro(), end.getCidade(),end.getEstado(), end.getNumero());
		return end;
	}
	/**
	 * 
	 * @param lab
	 * @param end
	 * @return
	 */
	public Integer retornaID(Laboratorio lab, Endereco end) {
		Integer retorno = 0;
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		ResultSet mResultSet = null;
		Statement mStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		String sql = "SELECT exameID FROM exame WHERE endereco LIKE '"+end.getEndereco()+"' AND labID = "+lab.getLabID();
		try {
			mStatement = mConnection.createStatement();
			mResultSet = mStatement.executeQuery(sql);
			if(mResultSet.next()) {
				retorno = mResultSet.getInt("endID");
			}
		}catch(SQLException e) {
			System.out.println("Retornar ID Endereço Filial Laboratorio ERRO: "+e.getMessage());
		}
		return retorno;
	}
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
				end.setEndereco(mResultSet.getString("endereco"));
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
		String sql = "SELECT * FROM endereco WHERE labID = "+lab.getLabID()+" AND endereco 'LIKE "+end.getEndereco()+"'";
		try {
			mStatement = mConnection.createStatement();
			mResultSet = mStatement.executeQuery(sql);
			if(!mResultSet.next()) {
				sql = "INSERT INTO endereco (labID, endereco, rua, cidade, estado, numero) VALUES (?, ?, ?, ?, ?, ?)";
				mPreparedStatement = mConnection.prepareStatement(sql);
				mPreparedStatement.setInt(1, lab.getLabID());
				mPreparedStatement.setString(2, end.getEndereco());
				mPreparedStatement.setString(3, end.getLogradouro());
				mPreparedStatement.setInt(4, end.getCidade());
				mPreparedStatement.setInt(5, end.getEstado());
				mPreparedStatement.setString(6, end.getNumero());
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

	//	/**
	//	 * Inclui endereços (filiais) para laboratorios ja cadastrados
	//	 * @param lab - Objeto do tipo Laboratorio
	//	 */
	//	public void incluirMulti(Laboratorio lab, Endereco end) {
	//		Integer retorno = 0;
	//		mConexaoMySQL = new ConexaoMySQL();
	//		Connection mConnection = null;
	//		PreparedStatement mPreparedStatement = null;
	//		mConnection = mConexaoMySQL.abreConexaoBD();
	//		try {
	//			for(Integer i = 0; i < lab.getEndereco().size(); i++) {
	//				String sql = "INSERT INTO endereco (labID, endereco) VALUES (?, ?)";
	//				mPreparedStatement = mConnection.prepareStatement(sql);
	//				mPreparedStatement.setInt(1, mLaboratorioPersist.retornaID(lab));
	//				mPreparedStatement.setString(2, lab.getEndereco().get(i).getEndereco());
	//				mPreparedStatement.executeUpdate();
	//				mPreparedStatement.close();
	//				retorno = 1;
	//			}
	//		}catch(SQLException e) {
	//			System.out.println("Incluir Endereco ERRO: "+e.getMessage());
	//		}
	//	}
	//	
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
}
