package it.unisa.deliveryultra.model;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ImpiegoTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;

	protected static final String[] COLUMN_NAMES = {
	        "Data Impiego",
	        "PIva Societa",
	        "Quota Oraria"
	    };
	
	private List<Impiego> rowData;
	
	public ImpiegoTableModel() {
		this.rowData = new ArrayList<>(25);
	}

	public void removeAll() {
		rowData = new ArrayList<>(25);
	}

	public void add(Impiego... pd) {
        add(Arrays.asList(pd));
    }
	
	public void add(List<Impiego> pd) {
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
		Impiego impiego = getImpiegoDataAt(rowIndex);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Object value = null;
        switch (columnIndex) {
            case 0:
            	value = impiego.getDataImpiego().format(formatter);
                break;
            case 1:
            	value = impiego.getPiva();
            	break;
            case 2:
            	value = String.format("%.2f", impiego.getQuotaOraria());
            	break;
            default:
            	break;
        }
        return value;
	}
    	
    public Impiego getImpiegoDataAt(int row) {
        return rowData.get(row);
    }

}
