package it.unisa.deliveryultra.model;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ValutazioneTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static final String[] COLUMN_NAMES = {
	        "Data",
	        "Cliente",
	        "Valutazione",
	    };
	
	private List<Valutazione> rowData;
	
	public ValutazioneTableModel() {
		rowData = new ArrayList<>(25);
	}
	
	public void removeAll() {
		rowData = new ArrayList<>(25);
	}

	public void add(Valutazione... pd) {
        add(Arrays.asList(pd));
    }
	
	public void add(List<Valutazione> pd) {
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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Valutazione valutazione = getValutazioneDataAt(rowIndex);
        Object value = null;
        switch (columnIndex) {
            case 0:
                value = valutazione.getDataValutazione().format(formatter);
                break;
            case 1:
                value = valutazione.getClienteEmail();
                break;
            case 2:
                value = valutazione.getValutazione();
                break;
            default:
            	break;
        }
        return value;
	}
    	
    public Valutazione getValutazioneDataAt(int row) {
        return rowData.get(row);
    }
}
