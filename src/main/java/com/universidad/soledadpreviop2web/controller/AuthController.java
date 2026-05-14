package com.universidad.soledadpreviop2web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private static final Map<String, String> USUARIOS = new HashMap<>();

	static {
		USUARIOS.put("mesero1", "mes123");
		USUARIOS.put("mesero2", "mes456");
		USUARIOS.put("supervisor", "sup789");
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestParam String usuario,
			@RequestParam String contrasena,
			HttpServletRequest request) {

		Map<String, Object> response = new HashMap<>();

		if (!USUARIOS.containsKey(usuario) || !USUARIOS.get(usuario).equals(contrasena)) {
			response.put("mensaje", "Usuario o contrasena incorrectos");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}

		HttpSession session = request.getSession(true);
		session.setAttribute("usuario", usuario);
		session.setMaxInactiveInterval(1800);

		response.put("mensaje", "Login exitoso");
		response.put("usuario", usuario);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/logout")
	public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		Map<String, String> response = new HashMap<>();
		response.put("mensaje", "Logout exitoso");
		return ResponseEntity.ok(response);
	}
}

