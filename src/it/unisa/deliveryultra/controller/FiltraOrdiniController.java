package it.unisa.deliveryultra.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import it.unisa.deliveryultra.model.*;
import it.unisa.deliveryultra.view.FiltraOrdiniView;

public class FiltraOrdiniController {
	
	private Database db;
	private FiltraOrdiniView view;
	private String lastEvent;
	OrdineTableModel model;
	
	
	public FiltraOrdiniController(Database db, FiltraOrdiniView view) {
		this.db = db;
		this.view = view;
		this.lastEvent = "";
	}
	
	public void initialize() {
		setLabelToNull();
		try {
			for (Cliente cliente : db.getClienti()) {
				view.getCmbClienti().addItem(cliente);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}
		view.getBtnOrdiniValutabili().addActionListener(e -> onOrdiniValutabiliClick());
		view.getCmbClienti().setSelectedIndex(-1);
		view.getBtnCerca().addActionListener(e -> onCercaClick());
		view.getBtnElimina().addActionListener(e -> onEliminaClick());
		view.getCmbClienti().addActionListener(e -> onSelectedClienteChange());
		view.getTable().getSelectionModel().addListSelectionListener(e -> onSelectedOrdineChange());
		view.setVisible(true);
	}

	private void onOrdiniValutabiliClick() {
		List<Ordine> ordini;
		resetForm();
		view.getModel().removeAll();
		view.getLblVisualizzazioneOrdini().setText("Ordini consegnati da Rider non valutati");
		try {
			ordini = db.getOrdiniConsegnatiDaRiderNonValutati();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		view.getModel().add(ordini);
	}

	private void onSelectedClienteChange() {
		resetForm();
		if(view.getCmbClienti().getSelectedIndex() == -1) {
			setLabelToNull();
		} else {
			setLabelByCliente((Cliente)view.getCmbClienti().getSelectedItem());
		}
	}

	private void setLabelToNull() {
		view.getLblNomeBody().setText("Tutti");
		view.getLblCognomeBody().setText("N/D");
		view.getLblTelefonoBody().setText("N/D");
		view.getLblEmailBody().setText("N/D");
		view.getLblDataRegBody().setText("N/D");
		view.getLblOrdiniBody().setText("N/D");
		view.getLblValutazioniBody().setText("N/D");
	}
	
	private void onCercaClick() {
		lastEvent = "cercaClick";
		List<Ordine> ordini;
		view.getModel().removeAll();
		try {
			if(view.getCmbClienti().getSelectedIndex() == -1) {
				ordini = db.getOrdini();
			} else {
				Cliente cliente = (Cliente) view.getCmbClienti().getSelectedItem();
				ordini = db.getOrdiniByCliente(cliente.getEmail());
				view.getLblOrdiniBody().setText(String.format("%d",ordini.size()));
				view.getLblValutazioniBody().setText(String.format("%d",db.getValutazioniEffettuateByCliente(cliente.getEmail()).size()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		view.getModel().add(ordini);
	}

	private void onEliminaClick() {
		Ordine ordine = view.getModel().getOrdineDataAt(view.getTable().getSelectedRow());
		if(!ordine.getStato().equals("CONSEGNATO")) {
			try {
				db.eliminaOrdine(ordine);
				resetForm();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void onSelectedOrdineChange() {
		Ordine ordine = view.getTable().getSelectedRow() == -1 ? null : view.getModel().getOrdineDataAt(view.getTable().getSelectedRow());
		boolean val = ordine == null || ordine.getStato().equals("CONSEGNATO");
		view.getBtnElimina().setEnabled(!val);
	}
	
	private void resetForm() {
		if(lastEvent.equals("")) {
			view.getModel().removeAll();
		} else if (lastEvent.equals("cercaClick")) {
			onCercaClick();
		}
	}
	
	private void setLabelByCliente(Cliente cliente) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		view.getLblNomeBody().setText(cliente.getNome());
		view.getLblCognomeBody().setText(cliente.getCognome());
		view.getLblTelefonoBody().setText(cliente.getTelefono());
		view.getLblEmailBody().setText(cliente.getEmail());
		view.getLblDataRegBody().setText(cliente.getDataReg().format(formatter));
		
	}
}
