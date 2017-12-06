package com.moliveiralucas.persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.moliveiralucas.connect.ConexaoMySQL;
import com.moliveiralucas.modelo.Cidade;

public class CidadePersist {
	ConexaoMySQL mConexaoMySQL;
	public Integer retornaID(Cidade cidade) {
		Integer retorno = 0;
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		ResultSet mResultSet = null;
		Statement mStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		String sql = "SELECT cidadeID FROM cidade WHERE cidade LIKE '"+cidade.getCidade()+"'";
		try {
			mStatement = mConnection.createStatement();
			mResultSet = mStatement.executeQuery(sql);
			if(mResultSet.next()) {
				retorno = mResultSet.getInt("ufID");
			}
		}catch(SQLException e) {
			System.out.println("RetornarID UF ERROR: "+e.getMessage());
		}
		return retorno;
	}
	
	public Cidade consulta(String parametroBusca) {
		Cidade cidade = new Cidade();
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		ResultSet mResultSet = null;
		Statement mStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		String sql = "SELECT * FROM cidade WHERE cidade LIKE '"+parametroBusca+"'";
		try {
			mStatement = mConnection.createStatement();
			mResultSet = mStatement.executeQuery(sql);
			if(mResultSet.next()) {
				cidade.setCidade(mResultSet.getString("cidade"));
				cidade.setCidadeID(mResultSet.getInt("cidadeID"));
			}else {
				cidade = null;
			}
		}catch(SQLException e) {
			System.out.println("Consulta UF ERROR: "+e.getMessage());
			cidade = null;
		}
		return cidade;
	}
	public ArrayList<Cidade> buscarPorEstado(Integer estadoID){
		ArrayList<Cidade> mCidadeLista = new ArrayList<Cidade>();
		Cidade mCidade;
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		ResultSet mResultSet = null;
		Statement mStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		String sql = "SELECT * FROM cidade WHERE ufID = "+estadoID;
		try {
			mStatement = mConnection.createStatement();
			mResultSet = mStatement.executeQuery(sql);
			while(mResultSet.next()) {
				mCidade = new Cidade();
				mCidade.setCidade(mResultSet.getString("cidade"));
				mCidade.setCidadeID(mResultSet.getInt("cidadeID"));
				mCidadeLista.add(mCidade);
			}
		}catch(SQLException e) {
			System.out.println("RetornarID UF ERROR: "+e.getMessage());
			mCidadeLista = null;
		}
		return mCidadeLista;
	}
	public ArrayList<Cidade> listarTodos(String parametroBusca){
		ArrayList<Cidade> mCidadeLista = new ArrayList<Cidade>();
		Cidade mCidade;
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		ResultSet mResultSet = null;
		Statement mStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		String sql;
		if(parametroBusca.equals("null")) {
			sql = "SELECT * FROM cidade ORDER BY cidade";
		}else {
			sql = "SELECT * FROM cidade WHERE cidade LIKE '"+parametroBusca+"%' ORDER BY cidade";
		}
		try {
			mStatement = mConnection.createStatement();
			mResultSet = mStatement.executeQuery(sql);
			while(mResultSet.next()) {
				mCidade = new Cidade();
				mCidade.setCidade(mResultSet.getString("cidade"));
				mCidade.setCidadeID(mResultSet.getInt("cidadeID"));
				mCidadeLista.add(mCidade);
			}
		}catch(SQLException e) {
			System.out.println("RetornarID UF ERROR: "+e.getMessage());
			mCidadeLista = null;
		}
		return mCidadeLista;
	}
}
