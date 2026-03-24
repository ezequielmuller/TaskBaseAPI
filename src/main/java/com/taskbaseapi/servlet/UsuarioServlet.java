package com.taskbaseapi.servlet;

import com.taskbaseapi.modelo.Usuario;
import com.taskbaseapi.persistencia.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/usuario")
public class UsuarioServlet extends BaseServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      List<Usuario> usuariosList = UsuarioDAO.getInstance().listarUsuarios();

      retorno(response, HttpServletResponse.SC_OK, new JSONArray(usuariosList));
    } catch (Exception ex) {
      retornarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    JSONObject json = lerBody(request);
    try {
      Usuario novoUsuario = UsuarioDAO.getInstance().gravarUsuario(
              json.getString("nome"),
              json.getString("email"),
              json.getString("senha"),
              json.getBoolean("gerenciador")
      );

      retorno(response, HttpServletResponse.SC_CREATED, new JSONObject(novoUsuario));
    } catch (Exception ex) {
      retornarErro(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
    }
  }
}