package it.unisa.deliveryultra.model;

import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.swing.table.AbstractTableModel;

public class OrdineTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static final String[] COLUMN_NAMES = {
	        "Num",
	        "Data",
	        "IDR",
	        "Cliente",
	        "Stato",
	        "Stima Consegna",
	        "Incaricato",
	        "Orario Consegna",
	        "Nominativo Ritiro"
	    };
	
	private List<Ordine> rowData;
	
	public void removeAll() {
		rowData = new ArrayList<>(25);
	}
	
	public OrdineTableModel() {
		rowData = new ArrayList<>(25);
	}
	
	public void add(Ordine... pd) {
        add(Arrays.asList(pd));
    }
	
	public void add(List<Ordine> pd) {
        rowData.addAll(pd);
        fireTableDataChanged();
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
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
//		Custom format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		Ordine ordine = getOrdineDataAt(rowIndex);
        Object value = null;
        switch (columnIndex) {
            case 0:
                value = ordine.getNumOrdine();
                break;
            case 1:
                value =  ordine.getDataOrdine().format(formatter);
                break;
            case 2:
                value = ordine.getRistoranteId();
                break;
            case 3:
            	value = ordine.getClienteEmail();
            	break;
            case 4:
            	value = ordine.getStato();
            	break;
            case 5:
            	value = ordine.getStimaOrario() == null ? "N/D" : ordine.getStimaOrario().format(formatter);
            	break;
            case 6:
            	value = ordine.getPersonaCf() == null ? "N/D" : ordine.getPersonaCf();
            	break;
            case 7:
            	value = ordine.getOrarioConsegna() == null ? "N/D" : ordine.getOrarioConsegna().format(formatter);
            	break;
            case 8:
            	value = ordine.getNominativoConsegna() == null ? "N/D" : ordine.getNominativoConsegna();
            	break;
            default:
            	break;
        }
        return value;
	}
	
	public Ordine getOrdineDataAt(int row) {
        return rowData.get(row);
    }

}
