package it.unisa.deliveryultra.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;

public class MainView extends JFrame {

	private JPanel contentPane;
	private JButton btnInserisciOrdine;

	/**
	 * Create the frame.
	 */
	public MainView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 768, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[]", "[][]"));
		
		setBtnInserisciOrdine(new JButton("Inserisci Ordine"));
		
		contentPane.add(getBtnInserisciOrdine(), "cell 0 1");
	}

	public JButton getBtnInserisciOrdine() {
		return btnInserisciOrdine;
	}

	public void setBtnInserisciOrdine(JButton btnInserisciOrdine) {
		this.btnInserisciOrdine = btnInserisciOrdine;
	}
}
