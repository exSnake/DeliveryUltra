package it.unisa.deliveryultra.controller;

import java.sql.SQLException;
import java.util.List;

import it.unisa.deliveryultra.model.Database;
import it.unisa.deliveryultra.model.OrdineTableModel;
import it.unisa.deliveryultra.model.Persona;
import it.unisa.deliveryultra.view.TrackingCovidView;

public class TrackingCovidController {
	private Database db;
	private TrackingCovidView view;
	OrdineTableModel model;
	
	
	public TrackingCovidController(Database db, TrackingCovidView view) {
		this.db = db;
		this.view = view;
	}
	
	public void initialize() {
		view.getBtnCerca().addActionListener(e -> onCercaClick());
		this.view.setVisible(true);
	}

	private void onCercaClick() {
		view.getLstPersone().removeAll();
		Persona[] persone = null;
		String nominativo = view.getTxtNominativo().getText();
		List<Persona> tmp = null;
		try {
			tmp = db.getPersoneByNominativoConsegna(nominativo);
			persone = new Persona[tmp.size()];
			tmp.toArray(persone);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		view.getLstPersone().setListData(persone);
	}
}
