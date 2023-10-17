package br.com.lipcam.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.lipcam.todolist.model.UserModel;
import br.com.lipcam.todolist.repository.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    IUserRepository iUserRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var servletPath = request.getServletPath();
        if (servletPath.startsWith("/tasks")) {
            //Decode Authorization
            var authorization = request.getHeader("Authorization");
            var authDecoded = new String(Base64.getDecoder().decode(authorization.replace("Basic ", "")));

            //get User
            UserModel userModel = iUserRepository.findByUserName(authDecoded.split(":")[0]);

            if (userModel == null)
                response.sendError(401, "Usuário inexistente");
            else {
                var pws = BCrypt.verifyer().verify(authDecoded.split(":")[1].toCharArray(), userModel.getPassword());
                if (pws.verified) {
                    //Setando o IdUser vindo do Header para o request para ser pego na controller de Task
                    request.setAttribute("idUser", userModel.getId());
                    filterChain.doFilter(request, response);
                }
                else
                    response.sendError(401, "Senha incorreta");
            }
        } else
            filterChain.doFilter(request, response);
    }
}
