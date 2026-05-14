package com.universidad.soledadpreviop2web.controller;

import com.universidad.soledadpreviop2web.model.Reserva;
import com.universidad.soledadpreviop2web.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservas")
public class ReservaController {
	@Autowired
	private ReservaService reservaService;

	private boolean verificarSesion(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return session != null && session.getAttribute("usuario") != null;
	}

	@GetMapping
	public ResponseEntity<?> obtenerTodas(HttpServletRequest request, HttpServletResponse response) {
		if (!verificarSesion(request)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("mensaje", "Sesion no iniciada"));
		}

		String ultimaMesaCookie = obtenerCookie(request, "ultimaMesa");
		if (ultimaMesaCookie != null) {
			response.addHeader("X-Ultima-Mesa", ultimaMesaCookie);
		}

		return ResponseEntity.ok(reservaService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerPorId(@PathVariable int id, HttpServletRequest request) {
		if (!verificarSesion(request)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("mensaje", "Sesion no iniciada"));
		}

		Reserva reserva = reservaService.findById(id);
		if (reserva == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(reserva);
	}

	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Reserva reserva, HttpServletRequest request, HttpServletResponse response) {
		if (!verificarSesion(request)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("mensaje", "Sesion no iniciada"));
		}

		Map<String, String> errores = reservaService.validar(reserva);
		if (!errores.isEmpty()) {
			return ResponseEntity.badRequest().body(errores);
		}

		Reserva nuevaReserva = reservaService.save(reserva);
		if (nuevaReserva == null) {
			return ResponseEntity.badRequest().body(Map.of("mensaje", "Error al crear reserva"));
		}

		Cookie cookie = new Cookie("ultimaMesa", String.valueOf(reserva.getMesaId()));
		cookie.setMaxAge(604800);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);

		return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReserva);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Reserva reserva, HttpServletRequest request) {
		if (!verificarSesion(request)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("mensaje", "Sesion no iniciada"));
		}

		Map<String, String> errores = reservaService.validar(reserva);
		if (!errores.isEmpty()) {
			return ResponseEntity.badRequest().body(errores);
		}

		Reserva reservaActualizada = reservaService.update(id, reserva);
		if (reservaActualizada == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(reservaActualizada);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable int id, HttpServletRequest request) {
		if (!verificarSesion(request)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("mensaje", "Sesion no iniciada"));
		}

		boolean eliminada = reservaService.delete(id);
		if (!eliminada) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}

	private String obtenerCookie(HttpServletRequest request, String nombreCookie) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(nombreCookie)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
}

