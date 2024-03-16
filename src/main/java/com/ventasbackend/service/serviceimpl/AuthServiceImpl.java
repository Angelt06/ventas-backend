package com.ventasbackend.service.serviceimpl;

import com.ventasbackend.controller.AuthController;
import com.ventasbackend.dto.AuthRequestDTO;
import com.ventasbackend.dto.response.AuthResponseDTO;
import com.ventasbackend.entity.User;
import com.ventasbackend.exceptions.ApiRequestException;
import com.ventasbackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl {

    @Autowired
    UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;

    public AuthResponseDTO login(AuthRequestDTO authRequestDTO){
        logger.info("principio del meétodo" + authRequestDTO.getUsername() + authRequestDTO.getPassword());
        UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
                authRequestDTO.getUsername(), authRequestDTO.getPassword());
        logger.info("mitad del método");

        //Autentica el usuario, si el usuario y la contraseña no coinciden a las credenciales, lanzará un error.
        authenticationManager.authenticate(authToken);
        logger.info("mitad del método luego del authtoken");

        /*Si el authenticationManager funciona, entonces proseguimos a crear el token, por tanto podemos
         hacer el .get, y no lanzar una excepción ya que el usuario en este paso siempre va a existir*/
        User user = userRepository.findByUsername(authRequestDTO.getUsername()).get();
        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
        logger.info("final del metodo");

        return new AuthResponseDTO(jwt);
    }

    private Map<String, Object> generateExtraClaims(User user) {

        Map<String, Object> extraClaims = new HashMap<>();

        extraClaims.put("user",user.getUsername());
        extraClaims.put("role", user.getRole().getRoleType().name());
        extraClaims.put("authority", user.getAuthorities());

        return null;
    }
}
