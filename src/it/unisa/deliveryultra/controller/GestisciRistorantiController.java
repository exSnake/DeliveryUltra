package it.unisa.deliveryultra.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import it.unisa.deliveryultra.model.*;
import it.unisa.deliveryultra.view.DeliveryEsternoSelectDialog;
import it.unisa.deliveryultra.view.GestisciRistorantiView;

public class GestisciRistorantiController {
	private GestisciRistorantiView view;
	private Database db;
	private DeliveryEsternoSelectDialog deliveryDialog;
	
	public GestisciRistorantiController(Database db, GestisciRistorantiView view) {
		this.db = db;
		this.view = view;
	}
	
	public void initialize() {
		try {
			for (Ristorante ristorante : db.getRistoranti()) {
				view.getCmbRistoranti().addItem(ristorante);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			return;
		}
		setLabelToNull();
		view.getCmbRistoranti().setSelectedIndex(-1);
		view.getBtnAffida().setEnabled(false);
		view.getCmbRistoranti().addActionListener(e -> onRistoranteChange());
		view.getBtnAffida().addActionListener(e -> onAffidaClick());
		view.setVisible(true);
	}

	private void onAffidaClick() {
		Ristorante ristorante = (Ristorante) view.getCmbRistoranti().getSelectedItem();
		if(ristorante == null)
			return;
		List<Delivery> deliveriesEsterni;
		List<Societa> listaSocieta;
		try {
			deliveriesEsterni = db.getRistoranteServiziEsterni(ristorante);
			listaSocieta = db.getSocietaNonAssociateAlRistorante(ristorante);
			if(deliveriesEsterni.isEmpty() || listaSocieta.isEmpty()) {
				JOptionPane.showMessageDialog(view, "Impossibile associare una nuova societa, il ristorante non dispone di delivery esterni");
				return;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			return;
		}
		this.deliveryDialog = new DeliveryEsternoSelectDialog();
		for (Delivery delivery : deliveriesEsterni) {
			deliveryDialog.getCmbDeliveries().addItem(delivery);
		}
		for (Societa societa : listaSocieta) {
			deliveryDialog.getCmbSocieta().addItem(societa);
		}
		
		deliveryDialog.getOkButton().addActionListener(e -> {
			Delivery deliveryEsterno = (Delivery) this.deliveryDialog.getCmbDeliveries().getSelectedItem();
			Societa societa  = (Societa) this.deliveryDialog.getCmbSocieta().getSelectedItem();
			if(deliveryEsterno == null || societa == null)
				return;
			try {
				if(db.assegnaSocietaADeliveryEsterno(societa, deliveryEsterno)) {
					JOptionPane.showMessageDialog(view, "Societa associata con successo");
					deliveryDialog.dispose();
				} else {
					JOptionPane.showMessageDialog(view, "Errore imprevisto nell'associazione");
				}
						
			} catch (SQLException | IOException e1) {
				e1.printStackTrace();
				return;
			}
		});
		deliveryDialog.setVisible(true);	
	
	}

	private void onRistoranteChange() {
		view.getDeliveryModel().removeAll();
		if(view.getCmbRistoranti().getSelectedIndex() == -1) {
			view.getDeliveryModel().removeAll();
			return;
		}
		List<Delivery> deliveries = new ArrayList<>();
		Ristorante ristorante = (Ristorante) view.getCmbRistoranti().getSelectedItem();
		setLabelByRistorante(ristorante);
		view.getBtnAffida().setEnabled(true);
		try {
			deliveries = db.getDeliveriesByRistorante(ristorante);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		view.getDeliveryModel().add(deliveries);
	}
	
	private void setLabelByRistorante(Ristorante ristorante) {
		view.getLblDenominazioneBody().setText(ristorante.getDenominazione());
		view.getLblRagioneSocialeBody().setText(ristorante.getRagioneSociale());
		view.getLblPivaBody().setText(ristorante.getPiva());
		view.getLblTipologiaBody().setText(ristorante.getTipologia());
		view.getLblTelefonoBody().setText(ristorante.getTelefono());
		view.getLblEmailBody().setText(ristorante.getEmail());
		view.getLblIndirizzoBody().setText(ristorante.getIndirizzoCompleto());
		view.getLblOrdiniBody().setText(String.format("%d",ristorante.getOrdiniCoda()));
		view.getLblCodaMaxBody().setText(String.format("%d",ristorante.getCodaMax()));
	}
	
	private void setLabelToNull() {
		view.getLblDenominazioneBody().setText("Tutti");
		view.getLblRagioneSocialeBody().setText("N/D");
		view.getLblPivaBody().setText("N/D");
		view.getLblTipologiaBody().setText("N/D");
		view.getLblTelefonoBody().setText("N/D");
		view.getLblEmailBody().setText("N/D");
		view.getLblIndirizzoBody().setText("N/D");
		view.getLblOrdiniBody().setText("N/D");
		view.getLblCodaMaxBody().setText("N/D");
	}
}	
