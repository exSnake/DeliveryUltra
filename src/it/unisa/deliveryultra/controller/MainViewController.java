package it.unisa.deliveryultra.controller;

import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unisa.deliveryultra.model.Database;
import it.unisa.deliveryultra.view.GestisciOrdiniView;
import it.unisa.deliveryultra.view.InserisciOrdineView;
import it.unisa.deliveryultra.view.MainView;

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
		
	}
	
	public void initialize() {
		initView();
		this.view.getBtnInserisciOrdine().addActionListener(e -> inserisciOrdineShow());
		this.view.getBtnGestisciOrdini().addActionListener(e -> gestisciOrdiniShow());
		this.view.setVisible(true);
	}
	
	private void gestisciOrdiniShow() {
		GestisciOrdiniController gestisciOrdiniController = new GestisciOrdiniController(this.db, new GestisciOrdiniView());
		gestisciOrdiniController.initialize();
	}

	private void inserisciOrdineShow() {
		InserisciOrdineController inserisciOrdineController = new InserisciOrdineController(this.db, new InserisciOrdineView());
		inserisciOrdineController.initialize();
	}
	
	
	
}
