package it.unisa.deliveryultra.controller;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import it.unisa.deliveryultra.model.Database;
import it.unisa.deliveryultra.model.Dipendente;
import it.unisa.deliveryultra.model.Ristorante;
import it.unisa.deliveryultra.view.AssumiDipendenteView;

public class AssumiDipendenteController {
	private Database db;
	private AssumiDipendenteView view;
	
	public AssumiDipendenteController(Database db, AssumiDipendenteView view) {
		this.db = db;
		this.view = view;
	}
	
	public void initialize() {
		view.getBtnInserisci().addActionListener(e -> onInserisciClick());
		try {
			for (Ristorante ristorante : db.getRistoranti()) {
				view.getCmbRistoranti().addItem(ristorante);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			return;
		}
		view.setVisible(true);
	}

	private void onInserisciClick() {
		if(!validateForm())
			return;
		Ristorante ristorante = (Ristorante) view.getCmbRistoranti().getSelectedItem();
		Dipendente dipendente = new Dipendente(view.getTxtCf().getText(), view.getTxtNome().getText(), view.getTxtCognome().getText(), view.getTxtTelefono().getText(), view.getTxtEmail().getText(), "Disponibile", (Integer) view.getSpinAnniEsperienza().getValue(), view.getTxtCurriculum().getText());
		String cadenza = view.getTxtCadenza().getText();
		String descrizione = view.getTxtDescrizione().getText();
		try {
			if(db.inserisciDeliveryInternoPerRistorante(ristorante, dipendente, cadenza, descrizione)) {
				JOptionPane.showMessageDialog(view, "Nuovo dipendente inserito con successo.");
				resetForm();
			} else {
				JOptionPane.showMessageDialog(view, "Errore impreviso nell'inserimento.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(view, "Errore nell'inserimento.");
		}
	}

	private void resetForm() {
		view.getLblErrors().setText("");
		view.getTxtCf().setText("");
		view.getTxtNome().setText("");
		view.getTxtCognome().setText("");
		view.getTxtTelefono().setText("");
		view.getTxtEmail().setText("");
		view.getTxtCurriculum().setText("");
		view.getCmbRistoranti().setSelectedIndex(-1);
		view.getTxtCadenza().setText("");
		view.getTxtDescrizione().setText("");
	}

	private boolean validateForm() {
		view.getLblErrors().setText("");
		StringBuilder errors = new StringBuilder();
		errors.append("<html>");
		if(view.getTxtCf().getText().isEmpty()) errors.append("Inserire un Codice Fiscale<br/>");
		if(view.getTxtNome().getText().isEmpty()) errors.append("Inserisci un Nome<br/>");
		if(view.getTxtCognome().getText().isEmpty()) errors.append("Inserisci un Cognome<br/>");
		if(view.getTxtTelefono().getText().isEmpty()) errors.append("Inserisci un Telefono<br/>");
		if(view.getTxtEmail().getText().isEmpty()) errors.append("Inserisci una Email<br/>");
		if(view.getTxtCurriculum().getText().isEmpty()) errors.append("Inserisci Curriculum<br/>");
		if(view.getTxtDescrizione().getText().isEmpty()) errors.append("Inserisci una descrzione<br/>");
		if(view.getTxtCadenza().getText().isEmpty()) errors.append("Inserisci la cadenza<br/>");
		if(view.getCmbRistoranti().getSelectedIndex() == -1) errors.append("Selezionare un ristorante");
		
		errors.append("</html>");
		if(errors.toString().length() > 14) {
			view.getLblErrors().setText(errors.toString());
			return false;
		}
		return true;
	}
	
}
