package it.unisa.deliveryultra.controller;

import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;

import it.unisa.deliveryultra.model.*;
import it.unisa.deliveryultra.view.*;

public class GestisciOrdiniController {
	private Database db;
	private GestisciOrdiniView view;
	private PersonaSelectDialog personaDialog;
	
	
	public GestisciOrdiniController(Database db, GestisciOrdiniView view) {
		this.db = db;
		this.view = view;
		this.personaDialog = new PersonaSelectDialog();
	}
	
	public void initialize() {
		try {
			for (Ristorante ristorante : db.getRistoranti()) {
				view.getCmbRistoranti().addItem(ristorante);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}
		view.getCmbRistoranti().setSelectedIndex(-1);
		view.getCmbRistoranti().addActionListener(e -> onSelectedRistoranteChange());
		view.getBtnAccetta().addActionListener(e -> onAccettaClick());
		view.getBtnAssegna().addActionListener(e -> onAssegnaClick());
		view.getBtnCerca().addActionListener(e -> onCercaClick());
		view.getBtnConsegna().addActionListener(e -> onConsegnaClick());
		view.getBtnElimina().addActionListener(e -> onEliminaClick());
		view.getLstOrdini().addListSelectionListener(e -> onSelectedOrdineChange(e));
		view.getBtnInCoda().addActionListener(e -> onInCodaClick());
		view.setVisible(true);
	}

	private void onSelectedRistoranteChange() {
		if(view.getCmbRistoranti().getSelectedIndex() == -1) {
			setLabelToNull();
		} else {
			setLabelByRistorante((Ristorante)view.getCmbRistoranti().getSelectedItem());
		}
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

	private void onAccettaClick() {
		Ordine ordine = view.getLstOrdini().getSelectedValue();
		if(ordine.getStato().equals("IN ATTESA")) {
			try {
				db.accettaOrdine(ordine);
				onCercaClick();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void onAssegnaClick() {
		this.personaDialog = new PersonaSelectDialog();
		Ordine ordine = view.getLstOrdini().getSelectedValue();
		if(ordine.getStato().equals("ORDINATO")) {
			List<Persona> persone;
			try {
				persone = db.getPersoneByRistoranteId(ordine.getRistoranteId());
			} catch (Exception e1) {
				e1.printStackTrace();
				return;
			}
			if(persone.isEmpty()) {
				JOptionPane.showMessageDialog(view, "Nessun rider disponibile per la consegna");
				return;
			}
			for (Persona persona : persone) {
				personaDialog.getCmbPersone().addItem(persona);
			}
			personaDialog.getOkButton().addActionListener(e -> {
				Persona persona = (Persona) this.personaDialog.getCmbPersone().getSelectedItem();
				this.personaDialog.dispose();
				if(persona == null)
					return;
				try {
					db.assegnaOrdine(ordine, persona, LocalDateTime.now().plusMinutes(30));
					onCercaClick();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
			personaDialog.setVisible(true);	
		}
	}	
	
	private void onCercaClick() {
		List<Ordine> tmp;
		Ordine[] ordini;
		view.getLstOrdini().removeAll();
		if(view.getCmbRistoranti().getSelectedIndex() == -1) {
			try {
				tmp = db.getOrdini();
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			ordini = new Ordine[tmp.size()];
			tmp.toArray(ordini);
			view.getLstOrdini().setListData(ordini);
		} else {
			Ristorante ristorante = (Ristorante) view.getCmbRistoranti().getSelectedItem();
			try {
				tmp = db.getOrdiniByRistorante(ristorante);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			ordini = new Ordine[tmp.size()];
			tmp.toArray(ordini);
			view.getLstOrdini().setListData(ordini);
		}
	}

	private void onConsegnaClick() {
		Ordine ordine = view.getLstOrdini().getSelectedValue();
		if(ordine.getStato().equals("ESPLETATO")) {
			try {
				String str = JOptionPane.showInputDialog("Inserisci nominativo consegna");
				if(str.length() < 3 && !str.contains(" ")) {
					JOptionPane.showMessageDialog(view, "Inserire nome cognome");
				} else {
					db.consegnaOrdine(ordine, str);
					onCercaClick();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void onEliminaClick() {
		Ordine ordine = view.getLstOrdini().getSelectedValue();
		if(!ordine.getStato().equals("CONSEGNATO")) {
			try {
				db.eliminaOrdine(ordine);
				onCercaClick();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void onInCodaClick() {
		List<Ordine> tmp;
		Ordine[] ordini;
		view.getLstOrdini().removeAll();
		if(view.getCmbRistoranti().getSelectedIndex() == -1) {
			try {
				tmp = db.getOrdiniInCoda();
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			ordini = new Ordine[tmp.size()];
			tmp.toArray(ordini);
			view.getLstOrdini().setListData(ordini);
		} else {
			Ristorante ristorante = (Ristorante) view.getCmbRistoranti().getSelectedItem();
			try {
				tmp = db.getOrdiniInCodaByRistorante(ristorante);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			ordini = new Ordine[tmp.size()];
			tmp.toArray(ordini);
			view.getLstOrdini().setListData(ordini);
		}
	}

	private void onSelectedOrdineChange(ListSelectionEvent e) {
		Ordine ordine = view.getLstOrdini().getSelectedValue();
		if(ordine == null || ordine.getStato().equals("CONSEGNATO")) {
			view.getBtnAccetta().setEnabled(false);
			view.getBtnAssegna().setEnabled(false);
			view.getBtnConsegna().setEnabled(false);
			view.getBtnElimina().setEnabled(false);
		} else if(ordine.getStato().equals("IN ATTESA")) {
			view.getBtnAccetta().setEnabled(true);
			view.getBtnAssegna().setEnabled(false);
			view.getBtnConsegna().setEnabled(false);
			view.getBtnElimina().setEnabled(true);
		} else if(ordine.getStato().equals("ORDINATO")) {
			view.getBtnAccetta().setEnabled(false);
			view.getBtnAssegna().setEnabled(true);
			view.getBtnConsegna().setEnabled(false);
			view.getBtnElimina().setEnabled(true);
		} else if(ordine.getStato().equals("ESPLETATO")) {
			view.getBtnAccetta().setEnabled(false);
			view.getBtnAssegna().setEnabled(false);
			view.getBtnConsegna().setEnabled(true);
			view.getBtnElimina().setEnabled(true);
		};
	}

	private void onSelectedPerson(ActionEvent e) {
		
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
}
