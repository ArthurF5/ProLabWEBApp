package com.moliveiralucas.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.moliveiralucas.connect.ConexaoMySQL;
import com.moliveiralucas.modelo.Usuario;

public class UsuarioPersist {
	ConexaoMySQL mConexaoMySQL;
	/**
	 * 
	 * @param usr
	 * @return
	 */
	public Integer returnID(Usuario usr) {
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		ResultSet mResultSet = null;
		Statement mStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		Integer id = 0;
		String sql = "SELECT usuarioID FROM users WHERE usuario like '"+usr.getUsuario()+"'";
		try {
			mStatement = mConnection.createStatement();
			mResultSet = mStatement.executeQuery(sql);
			if(mResultSet.next()) {
				id = mResultSet.getInt("usuarioID");
			}
		}catch (SQLException e) {
			System.out.println("Retornar ID Usuario ERRO: "+e.getMessage());
		}
		return id;
	}
	/**
	 * 
	 * @param usr
	 * @return
	 */
	public Usuario consulta(Usuario usr) {
		Usuario usuario = new Usuario();
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		ResultSet mResultSet = null;
		Statement mStatement= null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		String sql = "SELECT * FROM users WHERE usuario = '"+usr.getUsuario()+"'";
		try {
			mStatement = mConnection.createStatement();
			mResultSet = mStatement.executeQuery(sql);
			if(mResultSet.next()) {
				usuario = new Usuario();
				usuario.setId(mResultSet.getInt(1));
				usuario.setUsuario(mResultSet.getString("usuario"));
				usuario.setSenha(mResultSet.getString("senha"));
				usuario.setPermissao(mResultSet.getInt("permissao"));
			} else {
				usuario.setUsuario("");
			}
		}catch(SQLException e) {
			System.out.println("Consulta Usuario ERRO: "+e.getMessage());
		}
		return usuario;
	}
	/**
	 * 
	 * @param usr
	 * @return
	 */
	public Integer incluir(Usuario usr) {
		Integer retorno = 0;
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		ResultSet mResultSet = null;
		Statement mStatement = null;
		PreparedStatement mPreparedStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		String sql = "SELECT * FROM users WHERE usuario = '"+usr.getUsuario()+"'";
		try {
			mStatement = mConnection.createStatement();
			mResultSet = mStatement.executeQuery(sql);
			if(!mResultSet.next()) {
				sql = "INSERT INTO users (usuario, senha, permissao) VALUES (?, ?, ?)";
				mPreparedStatement = mConnection.prepareStatement(sql);
				mPreparedStatement.setString(1, usr.getUsuario());
				mPreparedStatement.setString(2, usr.getSenha());
				mPreparedStatement.setInt(3, usr.getPermissao());
				mPreparedStatement.executeUpdate();
				mPreparedStatement.close();
				retorno = 1;
			} else {
				retorno = 2;
			}

		}catch(SQLException e) {
			System.out.println("Incluir Usuario ERRO: "+e.getMessage());
		}
		return retorno;
	}
	/**
	 * 
	 * @param usr
	 * @return
	 */
	public boolean alterar(Usuario usr) {
		Boolean retorno = false;
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		PreparedStatement mPreparedStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		String sql = "";
		try {
			sql = "UPDATE usuario SET usuario = ?, senha = ?, permissao = ? WHERE usuarioID = ?";
			mPreparedStatement = mConnection.prepareStatement(sql);
			mPreparedStatement.setString(1, usr.getUsuario());
			mPreparedStatement.setString(2, usr.getSenha());
			mPreparedStatement.setInt(3, usr.getPermissao());
			mPreparedStatement.executeUpdate();
			retorno = true;
		}catch(SQLException e) {
			System.out.println("Alterar Usuario ERRO: "+e.getMessage());
		}
		return retorno;
	}
	/**
	 * 
	 * @param usr
	 * @return
	 */
	public Integer excluir(Usuario usr) {
		int retorno = 0;
		mConexaoMySQL = new ConexaoMySQL();
		Connection mConnection = null;
		PreparedStatement mPreparedStatement = null;
		mConnection = mConexaoMySQL.abreConexaoBD();
		String sql = "";
		try {
			sql = "DELETE FROM usuarios WHERE usuarioID = ?";
			mPreparedStatement = mConnection.prepareStatement(sql);
			mPreparedStatement.setInt(1, usr.getId());
			mPreparedStatement.execute();
			retorno = 1;
		}catch(SQLException e) {
			System.out.println("Excluir Usuario ERRO: "+ e.getMessage());
		}
		return retorno;
	}
}
