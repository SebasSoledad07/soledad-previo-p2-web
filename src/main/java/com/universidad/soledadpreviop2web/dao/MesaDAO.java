package com.universidad.soledadpreviop2web.dao;

import com.universidad.soledadpreviop2web.model.Mesa;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MesaDAO {
	private static int contadorId = 1;
	private final List<Mesa> mesas = new ArrayList<>();

	public List<Mesa> findAll() {
		return new ArrayList<>(mesas);
	}

	public Mesa findById(int id) {
		for (Mesa mesa : mesas) {
			if (mesa.getId() == id) {
				return mesa;
			}
		}
		return null;
	}

	public Mesa save(Mesa mesa) {
		mesa.setId(contadorId++);
		mesas.add(mesa);
		return mesa;
	}

	public Mesa update(int id, Mesa mesaActualizada) {
		Mesa existente = findById(id);
		if (existente == null) {
			return null;
		}

		existente.setNumero(mesaActualizada.getNumero());
		existente.setCapacidad(mesaActualizada.getCapacidad());
		existente.setZona(mesaActualizada.getZona());
		existente.setActiva(mesaActualizada.isActiva());
		return existente;
	}

	public boolean delete(int id) {
		Mesa existente = findById(id);
		if (existente == null) {
			return false;
		}
		return mesas.remove(existente);
	}
}

