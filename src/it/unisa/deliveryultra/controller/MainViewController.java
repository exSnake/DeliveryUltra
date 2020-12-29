package it.unisa.deliveryultra.controller;

import it.unisa.deliveryultra.model.Database;
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
		this.view.setVisible(true);
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
		//doing nothing for now
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
