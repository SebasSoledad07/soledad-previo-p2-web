package com.universidad.soledadpreviop2web.service;

import com.universidad.soledadpreviop2web.dao.MesaDAO;
import com.universidad.soledadpreviop2web.model.Mesa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MesaService {
	private final MesaDAO mesaDAO;

	@Autowired
	public MesaService(MesaDAO mesaDAO) {
		this.mesaDAO = mesaDAO;
	}

	public List<Mesa> findAll() {
		return mesaDAO.findAll();
	}

	public Mesa findById(int id) {
		return mesaDAO.findById(id);
	}

	public Mesa save(Mesa mesa) {
		return mesaDAO.save(mesa);
	}

	public Mesa update(int id, Mesa mesa) {
		return mesaDAO.update(id, mesa);
	}

	public boolean delete(int id) {
		return mesaDAO.delete(id);
	}

	public Map<String, String> validar(Mesa mesa) {
		Map<String, String> errores = new LinkedHashMap<>();

		if (mesa == null) {
			errores.put("mesa", "La mesa es obligatoria.");
			return errores;
		}

		if (mesa.getCapacidad() <= 0) {
			errores.put("capacidad", "La capacidad debe ser mayor a 0.");
		}

		String zona = mesa.getZona();
		if (zona == null || zona.trim().isEmpty()) {
			errores.put("zona", "La zona es obligatoria.");
		}

		return errores;
	}
}

