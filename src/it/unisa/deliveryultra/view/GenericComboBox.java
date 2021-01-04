package it.unisa.deliveryultra.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;

public class GenericComboBox extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnGeneric;
	private JLabel lblGeneric;
	private JComboBox<Object> cmbGeneric;

	public GenericComboBox() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 140);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[][][]"));
		
		lblGeneric = new JLabel("New label");
		contentPane.add(lblGeneric, "cell 0 0");
		
		cmbGeneric = new JComboBox<>();
		contentPane.add(cmbGeneric, "cell 0 1,growx");
		
		btnGeneric = new JButton("New button");
		contentPane.add(btnGeneric, "cell 0 2,alignx right");
	}

	public GenericComboBox(String title, String label, String btn) {
		this();
		this.setTitle(title);
		this.getLblGeneric().setText(label);
		this.getBtnGeneric().setText(btn);
	}

	public JButton getBtnGeneric() {
		return btnGeneric;
	}
	public JLabel getLblGeneric() {
		return lblGeneric;
	}
	public JComboBox<Object> getCmbGeneric() {
		return cmbGeneric;
	}
}
