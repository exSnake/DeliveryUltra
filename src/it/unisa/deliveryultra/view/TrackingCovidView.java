package it.unisa.deliveryultra.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import it.unisa.deliveryultra.model.Persona;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.Color;

public class TrackingCovidView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNominativo;
	private JButton btnCerca;
	private JPanel panelList;
	private JList<Persona> lstPersone;

	public TrackingCovidView() {
		setTitle("Contact Tracing - Covid");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow][]", "[][][grow]"));
		
		JLabel lblNominativo = new JLabel("Nominativo Consegna");
		contentPane.add(lblNominativo, "cell 0 0");
		
		txtNominativo = new JTextField();
		txtNominativo.setText("Giuseppe Verdi");
		contentPane.add(txtNominativo, "cell 0 1,growx");
		txtNominativo.setColumns(10);
		
		btnCerca = new JButton("Cerca");
		contentPane.add(btnCerca, "cell 1 1");
		
		panelList = new JPanel();
		panelList.setBackground(Color.WHITE);
		contentPane.add(panelList, "cell 0 2 2 1,grow");
		
		lstPersone = new JList<>();
		panelList.add(lstPersone);
	}

	public JTextField getTxtNominativo() {
		return txtNominativo;
	}
	public JButton getBtnCerca() {
		return btnCerca;
	}
	public JList<Persona> getLstPersone() {
		return lstPersone;
	}
}
