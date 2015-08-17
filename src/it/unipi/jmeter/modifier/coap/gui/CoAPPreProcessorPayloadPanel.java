package it.unipi.jmeter.modifier.coap.gui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextArea;
import java.awt.Insets;
import javax.swing.border.EtchedBorder;

public class CoAPPreProcessorPayloadPanel extends JPanel {

	private JTextArea txaPayload;
	/**
	 * Create the panel.
	 */
	public CoAPPreProcessorPayloadPanel() {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{180, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblPayload = new JLabel("Payload:");
		GridBagConstraints gbc_lblPayload = new GridBagConstraints();
		gbc_lblPayload.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblPayload.insets = new Insets(5, 5, 0, 5);
		gbc_lblPayload.gridx = 0;
		gbc_lblPayload.gridy = 0;
		add(lblPayload, gbc_lblPayload);
		
		txaPayload = new JTextArea();
		txaPayload.setWrapStyleWord(true);
		txaPayload.setRows(20);
		GridBagConstraints gbc_txaPayload = new GridBagConstraints();
		gbc_txaPayload.insets = new Insets(5, 5, 5, 5);
		gbc_txaPayload.fill = GridBagConstraints.BOTH;
		gbc_txaPayload.gridx = 1;
		gbc_txaPayload.gridy = 0;
		add(txaPayload, gbc_txaPayload);

	}
	public String getTxaPayload() {
		return txaPayload.getText();
	}
	public void setTxaPayload(String value) {
		this.txaPayload.setText(value);
	}

}
