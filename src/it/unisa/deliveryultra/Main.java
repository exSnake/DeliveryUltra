package it.unisa.deliveryultra;

import it.unisa.deliveryultra.model.*;
import it.unisa.deliveryultra.controller.*;
import it.unisa.deliveryultra.view.*;

public class Main {

	public static void main(String[] args) {
		Database db = new DBMysql("127.0.0.1", 3306, "del2", "root", "root");
		DBAccessView view = new DBAccessView();
		DBAccessController controller = new DBAccessController(db, view);
		controller.initialize();
	}
}
