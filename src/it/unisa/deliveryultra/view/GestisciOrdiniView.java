package it.unisa.deliveryultra.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import it.unisa.deliveryultra.model.*;
import net.miginfocom.swing.MigLayout;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class GestisciOrdiniView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnCerca;
	private JComboBox<Ristorante> cmbRistoranti;
	private JList<Ordine> lstOrdini;
	private JButton btnAssegna;
	private JButton btnElimina;
	private JButton btnConsegna;
	private JScrollPane scrollPane;
	private JButton btnAccetta;
	private JButton btnInCoda;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestisciOrdiniView frame = new GestisciOrdiniView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GestisciOrdiniView() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 768, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow][][][][]", "[][][grow][]"));
		
		JLabel lblRistorante = new JLabel("Seleziona un ristorante");
		contentPane.add(lblRistorante, "cell 0 0");
		
		cmbRistoranti = new JComboBox<>();
		contentPane.add(cmbRistoranti, "cell 0 1 3 1,growx");
		
		btnCerca = new JButton("Cerca");
		contentPane.add(btnCerca, "cell 3 1,grow");
		
		btnInCoda = new JButton("In coda");
		contentPane.add(btnInCoda, "cell 4 1");
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "cell 0 2 5 1,grow");
		
		lstOrdini = new JList<>();
		scrollPane.setViewportView(lstOrdini);
		lstOrdini.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		btnElimina = new JButton("Elimina");
		btnElimina.setEnabled(false);
		contentPane.add(btnElimina, "cell 1 3");
		
		btnAccetta = new JButton("Accetta");
		btnAccetta.setEnabled(false);
		contentPane.add(btnAccetta, "cell 2 3");
		
		btnAssegna = new JButton("Assegna");
		btnAssegna.setEnabled(false);
		contentPane.add(btnAssegna, "cell 3 3");
		
		btnConsegna = new JButton("Consegna");
		btnConsegna.setEnabled(false);
		contentPane.add(btnConsegna, "cell 4 3");
	}

	public JButton getBtnCerca() {
		return btnCerca;
	}
	public JComboBox<Ristorante> getCmbRistoranti() {
		return cmbRistoranti;
	}
	public JList<Ordine> getLstOrdini() {
		return lstOrdini;
	}
	public JButton getBtnAssegna() {
		return btnAssegna;
	}
	public JButton getBtnElimina() {
		return btnElimina;
	}
	public JButton getBtnConsegna() {
		return btnConsegna;
	}
	public JButton getBtnAccetta() {
		return btnAccetta;
	}
	public JButton getBtnInCoda() {
		return btnInCoda;
	}
}
