package filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import connection.SingleConnectionBanco;
import dao.DaoVersionadorBanco;


@WebFilter(urlPatterns =  {"/principal/*"}) /*Intercepta todas as requisi��es do projeto ou mapeamento*/
public class FilterAutenticao implements Filter {
	
	private static Connection connection;

    public FilterAutenticao() {
    	
    }

    /*Encerra os processo quando o servidor � parado*/
    // mataria os processos de conex�o
	public void destroy() {
		try {
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/*tudo que fizer no sistema vai passar por aqui*/
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		try {
		
			HttpServletRequest req = (HttpServletRequest) request;
			
			HttpSession session = req.getSession();
			
			String usuarioLogado = (String) session.getAttribute("usuario");
			
			String urlParaAutenticar = req.getServletPath(); /*Url que est� sendo acessada*/
			
			/*Validar se est� logado se n�o redireciona pra tela login*/
			if (usuarioLogado == null  &&
					!urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) { // n�o est� logado
				
				RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
				request.setAttribute("msg", "Por favor realize o login!");
				redireciona.forward(request, response);
				return; /*Para a execu��o e redireciona para o login*/
				
		}
		else {
			chain.doFilter(request, response);
			}
			connection.commit(); // deu tudo certo , ent�o comita as altera��es
		
		}catch (Exception e) {
			e.printStackTrace();
			
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
			
			try {
				connection.rollback();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	/*Incia os processos servidor sobre projeto*/
	// inciar a conex�o com banco
	public void init(FilterConfig fConfig) throws ServletException {
		connection = SingleConnectionBanco.getConnection();
		
		DaoVersionadorBanco daoVersionadorBanco = new DaoVersionadorBanco();
		
		String caminhoPastaSql = fConfig.getServletContext().getRealPath("versionadorbancosql") +  File.separator;
		
		File[] filesSql = new File(caminhoPastaSql).listFiles();
		
		try {
			for (File file : filesSql) {
				
				boolean arquivoJaRodado = daoVersionadorBanco.arquivoSqlRodado(file.getName());
				
				if (!arquivoJaRodado) {
					
					FileInputStream entradaArquivo = new FileInputStream(file);
					
					Scanner lerArquivo = new Scanner(entradaArquivo, "UTF-8");
					
					StringBuilder sql = new StringBuilder();
					
					while (lerArquivo.hasNext()) {
						
						sql.append(lerArquivo.nextLine());
						sql.append("\n");
					}
					
					connection.prepareStatement(sql.toString()).execute();
					daoVersionadorBanco.gravaArquivoRodado(file.getName());
					
					connection.commit();
					lerArquivo.close();
				}
			}
			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		}
		

}
