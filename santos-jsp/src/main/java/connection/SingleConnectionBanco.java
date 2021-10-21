package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {
	
	private static String banco = "jdbc:postgresql://ec2-52-200-68-5.compute-1.amazonaws.com:5432/d4ti7qiavs25ug?sslmode=require&autoReconnect=true";
	private static String user = "xpnblrbnwbnfzq";
	private static String senha = "3bcdc9d89cc5429d91fb83eaf4f32fdeacd4b2806eac1b724f13ba463c0f20e6";
	private static Connection connection = null;
	
	public static Connection getConnection() {
		return connection;
	}
	
	static {
		conectar();
	}
	
	public SingleConnectionBanco() { // quando tiver um instancia vai conectar
		conectar();
	}
	
	
	private static void conectar() {
		try {
			
			if (connection == null) {
				
				Class.forName("org.postgresql.Driver"); // carreaga o driver de concxao do banco
				connection = DriverManager.getConnection(banco, user, senha);
				connection.setAutoCommit(false); // para nao afetuar alterações no banco
				
			}
			
		} catch (Exception e) {
			e.printStackTrace(); // Mostrar qualquer erro na coxecçao
		}
	}

}
