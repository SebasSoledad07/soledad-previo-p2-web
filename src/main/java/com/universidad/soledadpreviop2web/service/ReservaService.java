package com.universidad.soledadpreviop2web.service;

import com.universidad.soledadpreviop2web.dao.MesaDAO;
import com.universidad.soledadpreviop2web.dao.ReservaDAO;
import com.universidad.soledadpreviop2web.model.Mesa;
import com.universidad.soledadpreviop2web.model.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReservaService {
	private final ReservaDAO reservaDAO;
	private final MesaDAO mesaDAO;

	@Autowired
	public ReservaService(ReservaDAO reservaDAO, MesaDAO mesaDAO) {
		this.reservaDAO = reservaDAO;
		this.mesaDAO = mesaDAO;
	}

	public List<Reserva> findAll() {
		return reservaDAO.findAll();
	}

	public Reserva findById(int id) {
		return reservaDAO.findById(id);
	}

	public List<Reserva> findByMesa(int mesaId) {
		return reservaDAO.findByMesa(mesaId);
	}

	public Reserva save(Reserva reserva) {
		Map<String, String> errores = validar(reserva);
		if (!errores.isEmpty()) {
			return null;
		}
		return reservaDAO.save(reserva);
	}

	public Reserva update(int id, Reserva reserva) {
		if (reservaDAO.findById(id) == null) {
			return null;
		}

		Map<String, String> errores = validar(reserva);
		if (!errores.isEmpty()) {
			return null;
		}
		return reservaDAO.update(id, reserva);
	}

	public boolean delete(int id) {
		return reservaDAO.delete(id);
	}

	public Map<String, String> validar(Reserva reserva) {
		Map<String, String> errores = new LinkedHashMap<>();

		if (reserva == null) {
			errores.put("reserva", "La reserva es obligatoria.");
			return errores;
		}

		Mesa mesa = mesaDAO.findById(reserva.getMesaId());
		if (mesa == null) {
			errores.put("mesaId", "La mesa no existe.");
		} else if (!mesa.isActiva()) {
			errores.put("mesaId", "La mesa no esta activa.");
		}

		String clienteNombre = reserva.getClienteNombre();
		if (clienteNombre == null || clienteNombre.trim().length() < 3) {
			errores.put("clienteNombre", "El nombre del cliente debe tener al menos 3 caracteres.");
		}

		String horaInicio = reserva.getHoraInicio();
		String horaFin = reserva.getHoraFin();
		if (horaInicio == null || horaFin == null || horaInicio.isBlank() || horaFin.isBlank()) {
			errores.put("horario", "Hora inicio y hora fin son obligatorias.");
		} else if (horaFin.compareTo(horaInicio) <= 0) {
			errores.put("horario", "La hora fin debe ser posterior a la hora inicio.");
		}

		if (reserva.getNumPersonas() <= 0) {
			errores.put("numPersonas", "El numero de personas debe ser mayor a 0.");
		} else if (mesa != null && reserva.getNumPersonas() > mesa.getCapacidad()) {
			errores.put("numPersonas", "El numero de personas excede la capacidad de la mesa.");
		}

		return errores;
	}
}

