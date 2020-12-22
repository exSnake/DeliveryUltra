/**
 * 
 */
package it.unisa.deliveryultra.controller;

import it.unisa.deliveryultra.model.Database;
import it.unisa.deliveryultra.view.DBAccessView;

/**
 * @author exSna
 *
 */
public class DBAccessController {
	private Database db;
	private DBAccessView view;
	/**
	 * 
	 */
	public DBAccessController(Database dbmodel, DBAccessView dbview) {
		this.db = dbmodel;
		this.view = dbview;
	}
	
	private void initView() {
		view.getTxtServer().setText(db.getServer());
		view.getTxtPorta().setText(String.format("%d", db.getPort()));
		view.getTxtDbName().setText(db.getDbName());
		view.getTxtUser().setText(db.getUsername());
		view.getTxtPassword().setText(db.getPassword());
	}
	
	public void initialize() {
		initView();
		view.getBtnAccedi().addActionListener(e -> AccediBtnClick());
	}
	
	private void AccediBtnClick() {
		if(!validateForm())
			return;
		this.view.getLblErrors().setText("");
		updateModel();
		//TODO IF connection successful, main window should be opened
		if(db.openConnection())
			view.getLblErrors().setText("Connessione Riuscita");
		else {
			System.out.println(db.toString());
			view.getLblErrors().setText("Connessione Fallita");
		}
	}
	
	private void updateModel() {
		db.setServer(view.getTxtServer().getText());
		db.setPort(Integer.parseInt(view.getTxtPorta().getText()));
		db.setDbName(view.getTxtDbName().getText());
		db.setUsername(view.getTxtUser().getText());
		db.setPassword(view.getTxtPassword().getText());
	}

	private boolean validateForm() {
		if(this.view.getTxtServer().getText().isEmpty()) {
			this.view.getLblErrors().setText("Inserire l'ip di un server");
			return false;
		}
		if(this.view.getTxtPorta().getText().isEmpty()) {
			this.view.getLblErrors().setText("Inserire una porta");
			return false;
		}
		if(this.view.getTxtDbName().getText().isEmpty()) {
			this.view.getLblErrors().setText("Inserire il nome del DB");
			return false;
		}
		if(this.view.getTxtPorta().getText().isEmpty()) {
			this.view.getLblErrors().setText("Inserire l'username per l'accesso");
			return false;
		}
		
		try {
			Integer.parseInt(this.view.getTxtPorta().getText());
		} catch (Exception e) {
			this.view.getLblErrors().setText("Porta non valida");
			return false;
		}
		return true;
	}

}
