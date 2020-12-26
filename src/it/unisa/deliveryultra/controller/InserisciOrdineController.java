package it.unisa.deliveryultra.controller;

import java.awt.Color;
import java.awt.event.ItemEvent;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import it.unisa.deliveryultra.model.*;
import it.unisa.deliveryultra.view.*;

public class InserisciOrdineController {
	private Database db;
	private InserisciOrdineView view;
	
	
	public InserisciOrdineController(Database db, InserisciOrdineView view) {
		this.db = db;
		this.view = view;
	}
	
	public void initialize() {
		try {
			for (Ristorante rist : db.getRistoranti())	 {
				this.view.getCmbRistorante().addItem(rist);
			}
			for (Cliente cliente : db.getClienti()) {
				this.view.getCmbCliente().addItem(cliente);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.view.getCmbCliente().setSelectedItem(null);
		this.view.getCmbRistorante().setSelectedItem(null);
		this.view.getCmbCliente().addItemListener(e -> onChangeClienteItem(e));
		this.view.getBtnInserisci().addActionListener(e -> onInserisciClick());
		this.view.getTxtDescrizione().getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) { onDescrizioneChange(); }
			@Override
			public void insertUpdate(DocumentEvent e) { onDescrizioneChange(); }			
			@Override
			public void changedUpdate(DocumentEvent e) { onDescrizioneChange(); }
		});
		this.view.setVisible(true);
	}

	protected void onDescrizioneChange() {
		int size = this.view.getTxtDescrizione().getText().length();
		this.view.getLblRemainingChar().setText(size + "/255");
		if(size > 255) {
			this.view.getLblRemainingChar().setForeground(new Color(153, 0, 0));
		} else {
			this.view.getLblRemainingChar().setForeground(new Color(0, 0, 0));
		}
	}

	private void onInserisciClick() {
		if(!validateForm())
			return;
		Ordine ordine = new Ordine();
		ordine.setDestinazione(this.view.getCmbIndirizzo().getSelectedItem().toString());
		ordine.setDescrizione(this.view.getTxtDescrizione().getText());
		ordine.setTipo(this.view.getTxtTipo().getText());
		try {
			this.db.inserisciOrdine(ordine, (Ristorante)this.view.getCmbRistorante().getSelectedItem(), (Cliente)this.view.getCmbCliente().getSelectedItem());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(view, "Errore nell'inserimento dell'ordine");
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(view, "Ordine inserito con successo");
		resetForm();
	}

	private void resetForm() {
		this.view.getCmbRistorante().setSelectedIndex(-1);
		this.view.getCmbCliente().setSelectedIndex(-1);
		this.view.getCmbIndirizzo().setSelectedIndex(-1);
		this.view.getTxtTipo().setText("");
		this.view.getTxtDescrizione().setText("");
		this.view.getLblErrors().setText("");
		this.view.getTxtDescrizione().setText("");
	}

	private boolean validateForm() {
		if(this.view.getCmbRistorante().getSelectedIndex() == -1) {
			this.view.getLblErrors().setText("Selezionare un ristorante");
			return false;
		}
		if(this.view.getCmbCliente().getSelectedIndex() == -1) {
			this.view.getLblErrors().setText("Selezionare un cliente");
			return false;
		}
		if(this.view.getCmbIndirizzo().getSelectedIndex() == -1) {
			this.view.getLblErrors().setText("Selezionare un indirizzo");
			return false;
		}
		if(this.view.getTxtTipo().getText().length() <= 1) {
			this.view.getLblErrors().setText("Specificare la tipologia");
			return false;
		}
		if(this.view.getTxtDescrizione().getText().length() <= 1) {
			this.view.getLblErrors().setText("Specificare la descrizione");
			return false;
		}
		if(this.view.getTxtDescrizione().getText().length() > 255) {
			this.view.getLblErrors().setText("Descrizione troppo lunga");
			return false;
		}
		
		return true;
	}

	private Object onChangeClienteItem(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			this.view.getCmbIndirizzo().setEnabled(true);
			this.view.getCmbIndirizzo().removeAllItems();
			Cliente cliente = (Cliente) e.getItem();
			for (Indirizzo indirizzo : cliente.getIndirizzi()) {
				this.view.getCmbIndirizzo().addItem(indirizzo);
			}
		}
		return null;
	}
	
	
}
