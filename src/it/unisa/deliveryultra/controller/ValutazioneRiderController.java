package it.unisa.deliveryultra.controller;

import java.awt.event.ItemEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import it.unisa.deliveryultra.model.*;
import it.unisa.deliveryultra.view.*;

public class ValutazioneRiderController {
	private Database db;
	private ValutazioneRiderView view;
	
	public ValutazioneRiderController(Database db, ValutazioneRiderView view) {
		this.db = db;
		this.view = view;
	}
	
	public void initialize() {
		try {
			for (Cliente cliente : db.getClienti()) {
				view.getCmbClienti().addItem(cliente);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			view.dispose();
			return;
		}
		view.getCmbClienti().addItemListener(this::onClienteChange);
		view.getBtnValuta().addActionListener(e -> onValutaClick());
		resetForm();
		view.setVisible(true);
	}

	private void onValutaClick() {
		if(!validateForm())
			return;
		Cliente cliente = (Cliente) view.getCmbClienti().getSelectedItem();
		Rider rider = (Rider) view.getCmbRiders().getSelectedItem();
		int valutazione = (Integer) view.getSpinValutazione().getValue();
		try {
			if(db.valutaRider(cliente, rider, valutazione))
				JOptionPane.showMessageDialog(view, "Valutazione effettuata");
			else
				JOptionPane.showMessageDialog(view, "Errore inaspettato nella valutazione");
			resetForm();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void resetForm() {
		view.getCmbRiders().setSelectedIndex(-1);
		view.getCmbClienti().setSelectedIndex(-1);
		setValutazionePanelEnabled(false);
	}

	private boolean validateForm() {
		if(view.getCmbClienti().getSelectedIndex() == -1) {
			view.getLblErrors().setText("Selezionare un cliente");
			return false;
		}
		if(view.getCmbRiders().getSelectedIndex() == -1) {
			view.getLblErrors().setText("Selezionare un rider");
			return false;
		}
		int i = (Integer) view.getSpinValutazione().getValue();
		if(i > 5 || i < 1) {
			view.getLblErrors().setText("La valutazione deve essere tra 1 e 5");
			return false;
		}
		return true;
	}

	private void onClienteChange(ItemEvent e) {
		view.getCmbRiders().removeAllItems();
		Cliente cliente = (Cliente) e.getItem();
		if(cliente == null) {
			setValutazionePanelEnabled(false);
			return;
		}
		try {
			List<Rider> ridersValutabili = db.getRiderValutabiliByCliente(cliente);
			if(!ridersValutabili.isEmpty()) {
				setValutazionePanelEnabled(true);
				for (Rider rider : ridersValutabili) {
					view.getCmbRiders().addItem(rider);
				}
			} else {
				setValutazionePanelEnabled(false);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void setValutazionePanelEnabled(boolean value) {
		view.getCmbRiders().setEnabled(value);
		view.getBtnValuta().setEnabled(value);
		view.getSpinValutazione().setEnabled(value);
	}
	
}
