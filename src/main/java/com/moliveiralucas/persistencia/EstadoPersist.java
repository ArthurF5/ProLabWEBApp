package com.moliveiralucas.persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.moliveiralucas.connect.ConexaoMySQL;
import com.moliveiralucas.modelo.Estado;

public class EstadoPersist {
	ConexaoMySQL mConexaoMySQL;

	public Integer retornaID(Estado uf) {
		Integer retorno = 0;
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		ResultSet mResultSet = null;
		Statement mStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		String sql = "SELECT ufID FROM uf WHERE uf LIKE '"+uf.getUf()+"'";
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

	public Estado consulta(String parametroBusca) {
		Estado uf = new Estado();
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		ResultSet mResultSet = null;
		Statement mStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		String sql = "SELECT * FROM uf WHERE uf LIKE '"+parametroBusca+"'";
		try {
			mStatement = mConnection.createStatement();
			mResultSet = mStatement.executeQuery(sql);
			if(mResultSet.next()) {
				uf.setUf(mResultSet.getString("uf"));
				uf.setUfID(mResultSet.getInt("ufID"));
			}else {
				uf = null;
			}
		}catch(SQLException e) {
			System.out.println("Consulta UF ERROR: "+e.getMessage());
		}
		return uf;
	}

	public ArrayList<Estado> listarTodos(String parametroBusca){
		ArrayList<Estado> mUF = new ArrayList<Estado>();
		Estado uf;
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		ResultSet mResultSet = null;
		Statement mStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		String sql;
		if(parametroBusca.equals("null")) {
			sql = "SELECT * FROM uf ORDER BY uf";
		}else {
			sql = "SELECT * FROM uf WHERE uf LIKE '"+parametroBusca+"%' ORDER BY uf";
		}
		try {
			mStatement = mConnection.createStatement();
			mResultSet = mStatement.executeQuery(sql);
			while(mResultSet.next()) {
				uf = new Estado();
				uf.setUf(mResultSet.getString("uf"));
				uf.setUfID(mResultSet.getInt("ufID"));
				mUF.add(uf);
			}
		}catch(SQLException e) {
			System.out.println("Listar todos UF ERROR: "+e.getMessage());
			mUF = null;
		}
		return mUF;
	}
}
