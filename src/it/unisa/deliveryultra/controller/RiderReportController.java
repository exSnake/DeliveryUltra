package it.unisa.deliveryultra.controller;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import it.unisa.deliveryultra.model.*;
import it.unisa.deliveryultra.view.RiderReportView;

public class RiderReportController {
	private Database db;
	private RiderReportView view;
	
	public RiderReportController(Database db, RiderReportView view) {
		this.db = db;
		this.view = view;
	}
	
	public void initialize() {
		resetForm();
		List<Rider> tmp;
		try {
			tmp = db.getRiders();
		} catch (SQLException e1) {
			e1.printStackTrace();
			return;
		}
		Rider[] riders;
		view.getLstRider().removeAll();
		riders = new Rider[tmp.size()];
		tmp.toArray(riders);
		view.getLstRider().setListData(riders);
		view.getLstRider().addListSelectionListener(e -> onChangeSelectedRider());
		
		this.view.setVisible(true);
	}

	private void onChangeSelectedRider() {
		resetForm();
		List<Valutazione> valutazioni;
		List<Valutazione> valutazioniBasse;
		List<Impiego> impieghi;
		view.getModelValutazione().removeAll();
		view.getModelImpiego().removeAll();
		view.getModelValutazioniBasse().removeAll();
		try {
			Rider rider = view.getLstRider().getSelectedValue();
			setLabelByRider(rider);
			valutazioni = db.getValutazioniByRider(rider);
			valutazioniBasse = db.getValutazioniBasseByRider(rider);
			impieghi = db.getImpieghiByRider(rider);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		view.getModelValutazione().add(valutazioni);
		view.getModelImpiego().add(impieghi);
		view.getModelValutazioniBasse().add(valutazioniBasse);
	}
	
	private void setLabelByRider(Rider rider) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		view.getLblNomeBody().setText(rider.getNome());
		view.getLblCognomeBody().setText(rider.getCognome());
		view.getLblCfBody().setText(rider.getCf());
		view.getLblTelefonoBody().setText(rider.getTelefono());
		view.getLblEmailBody().setText(rider.getEmail());
		view.getLblScoreMedioBody().setText(String.format("%.2f", rider.getScoreMedio()));
		view.getLblNumValutazioniBody().setText(String.format("%d",rider.getNumValutazioni()));
		view.getLblDataPrimaImpiegoBody().setText(rider.getDataPrimoImpiego().format(formatter));
		view.getLblNumImpieghiBody().setText(String.format("%d", rider.getNumImpiego()));
		view.getLblAutomunitoBody().setText(rider.isAutoMunito() ? "Si" : "No");
		view.getLblDisponibilitaBody().setText(rider.isDisponibilita() ? "Si" : "No");
		if(rider.isAutoMunito()) {
			view.getLblTargaHeader().setVisible(true);
			view.getLblTargaBody().setVisible(true);
			view.getLblTargaBody().setText(rider.getTarga());
			view.getLblTipoAutoHeader().setVisible(true);
			view.getLblTipoAutoBody().setVisible(true);
			view.getLblTipoAutoBody().setText(rider.getTipoVeicolo());
		}
	}
	
	private void resetForm() {
		view.getLblNomeBody().setText("");
		view.getLblCognomeBody().setText("");
		view.getLblCfBody().setText("");
		view.getLblTelefonoBody().setText("");
		view.getLblEmailBody().setText("");
		view.getLblScoreMedioBody().setText("");
		view.getLblNumValutazioniBody().setText("");
		view.getLblDataPrimaImpiegoBody().setText("");
		view.getLblNumImpieghiBody().setText("");
		view.getLblAutomunitoBody().setText("");
		view.getLblDisponibilitaBody().setText("");
		view.getLblTargaHeader().setVisible(false);
		view.getLblTargaBody().setVisible(false);
		view.getLblTipoAutoHeader().setVisible(false);
		view.getLblTipoAutoBody().setVisible(false);
	}

}
