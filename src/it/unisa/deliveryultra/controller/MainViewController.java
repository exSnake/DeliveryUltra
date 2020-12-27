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
	
	private void initView() {
		//doing nothing for now
	}
	
	public void initialize() {
		initView();
		this.view.getBtnInserisciOrdine().addActionListener(e -> inserisciOrdineShow());
		this.view.getBtnGestisciOrdini().addActionListener(e -> gestisciOrdiniShow());
		this.view.getBtnFiltraOrdini().addActionListener(e -> filtraOrdiniShow());
		this.view.getBtnCovidTracking().addActionListener(e -> covidTrackingShow());
		this.view.getBtnValutaRider().addActionListener(e -> valutaRiderShow());
		this.view.setVisible(true);
	}
	
	private void valutaRiderShow() {
		ValutazioneRiderController valutazioneRiderController = new ValutazioneRiderController(this.db, new ValutazioneRiderView());
		valutazioneRiderController.initialize();
	}

	private void covidTrackingShow() {
		TrackingCovidController trackingCovidController = new TrackingCovidController(this.db, new TrackingCovidView());
		trackingCovidController.initialize();
	}

	private void gestisciOrdiniShow() {
		GestisciOrdiniController gestisciOrdiniController = new GestisciOrdiniController(this.db, new GestisciOrdiniView());
		gestisciOrdiniController.initialize();
	}
	
	private void filtraOrdiniShow() {
		FiltraOrdiniController filtraOrdiniController = new FiltraOrdiniController(this.db, new FiltraOrdiniView());
		filtraOrdiniController.initialize();
	}

	private void inserisciOrdineShow() {
		InserisciOrdineController inserisciOrdineController = new InserisciOrdineController(this.db, new InserisciOrdineView());
		inserisciOrdineController.initialize();
	}
	
	
	
}
