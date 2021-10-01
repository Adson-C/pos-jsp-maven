package servlets;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

@WebServlet(urlPatterns =  {"/ServletUsuarioController"})
public class ServletUsuarioController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;
	
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

	public ServletUsuarioController() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 try {
		
				String acao = request.getParameter("acao");
				
				if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
					
					String idUser = request.getParameter("id");
					
					daoUsuarioRepository.deletarUser(idUser);
					
					List<ModelLogin> modelLogins = daoUsuarioRepository.consultarUsuarioList(super.getUserLogado(request));
					request.setAttribute("modelLogins", modelLogins);
					
					request.setAttribute("msg", "Excluido com sucesso!");
					
					request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
					
				}
				else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {
						
						String idUser = request.getParameter("id");
						
						daoUsuarioRepository.deletarUser(idUser);
						
						
						response.getWriter().write("Excluido com sucesso!");
					}
				
				else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
					
					String nomeBuscar = request.getParameter("nomeBuscar");
					
					
					List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultarUsuarioList(nomeBuscar, super.getUserLogado(request));
					
					ObjectMapper mapper = new ObjectMapper();
					String json = mapper.writeValueAsString(dadosJsonUser);
					
					response.getWriter().write(json);
					
				}
				else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
					
					String id = request.getParameter("id");
					
					ModelLogin modologin = daoUsuarioRepository.consultarUsuarioID(id, super.getUserLogado(request));
					
					List<ModelLogin> modelLogins = daoUsuarioRepository.consultarUsuarioList(super.getUserLogado(request));
					request.setAttribute("modelLogins", modelLogins);
					
					request.setAttribute("msg", "Usu�rio em edi��o");
					
					request.setAttribute("modelLogin", modologin);
					request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
					
				}
				
				else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {
					
					List<ModelLogin> modelLogins = daoUsuarioRepository.consultarUsuarioList(super.getUserLogado(request));
					
					request.setAttribute("msg", "Usu�rio carregados");
					
					request.setAttribute("modelLogins", modelLogins);
					request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
					
				}
					
				
				else {
					List<ModelLogin> modelLogins = daoUsuarioRepository.consultarUsuarioList(super.getUserLogado(request));
					request.setAttribute("modelLogins", modelLogins);
					
					request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				}
				
				
				
				
				 }
		 catch (Exception e) {
					 e.printStackTrace();
						RequestDispatcher redirecinar = request.getRequestDispatcher("erro.jsp");
						request.setAttribute("msg", e.getMessage());
						redirecinar.forward(request, response);
					}
		}
		

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			
			String msg = "Opera��o realizada com sucesso!";
		
		String id = request.getParameter("id");
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String perfil = request.getParameter("perfil");
		
		ModelLogin modelLogin = new ModelLogin();
		
		modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
		modelLogin.setNome(nome);
		modelLogin.setEmail(email);
		modelLogin.setLogin(login);
		modelLogin.setSenha(senha);
		modelLogin.setPerfil(perfil);
		
		if (daoUsuarioRepository.validarLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
			msg = "J� existe usu�rio com memso login, informe outro login;";
		}
		else {
			if (modelLogin.isNovo()) {
				msg = "Gravado com sucesso!";
		}else {
			msg = "Atualizado com Sucesso";
		}
		
		modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin, super.getUserLogado(request));
	}
		
		List<ModelLogin> modelLogins = daoUsuarioRepository.consultarUsuarioList(super.getUserLogado(request));
		request.setAttribute("modelLogins", modelLogins);
		
		
		request.setAttribute("msg", msg);
		
		request.setAttribute("modelLogin", modelLogin);
		request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
		
	}
		catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecinar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecinar.forward(request, response);
		}
		
	}
}
