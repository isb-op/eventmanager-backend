package com.neki.eventmanager.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neki.eventmanager.dtos.LoginResponseDto;
import com.neki.eventmanager.dtos.UserAuthenticationDto;
import com.neki.eventmanager.dtos.UserRegisterDto;
import com.neki.eventmanager.enums.Role;
import com.neki.eventmanager.models.User;
import com.neki.eventmanager.repositories.UserRepository;
import com.neki.eventmanager.services.TokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/account")
public class UserAuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TokenService tokenService;

	@Operation(summary = "Autenticação de usuário", description = "Realiza o login de um usuário previamente cadastrado, permitindo acesso à plataforma.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Login realizado com sucesso!"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique se as credenciais fornecidas estão corretas."),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado. Certifique-se de que os dados de login estão corretos."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor. Tente novamente mais tarde.") })
	@PostMapping("/sign-in")
	public ResponseEntity<?> signIn(@RequestBody @Valid UserAuthenticationDto userAuthenticationDto) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(userAuthenticationDto.login(),
				userAuthenticationDto.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var user = (User) auth.getPrincipal();
		var token = tokenService.generateToken(user);
		return ResponseEntity.ok(new LoginResponseDto(token, user.getIdUser()));
	}

	@Operation(summary = "Cadastro de novo usuário", description = "Permite que novos usuários criem uma conta na plataforma, fornecendo os dados necessários para registro.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso!"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida. Verifique se os dados fornecidos estão corretos e completos."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor. Tente novamente mais tarde.") })
	@PostMapping("/sign-up")
	public ResponseEntity<?> signUp(@RequestBody @Valid UserRegisterDto userRegisterDto) {
		if (this.userRepository.findByLogin(userRegisterDto.login()) != null)
			return ResponseEntity.badRequest().build();
		String encryptedPassword = new BCryptPasswordEncoder().encode(userRegisterDto.password());
		if (!userRegisterDto.password().equals(userRegisterDto.passwordConfirmation())) {
			throw new Error("Senhas não são iguais");
		}
		User newUser = new User(userRegisterDto.name(), userRegisterDto.login(), encryptedPassword, Role.ADMIN);
		newUser.setRegisteredAt(LocalDateTime.now());
		this.userRepository.save(newUser);
		return ResponseEntity.ok().build();
	}
}
