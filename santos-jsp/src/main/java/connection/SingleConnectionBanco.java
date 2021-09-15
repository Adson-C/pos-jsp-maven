package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {
	
	private static String banco = "jdbc:postgresql://localhost:5433/ads-jsp:?autoReconnect=true";
	private static String user = "postgres";
	private static String senha = "admin";
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
