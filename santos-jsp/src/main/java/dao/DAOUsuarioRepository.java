package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import beandto.BeanDTOGraficoSalarioUser;
import connection.SingleConnectionBanco;
import model.ModelLogin;
import model.ModelTelefone;

public class DAOUsuarioRepository {
	
	private Connection connection;
	
	
	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	
	public BeanDTOGraficoSalarioUser montarGraficoMediaSalario(Long userLogado, String dataInicial, String dataFinal) throws Exception {
		
String sql = "SELECT avg(rendamensal) as media_salarial, perfil from model_login where usuario_id = ? and datanascimento >= ? and datanascimento <= ? group by perfil";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setLong(1, userLogado);
		preparedStatement.setDate(2, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataInicial))));
		preparedStatement.setDate(3, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataFinal))));
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		List<String> perfils = new ArrayList<String>();
		List<Double> salarios = new ArrayList<Double>();
		
		BeanDTOGraficoSalarioUser beanDTOGraficoSalarioUser = new BeanDTOGraficoSalarioUser();
		
		while (resultSet.next()) {
			Double media_salarial = resultSet.getDouble("media_salarial");
			String perfil = resultSet.getString("perfil");
			
			perfils.add(perfil);
			salarios.add(media_salarial);
		}
		
		beanDTOGraficoSalarioUser.setPerfils(perfils);
		beanDTOGraficoSalarioUser.setSalarios(salarios);
		
		return beanDTOGraficoSalarioUser;
	}
	
	
	public BeanDTOGraficoSalarioUser montarGraficoMediaSalario(Long userLogado) throws Exception {
		
		String sql = "SELECT avg(rendamensal) as media_salarial, perfil from model_login where usuario_id = ? group by perfil";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setLong(1, userLogado);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		List<String> perfils = new ArrayList<String>();
		List<Double> salarios = new ArrayList<Double>();
		
		BeanDTOGraficoSalarioUser beanDTOGraficoSalarioUser = new BeanDTOGraficoSalarioUser();
		
		while (resultSet.next()) {
			Double media_salarial = resultSet.getDouble("media_salarial");
			String perfil = resultSet.getString("perfil");
			
			perfils.add(perfil);
			salarios.add(media_salarial);
		}
		
		beanDTOGraficoSalarioUser.setPerfils(perfils);
		beanDTOGraficoSalarioUser.setSalarios(salarios);
		
		return beanDTOGraficoSalarioUser;
	}
	
	public ModelLogin gravarUsuario(ModelLogin obj, Long userLogado) throws Exception {
		
		if (obj.isNovo()) { // Grava um novo
			

		String sql = "INSERT INTO model_login(login, senha, nome, email, usuario_id, perfil, sexo, cep, logradouro, bairro, cidade, uf, numero, datanascimento, rendamensal) VALUES (?,?,?,?,?,?,?,?,?,?, ?, ?, ?, ?, ?);";
		
		PreparedStatement pdSql = connection.prepareStatement(sql);
		
		pdSql.setString(1, obj.getLogin());
		pdSql.setString(2, obj.getSenha());
		pdSql.setString(3, obj.getNome());
		pdSql.setString(4, obj.getEmail());
		pdSql.setLong(5, userLogado);
		pdSql.setString(6, obj.getPerfil());
		pdSql.setString(7, obj.getSexo());
		pdSql.setString(8, obj.getCep());
		pdSql.setString(9, obj.getLogradouro());
		pdSql.setString(10, obj.getBairro());
		pdSql.setString(11, obj.getCidade());
		pdSql.setString(12, obj.getUf());
		pdSql.setString(13, obj.getNumero());
		pdSql.setDate(14, obj.getDataNascimento());
		pdSql.setDouble(15, obj.getRendamensal());
		
		pdSql.execute();
		
		connection.commit();
		
		if (obj.getFotoUser() != null && !obj.getFotoUser().isEmpty()) {
				sql = "UPDATE model_login SET fotouser=?, extensaofotouser=? where login=? ";
				
				pdSql = connection.prepareStatement(sql);
				
				pdSql.setString(1, obj.getFotoUser());
				pdSql.setString(2, obj.getExtensaoFotoUser());
				pdSql.setString(3, obj.getLogin());
				
				
				pdSql.execute();
				
				connection.commit();
			}	
		} 
		else {
			String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=?, perfil=?, sexo=?, cep=?, logradouro=?, bairro=?, cidade=?, uf=?, numero=?, datanascimento=?, rendamensal=? WHERE id = "+obj.getId()+";";
			PreparedStatement pdSql = connection.prepareStatement(sql);
			
			pdSql.setString(1, obj.getLogin());
			pdSql.setString(2, obj.getSenha());
			pdSql.setString(3, obj.getNome());
			pdSql.setString(4, obj.getEmail());
			pdSql.setString(5, obj.getPerfil());
			pdSql.setString(6, obj.getSexo());
			pdSql.setString(7, obj.getCep());
			pdSql.setString(8, obj.getLogradouro());
			pdSql.setString(9, obj.getBairro());
			pdSql.setString(10, obj.getCidade());
			pdSql.setString(11, obj.getUf());
			pdSql.setString(12, obj.getNumero());
			pdSql.setDate(13, obj.getDataNascimento());
			pdSql.setDouble(14, obj.getRendamensal());
			
			pdSql.executeUpdate();
			
			connection.commit();
			
			if (obj.getFotoUser() != null && !obj.getFotoUser().isEmpty()) {
				sql = "UPDATE model_login SET fotouser=?, extensaofotouser=? where id=? ";
				
				pdSql = connection.prepareStatement(sql);
				
				pdSql.setString(1, obj.getFotoUser());
				pdSql.setString(2, obj.getExtensaoFotoUser());
				pdSql.setLong(3, obj.getId());
				
				
				pdSql.execute();
				
				connection.commit();
			}	
			
		}
		
		return this.consultarUsuario(obj.getLogin(), userLogado);
		
	}
	
public List<ModelLogin> consultarUsuarioListPaginada(Long userLogado, Integer offset) throws Exception {
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "SELECT * from model_login where useradmin is false and usuario_id = " + userLogado + " order by nome offset "+offset+" limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { // pecorrer as linhas de resultado do SQL
			
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
			retorno.add(modelLogin);
			
		}
		
		return retorno;
	}


public int totalPagina(Long userLogado) throws Exception {
	
	String sql = "select count(1) as total from model_login where usuario_id = " + userLogado;
	
	PreparedStatement statement = connection.prepareStatement(sql);
	
	ResultSet resultado = statement.executeQuery();
	resultado.next();
	
	Double cadastros = resultado.getDouble("total");
	
	
	
	Double porpagina = 5.0;
	Double pagina = cadastros / porpagina;
	Double resto = pagina % 2;
	
	if (resto > 0) {
		pagina ++;
	}
	
	return pagina.intValue();
}

	
public List<ModelLogin> consultarUsuarioListRelatorio(Long userLogado) throws Exception {
	
	List<ModelLogin> retorno = new ArrayList<ModelLogin>();
	
	String sql = "SELECT * from model_login where useradmin is false and usuario_id = " + userLogado;
	PreparedStatement statement = connection.prepareStatement(sql);
	
	ResultSet resultado = statement.executeQuery();
	
	while (resultado.next()) { // pecorrer as linhas de resultado do SQL
		
		ModelLogin modelLogin = new ModelLogin();
		
		modelLogin.setEmail(resultado.getString("email"));
		modelLogin.setId(resultado.getLong("id"));
		modelLogin.setLogin(resultado.getString("login"));
		modelLogin.setNome(resultado.getString("nome"));
		modelLogin.setPerfil(resultado.getString("perfil"));
		modelLogin.setSexo(resultado.getString("sexo"));
		modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
		
		modelLogin.setTelefones(this.listFone(modelLogin.getId()));
		
		retorno.add(modelLogin);
		
	}
	
	return retorno;
}

public List<ModelLogin> consultarUsuarioListRelatorio(Long userLogado, String dataInicial, String dataFinal) throws Exception {
	
	List<ModelLogin> retorno = new ArrayList<ModelLogin>();
	
	String sql = "SELECT * from model_login where useradmin is false and usuario_id = " + userLogado + " and datanascimento >= ? and datanascimento <= ?";
	PreparedStatement statement = connection.prepareStatement(sql);
	
	statement.setDate(1, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataInicial))));
	statement.setDate(2, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataFinal))));
	
	ResultSet resultado = statement.executeQuery();
	
	while (resultado.next()) { // pecorrer as linhas de resultado do SQL
		
		ModelLogin modelLogin = new ModelLogin();
		
		modelLogin.setEmail(resultado.getString("email"));
		modelLogin.setId(resultado.getLong("id"));
		modelLogin.setLogin(resultado.getString("login"));
		modelLogin.setNome(resultado.getString("nome"));
		modelLogin.setPerfil(resultado.getString("perfil"));
		modelLogin.setSexo(resultado.getString("sexo"));
		modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
		
		modelLogin.setTelefones(this.listFone(modelLogin.getId()));
		
		retorno.add(modelLogin);
		
	}
	
	return retorno;
}
	
public List<ModelLogin> consultarUsuarioList(Long userLogado) throws Exception {
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "SELECT * from model_login where useradmin is false and usuario_id = " + userLogado + " limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { // pecorrer as linhas de resultado do SQL
			
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
			retorno.add(modelLogin);
			
		}
		
		return retorno;
	}

public int  consultarUsuarioListTotalPaginacao(String nome, Long userLogado) throws Exception {
	
	
	String sql = "SELECT count(1) as total from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ? ";
	PreparedStatement statement = connection.prepareStatement(sql);
	statement.setString(1, "%" + nome + "%");
	statement.setLong(2, userLogado);
	
	ResultSet resultado = statement.executeQuery();
	
	resultado.next();
	
	Double cadastros = resultado.getDouble("total");
	
	
	
	Double porpagina = 5.0;
	Double pagina = cadastros / porpagina;
	Double resto = pagina % 2;
	
	if (resto > 0) {
		pagina ++;
	}
	
	return pagina.intValue();
	

}

public List<ModelLogin> consultarUsuarioListOffSet(String nome, Long userLogado, int offset) throws Exception {
	
	List<ModelLogin> retorno = new ArrayList<ModelLogin>();
	
	String sql = "SELECT * from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ? offset "+ offset+" limit 5";
	
	PreparedStatement statement = connection.prepareStatement(sql);
	statement.setString(1, "%" + nome + "%");
	statement.setLong(2, userLogado);
	
	ResultSet resultado = statement.executeQuery();
	
	while (resultado.next()) { // pecorrer as linhas de resultado do SQL
		
		ModelLogin modelLogin = new ModelLogin();
		
		modelLogin.setEmail(resultado.getString("email"));
		modelLogin.setId(resultado.getLong("id"));
		modelLogin.setLogin(resultado.getString("login"));
		modelLogin.setNome(resultado.getString("nome"));
		modelLogin.setPerfil(resultado.getString("perfil"));
		modelLogin.setSexo(resultado.getString("sexo"));
		
		retorno.add(modelLogin);
		
	}
	
	return retorno;
}
		
	
	public List<ModelLogin> consultarUsuarioList(String nome, Long userLogado) throws Exception {
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "SELECT * from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ? limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { // pecorrer as linhas de resultado do SQL
			
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
			retorno.add(modelLogin);
			
		}
		
		return retorno;
	}
	
public ModelLogin consultarUsuarioLogado(String login) throws Exception  {
		
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
			modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotoUser(resultado.getString("fotouser"));
			
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setCidade(resultado.getString("cidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendamensal(resultado.getDouble("rendamensal"));
			
		}
		return modelLogin;
	}
	
	
public ModelLogin consultarUsuario(String login) throws Exception  {
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where upper(login) = upper('"+login+"') and useradmin is false ";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { // se tem resultado
			
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotoUser(resultado.getString("fotouser"));
			
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setCidade(resultado.getString("cidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendamensal(resultado.getDouble("rendamensal"));
			
		}
		return modelLogin;
	}
	
	
	public ModelLogin consultarUsuario(String login, Long userLogado) throws Exception  {
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where upper(login) = upper('"+login+"') and useradmin is false and usuario_id = " + userLogado;
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { // se tem resultado
			
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotoUser(resultado.getString("fotouser"));
			
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setCidade(resultado.getString("cidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendamensal(resultado.getDouble("rendamensal"));
			
		}
		return modelLogin;
	}
	

	public ModelLogin consultaUsuarioID(Long id) throws Exception  {
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where id = ? and useradmin is false";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, id);
		
		ResultSet resutlado =  statement.executeQuery();
		
		while (resutlado.next()) /*Se tem resultado*/ {
			
			modelLogin.setId(resutlado.getLong("id"));
			modelLogin.setEmail(resutlado.getString("email"));
			modelLogin.setLogin(resutlado.getString("login"));
			modelLogin.setSenha(resutlado.getString("senha"));
			modelLogin.setNome(resutlado.getString("nome"));
			modelLogin.setPerfil(resutlado.getString("perfil"));
			modelLogin.setSexo(resutlado.getString("sexo"));
			modelLogin.setFotoUser(resutlado.getString("fotouser"));
			modelLogin.setExtensaoFotoUser(resutlado.getString("extensaofotouser"));
			modelLogin.setCep(resutlado.getString("cep"));
			modelLogin.setLogradouro(resutlado.getString("logradouro"));
			modelLogin.setBairro(resutlado.getString("bairro"));
			modelLogin.setCidade(resutlado.getString("cidade"));
			modelLogin.setUf(resutlado.getString("uf"));
			modelLogin.setNumero(resutlado.getString("numero"));
			modelLogin.setDataNascimento(resutlado.getDate("datanascimento"));
			modelLogin.setRendamensal(resutlado.getDouble("rendamensal"));
		}
		
		
		return modelLogin;
		
	}
	
	
public ModelLogin consultarUsuarioID(String id, Long userLogado) throws Exception  {
		
		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where id = ? and useradmin is false and usuario_id = ?";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(id));
		statement.setLong(2, userLogado);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) { // se tem resultado
			
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotoUser(resultado.getString("fotouser"));
			modelLogin.setExtensaoFotoUser(resultado.getString("extensaofotouser"));
			
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setCidade(resultado.getString("cidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendamensal(resultado.getDouble("rendamensal"));
			
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
		String sql = "DELETE FROM model_login WHERE id = ? and useradmin is false;";
		PreparedStatement preparedSql = connection.prepareStatement(sql);
		
		preparedSql.setLong(1, Long.parseLong(idUser));
		
		preparedSql.executeUpdate();
		
		connection.commit();
		
	}
	
public List<ModelTelefone> listFone(Long idUserPai) throws Exception {
		
		List<ModelTelefone> retorno = new ArrayList<ModelTelefone>();
		
		String sql = "select * from telefone where usuario_pai_id = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setLong(1, idUserPai);
		
		ResultSet rs = preparedStatement.executeQuery();
		
		while (rs.next()) {
			
			ModelTelefone modelTelefone = new ModelTelefone();
			
			modelTelefone.setId(rs.getLong("id"));
			modelTelefone.setNumero(rs.getString("numero"));
			modelTelefone.setUsuario_cad_id(this.consultaUsuarioID(rs.getLong("usuario_cad_id")));
			modelTelefone.setUsuario_pai_id(this.consultaUsuarioID(rs.getLong("usuario_pai_id")));
			
			retorno.add(modelTelefone);
		}
		
		return retorno;
	}

}