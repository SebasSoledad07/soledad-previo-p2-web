package com.universidad.soledadpreviop2web.service;

import com.universidad.soledadpreviop2web.dao.MesaDAO;
import com.universidad.soledadpreviop2web.model.Mesa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}

