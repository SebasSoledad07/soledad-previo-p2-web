package com.universidad.soledadpreviop2web.dao;

import com.universidad.soledadpreviop2web.model.Reserva;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ReservaDAO {
	private static int contadorId = 1;
	private final List<Reserva> reservas = new ArrayList<>();

	public List<Reserva> findAll() {
		return new ArrayList<>(reservas);
	}

	public Reserva findById(int id) {
		for (Reserva reserva : reservas) {
			if (reserva.getId() == id) {
				return reserva;
			}
		}
		return null;
	}

	public Reserva save(Reserva reserva) {
		reserva.setId(contadorId++);
		reservas.add(reserva);
		return reserva;
	}

	public Reserva update(int id, Reserva reservaActualizada) {
		Reserva existente = findById(id);
		if (existente == null) {
			return null;
		}

		existente.setMesaId(reservaActualizada.getMesaId());
		existente.setClienteNombre(reservaActualizada.getClienteNombre());
		existente.setFecha(reservaActualizada.getFecha());
		existente.setHoraInicio(reservaActualizada.getHoraInicio());
		existente.setHoraFin(reservaActualizada.getHoraFin());
		existente.setNumPersonas(reservaActualizada.getNumPersonas());
		return existente;
	}

	public boolean delete(int id) {
		Reserva existente = findById(id);
		if (existente == null) {
			return false;
		}
		return reservas.remove(existente);
	}

	public List<Reserva> findByMesa(int mesaId) {
		return reservas.stream()
				.filter(reserva -> reserva.getMesaId() == mesaId)
				.collect(Collectors.toList());
	}
}

