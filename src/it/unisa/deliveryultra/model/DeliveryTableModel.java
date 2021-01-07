package it.unisa.deliveryultra.model;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class DeliveryTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	protected static final String[] COLUMN_NAMES = {
		"Codice",
		"Tipologia",
		"Descrizione",
		"Data Inizio",
		"Cadenza"
	};
	
	private List<Delivery> rowData;
	
	public DeliveryTableModel() {
		this.rowData = new ArrayList<>(25);				
	}

	
	public void removeAll() {
		rowData = new ArrayList<>(25);
	}
	
	public void add(Delivery... pd) {
        add(Arrays.asList(pd));
    }
	
	public void add(List<Delivery> pd) {
        rowData.addAll(pd);
        fireTableDataChanged();
    }
	
	@Override
	public String getColumnName(int column) {
		return COLUMN_NAMES[column];
	}

	@Override
	public int getRowCount() {
		return rowData.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Delivery delivery = getDeliveryDataAt(rowIndex);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Object value = null;
        switch (columnIndex) {
            case 0:
            	value = delivery.getCodice();
                break;
            case 1:
            	value = delivery.getTipologia();
            	break;
            case 2:
            	value = delivery.getDescrizione();
            	break;
            case 3:
            	value = delivery.getData().format(formatter);
            	break;
            case 4:
            	value = delivery.getCadenza();
            	break;
            default:
            	break;
        }
        return value;
	}
    	
    private Delivery getDeliveryDataAt(int rowIndex) {
		return rowData.get(rowIndex);
	}

}
