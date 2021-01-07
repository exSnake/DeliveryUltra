package it.unisa.deliveryultra.controller;

import java.awt.HeadlessException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JOptionPane;

import it.unisa.deliveryultra.model.*;
import it.unisa.deliveryultra.view.*;

public class MainViewController {
	private Database db;
	private MainView view;
	/**
	 * 
	 */
	public MainViewController(Database dbmodel, MainView dbview) {
		this.db = dbmodel;
		this.view = dbview;
	}
	
	public void initialize() {
		initView();
		this.view.getBtnEs9().addActionListener(e -> ristorantiEs9Show());
		this.view.getBtnInserisciOrdine().addActionListener(e -> inserisciOrdineShow());
		this.view.getBtnGestisciOrdini().addActionListener(e -> gestisciOrdiniShow());
		this.view.getBtnFiltraOrdini().addActionListener(e -> filtraOrdiniShow());
		this.view.getBtnCovidTracking().addActionListener(e -> covidTrackingShow());
		this.view.getBtnValutaRider().addActionListener(e -> valutaRiderShow());
		this.view.getBtnRiderReport().addActionListener(e -> riderReportShow());
		this.view.getBtnAssumiDipendente().addActionListener(e -> assumiDipendenteShow());
		this.view.getBtnGestisciRistoranti().addActionListener(e -> gestioneRistorantiShow());
		
		this.view.getBtnPunto1().addActionListener(e -> Punto1Click());
		this.view.getBtnPunto2().addActionListener(e -> Punto2Click());
		this.view.getBtnPunto3().addActionListener(e -> Punto3Click());
		this.view.getBtnPunto4().addActionListener(e -> Punto4Click());
		this.view.getBtnPunto5().addActionListener(e -> Punto5Click());
		this.view.getBtnPunto6().addActionListener(e -> Punto6Click());
		this.view.getBtnPunto7().addActionListener(e -> Punto7Click());
		this.view.getBtnPunto8().addActionListener(e -> Punto8Click());
		this.view.getBtnPunto9().addActionListener(e -> Punto9Click());
		this.view.getBtnPunto10().addActionListener(e -> Punto10Click());
		this.view.getBtnPunto11().addActionListener(e -> Punto11Click());
		this.view.getBtnPunto12().addActionListener(e -> Punto12Click());
		this.view.getBtnPunto13().addActionListener(e -> Punto13Click());
		this.view.getBtnPunto14().addActionListener(e -> Punto14Click());
		this.view.getBtnPunto15().addActionListener(e -> Punto15Click());
		
		this.view.setVisible(true);
	}

	private void Punto15Click() {
		StringBuilder str = new StringBuilder();
	    str.append(String.format("Clienti che hanno effettuato una valutazione bassa a un rider buono:%n"));
	    try {
	    	List<Cliente> clienti = db.getClientiValutazioniBasse();
			if(clienti.isEmpty()) {
				JOptionPane.showMessageDialog(view, "Nessuna cliente ha effettuato una valutazione bassa");
				return;
			}
			for (Cliente cliente : clienti) {
				str.append(String.format("%s%n" , cliente));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    JOptionPane.showMessageDialog(view, str);
	}

	private void Punto14Click() {
		StringBuilder str = new StringBuilder();
		str.append(String.format("Report Riders%n"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		try {
			for (Rider r : db.getRiders()) {
				str.append(String.format("%s %s CF:%s %s Impieghi:%d Primo Impiego:%s%n\tScore Medio:%.2f %s",
							r.getNome(), r.getCognome(), 
							r.getCf(), r.isDisponibilita() ? "Disponibile" : "Non Disponibile", 
							r.getNumImpiego(), r.getDataPrimoImpiego().format(formatter),
							r.getScoreMedio(), r.isAutoMunito() ? "Automunito" : "Non automunito"));
				if(r.isAutoMunito()) {
					str.append(String.format(" Targa %s: %s", r.getTipoVeicolo(), r.getTarga()));
				}
				str.append(String.format("%n- - - - - - - - - -%n"));
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(view, str);
	}

	private void Punto13Click() {
		StringBuilder str = new StringBuilder();
		str.append(String.format("Dati Ristoranti%n"));
		try {
			for (Ristorante ristorante : db.getRistoranti()) {
				str.append(String.format("%s Coda Ordini: %d%n ", ristorante, ristorante.getOrdiniCoda()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(view, str);
	}

	private void Punto12Click() {
		StringBuilder str = new StringBuilder();
		str.append(String.format("Persone che hanno consegnato a Giuseppe Verdi %n"));
		try {
			List<Persona> persone = db.getPersoneByNominativoConsegna("Giuseppe Verdi");
			if(persone.isEmpty()) {
				JOptionPane.showMessageDialog(view, "Nessuna persona ha consegnato a Giuseppe Verdi nell'ultima settimana");
				return;
			}
			for (Persona persona : persone) {
				str.append(String.format("%s %s%n", persona.getNome(), persona.getCognome()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(view, "Errore");
		}
		JOptionPane.showMessageDialog(view, str);
	}

	private void Punto11Click() {
		//scegli un ordine da cancellare
		GenericComboBox cmb = new GenericComboBox("Cancella un ordine","Seleziona un ordine da cancellare", "Elimina");
		try {
			List<Ordine> ordini = db.getOrdiniInCoda();
			if(ordini.isEmpty()) {
				JOptionPane.showMessageDialog(view, "Nessun ordine cancellabile");
				return;
			}
			for (Ordine ordine : db.getOrdiniInCoda()) {
				cmb.getCmbGeneric().addItem(ordine);	
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		cmb.getBtnGeneric().addActionListener(e -> {
			Ordine ordine = (Ordine) cmb.getCmbGeneric().getSelectedItem();
			try {
				if(db.eliminaOrdine(ordine)) {
					JOptionPane.showMessageDialog(view, "Ordine eliminato con successo");
				} else {
					JOptionPane.showMessageDialog(view, "Errore nell'eliminazione");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(view, "Errore");
			}
		});
		cmb.setVisible(true);
	}

	private void Punto10Click() {
		StringBuilder str = new StringBuilder();
		str.append(String.format("Ordini consegnati da rider non valutati%n"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		try {
			for (Ordine ordine : db.getOrdiniConsegnatiDaRiderNonValutati()) {
				str.append(String.format("Ordine: %d Data: %s CodiceRistorante: %d Email Cliente: %s Rider CF: %s%n",ordine.getNumOrdine(), ordine.getDataOrdine().format(formatter), ordine.getRistoranteId(), ordine.getClienteEmail(), ordine.getPersonaCf()));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}
		JOptionPane.showMessageDialog(view, str);
	}

	private void Punto9Click() {
		StringBuilder str = new StringBuilder();
		str.append(String.format("Ristoranti che impiegano dipendenti propri o si affidano a Food Delivery%n"));
		try {
			for (Ristorante ristorante : db.getRistorantiEs9()) {
				str.append(String.format("Ristorante: %s%n", ristorante.getDenominazione()));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}
		JOptionPane.showMessageDialog(view, str);
	}

	private void Punto8Click() {
	    assumiDipendenteShow();
	}

	private void Punto7Click() {
	    gestioneRistorantiShow();
	}

	private void Punto6Click() {
	    StringBuilder str = new StringBuilder();
	    str.append(String.format("Clienti e ordini effettuati:%n"));
	    try {
			for (Cliente cliente : db.getClientiConOrdiniEffettuati()) {
				str.append(String.format("%s %s - Ordini effettuati: %d%n", cliente.getNome(), cliente.getCognome(), cliente.getOrdiniEffetuati()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    JOptionPane.showMessageDialog(view, str);
	}

	private void Punto5Click() {
	    valutaRiderShow();
	}

	private void Punto4Click() {
		StringBuilder str = new StringBuilder();
		str.append(String.format("Ristoranti disponibili%n"));
		try {
			for (Ristorante ristorante : db.getRistorantiDisponibili()) {
				str.append(String.format("%s%n", ristorante));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(view, str);
	}

	private void Punto3Click() {
	    //scegli un ristorante
		GenericComboBox cmb = new GenericComboBox("Verifica disponibilita ristorante","Seleziona ristorante", "Posso ordinare?");
		try {
			for (Ristorante ristorante : db.getRistoranti()) {
				cmb.getCmbGeneric().addItem(ristorante);	
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		cmb.getBtnGeneric().addActionListener(e -> {
			Ristorante ristorante = (Ristorante) cmb.getCmbGeneric().getSelectedItem();
			try {
				if(db.isRistoranteDisponibile(ristorante)) {
					JOptionPane.showMessageDialog(view, "Puoi ordinare dal ristorante");
				} else {
					JOptionPane.showMessageDialog(view, "Non puoi ordinare dal ristorante");
				}
			} catch (HeadlessException | SQLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(view, "Errore");
			}
		});
		cmb.setVisible(true);
	}

	private void Punto2Click() {
	    
	}

	private void Punto1Click() {
		inserisciOrdineShow();
	}

	private void gestioneRistorantiShow() {
		GestisciRistorantiController gestisciRistorantiController = new GestisciRistorantiController(this.db, new GestisciRistorantiView());
		gestisciRistorantiController.initialize();
	}

	private void assumiDipendenteShow() {
		AssumiDipendenteController assumiDipendenteController = new AssumiDipendenteController(this.db, new AssumiDipendenteView());
		assumiDipendenteController.initialize();
	}

	private void ristorantiEs9Show() {
		GestisciOrdiniController gestisciOrdiniController = new GestisciOrdiniController(this.db, new GestisciOrdiniView());
		gestisciOrdiniController.initializeEs9();
	}

	private void riderReportShow() {
		RiderReportController riderReportController = new RiderReportController(this.db, new RiderReportView());
		riderReportController.initialize();
	}

	private void initView() {
		
	}
	
	private void covidTrackingShow() {
		TrackingCovidController trackingCovidController = new TrackingCovidController(this.db, new TrackingCovidView());
		trackingCovidController.initialize();
	}
	
	private void filtraOrdiniShow() {
		FiltraOrdiniController filtraOrdiniController = new FiltraOrdiniController(this.db, new FiltraOrdiniView());
		filtraOrdiniController.initialize();
	}
	
	private void gestisciOrdiniShow() {
		GestisciOrdiniController gestisciOrdiniController = new GestisciOrdiniController(this.db, new GestisciOrdiniView());
		gestisciOrdiniController.initialize();
	}
	
	private void inserisciOrdineShow() {
		InserisciOrdineController inserisciOrdineController = new InserisciOrdineController(this.db, new InserisciOrdineView());
		inserisciOrdineController.initialize();
	}

	private void valutaRiderShow() {
		ValutazioneRiderController valutazioneRiderController = new ValutazioneRiderController(this.db, new ValutazioneRiderView());
		valutazioneRiderController.initialize();
	}
	
	
	
}
