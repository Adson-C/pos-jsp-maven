package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {
	
	private Connection connection;
	
	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	public ModelLogin gravarUsuario(ModelLogin obj) throws Exception {
		
		if (obj.isNovo()) { // Grava um novo
			

		String sql = "INSERT INTO model_login(login, senha, nome, email) VALUES (?, ?, ?, ?);";
		
		PreparedStatement pdSql = connection.prepareStatement(sql);
		
		pdSql.setString(1, obj.getLogin());
		pdSql.setString(2, obj.getSenha());
		pdSql.setString(3, obj.getNome());
		pdSql.setString(4, obj.getEmail());
		
		pdSql.execute();
		
		connection.commit();
		
		} 
		else {
			String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=? WHERE id = "+obj.getId()+";";
			PreparedStatement pdSql = connection.prepareStatement(sql);
			
			pdSql.setString(1, obj.getLogin());
			pdSql.setString(2, obj.getSenha());
			pdSql.setString(3, obj.getNome());
			pdSql.setString(4, obj.getEmail());
			
			pdSql.executeUpdate();
			
			connection.commit();
			
		}
		
		return this.consultarUsuario(obj.getLogin());
		
	}
	
	
public List<ModelLogin> consultarUsuarioList() throws Exception {
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "SELECT * from model_login ";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { // pecorrer as linhas de resultado do SQL
			
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			
			retorno.add(modelLogin);
			
		}
		
		return retorno;
	}
	
	
	public List<ModelLogin> consultarUsuarioList(String nome) throws Exception {
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "SELECT * from model_login where upper(nome) like upper(?)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { // pecorrer as linhas de resultado do SQL
			
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			
			retorno.add(modelLogin);
			
		}
		
		return retorno;
	}
	
	
	public ModelLogin consultarUsuario(String login) throws Exception  {
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where upper(login) = upper('"+login+"')";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { // se tem resultado
			
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			
		}
		return modelLogin;
	}
	
	
public ModelLogin consultarUsuarioID(String id) throws Exception  {
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where id = ? ";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(id));
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { // se tem resultado
			
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			
		}
		return modelLogin;
	}
	
	
	public boolean validarLogin(String login) throws Exception {
		
		String sql = "select count(1) > 0 as existe from model_login where upper(login) = upper('"+login+"');";
		
			PreparedStatement statement = connection.prepareStatement(sql);
		
			ResultSet resultado = statement.executeQuery();
			
				resultado.next(); //para ele entrar nos resultados do sql
				return resultado.getBoolean("existe");
	}
	
	public void deletarUser( String idUser) throws Exception {
		String sql = "DELETE FROM model_login WHERE id = ?;";
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		
		preparedSql.setLong(1, Long.parseLong(idUser));
		
		preparedSql.executeUpdate();
		
		connection.commit();
		
	}

}