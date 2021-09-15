package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import connection.SingleConnectionBanco;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
		
		
	}

}
