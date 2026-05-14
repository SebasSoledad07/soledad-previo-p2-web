package com.universidad.soledadpreviop2web.controller;

import com.universidad.soledadpreviop2web.model.Mesa;
import com.universidad.soledadpreviop2web.service.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mesas")
public class MesaController {
	@Autowired
	private MesaService mesaService;

	@GetMapping
	public ResponseEntity<List<Mesa>> obtenerTodas() {
		return ResponseEntity.ok(mesaService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Mesa> obtenerPorId(@PathVariable int id) {
		Mesa mesa = mesaService.findById(id);
		if (mesa == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(mesa);
	}

	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Mesa mesa) {
		Map<String, String> errores = mesaService.validar(mesa);
		if (!errores.isEmpty()) {
			return ResponseEntity.badRequest().body(errores);
		}

		Mesa nuevaMesa = mesaService.save(mesa);
		return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMesa);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Mesa mesa) {
		Map<String, String> errores = mesaService.validar(mesa);
		if (!errores.isEmpty()) {
			return ResponseEntity.badRequest().body(errores);
		}

		Mesa mesaActualizada = mesaService.update(id, mesa);
		if (mesaActualizada == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(mesaActualizada);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable int id) {
		boolean eliminada = mesaService.delete(id);
		if (!eliminada) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}
}

