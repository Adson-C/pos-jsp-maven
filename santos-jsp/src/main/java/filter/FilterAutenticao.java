package filter;

import java.io.IOException;

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

    public FilterAutenticao() {
    	
    }

    /*Encerra os processo quando o servidor � parado*/
    // mataria os processos de conex�o
	public void destroy() {
		
	}

	/*tudo que fizer no sistema vai passar por aqui*/
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
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
		
	}

	/*Incia os processos servidor sobre projeto*/
	// inciar a conex�o com banco
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
