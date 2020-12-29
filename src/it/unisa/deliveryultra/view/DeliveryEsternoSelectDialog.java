package it.unisa.deliveryultra.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import it.unisa.deliveryultra.model.Delivery;
import it.unisa.deliveryultra.model.Societa;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;

public class DeliveryEsternoSelectDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JComboBox<Delivery> cmdDeliveries;
	private JButton okButton;
	private JButton cancelButton;
	private JComboBox<Societa> cmbSocieta;
	private JLabel lblSocieta;
	private JLabel lblDelivery;

	public DeliveryEsternoSelectDialog() {
		setTitle("Affidamento societa a Delivery Esterno");
		setBounds(100, 100, 450, 173);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[][][][]"));
		
		lblSocieta = new JLabel("Societa");
		contentPanel.add(lblSocieta, "cell 0 0");
		
		cmbSocieta = new JComboBox<>();
		contentPanel.add(cmbSocieta, "cell 0 1,growx");
		
		lblDelivery = new JLabel("Delivery Esterno");
		contentPanel.add(lblDelivery, "cell 0 2");
		
		cmdDeliveries = new JComboBox<>();
		contentPanel.add(cmdDeliveries, "cell 0 3,growx");
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		okButton = new JButton("Affida");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}

	public JComboBox<Delivery> getCmbDeliveries() {
		return cmdDeliveries;
	}
	public JButton getOkButton() {
		return okButton;
	}
	public JButton getCancelButton() {
		return cancelButton;
	}
	public JComboBox<Societa> getCmbSocieta() {
		return cmbSocieta;
	}
}
