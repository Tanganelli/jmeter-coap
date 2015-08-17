package it.unipi.jmeter.modifier.coap.gui;

import javax.swing.JPanel;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.border.EtchedBorder;

import org.eclipse.californium.core.coap.CoAP.Code;
import org.eclipse.californium.core.coap.CoAP.Type;

import java.awt.Font;
import java.util.List;

public class CoAPPreProcessorMessagePanel extends JPanel {

	private JTextField txtMid;
	private JTextField txtToken;
	private JTextField txtPath;
	private JComboBox cmbType;
	private JComboBox cmbCode;
	private JTextField txtNumRequests;
	
	/**
	 * Create the panel.
	 */
	public CoAPPreProcessorMessagePanel() {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{180, 342, 0};
		gridBagLayout.rowHeights = new int[]{0, 27, 28, 28, 27, 28, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblMessageParameters = new JLabel("Message");
		lblMessageParameters.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		GridBagConstraints gbc_lblMessageParameters = new GridBagConstraints();
		gbc_lblMessageParameters.anchor = GridBagConstraints.WEST;
		gbc_lblMessageParameters.insets = new Insets(5, 5, 5, 5);
		gbc_lblMessageParameters.gridx = 0;
		gbc_lblMessageParameters.gridy = 0;
		add(lblMessageParameters, gbc_lblMessageParameters);
		
		JLabel lblNumberOfRequests = new JLabel("Number of Requests:");
		GridBagConstraints gbc_lblNumberOfRequests = new GridBagConstraints();
		gbc_lblNumberOfRequests.anchor = GridBagConstraints.EAST;
		gbc_lblNumberOfRequests.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfRequests.gridx = 0;
		gbc_lblNumberOfRequests.gridy = 1;
		add(lblNumberOfRequests, gbc_lblNumberOfRequests);
		
		txtNumRequests = new JTextField();
		GridBagConstraints gbc_txtNumRequests = new GridBagConstraints();
		gbc_txtNumRequests.insets = new Insets(0, 0, 5, 0);
		gbc_txtNumRequests.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNumRequests.gridx = 1;
		gbc_txtNumRequests.gridy = 1;
		add(txtNumRequests, gbc_txtNumRequests);
		txtNumRequests.setColumns(10);
		
		JLabel lblMessageType = new JLabel("Type:");
		GridBagConstraints gbc_lblMessageType = new GridBagConstraints();
		gbc_lblMessageType.anchor = GridBagConstraints.EAST;
		gbc_lblMessageType.insets = new Insets(0, 0, 5, 5);
		gbc_lblMessageType.gridx = 0;
		gbc_lblMessageType.gridy = 2;
		add(lblMessageType, gbc_lblMessageType);
		
		cmbType = new JComboBox();
		GridBagConstraints gbc_cmbType = new GridBagConstraints();
		gbc_cmbType.anchor = GridBagConstraints.NORTH;
		gbc_cmbType.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbType.insets = new Insets(0, 0, 5, 0);
		gbc_cmbType.gridx = 1;
		gbc_cmbType.gridy = 2;
		add(cmbType, gbc_cmbType);
		
		JLabel lblMid = new JLabel("Starting MID:");
		GridBagConstraints gbc_lblMid = new GridBagConstraints();
		gbc_lblMid.anchor = GridBagConstraints.EAST;
		gbc_lblMid.insets = new Insets(0, 0, 5, 5);
		gbc_lblMid.gridx = 0;
		gbc_lblMid.gridy = 3;
		add(lblMid, gbc_lblMid);
		
		txtMid = new JTextField();
		GridBagConstraints gbc_txtMid = new GridBagConstraints();
		gbc_txtMid.anchor = GridBagConstraints.NORTH;
		gbc_txtMid.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMid.insets = new Insets(0, 0, 5, 0);
		gbc_txtMid.gridx = 1;
		gbc_txtMid.gridy = 3;
		add(txtMid, gbc_txtMid);
		txtMid.setColumns(10);
		
		JLabel lblToken = new JLabel("Starting Token:");
		GridBagConstraints gbc_lblToken = new GridBagConstraints();
		gbc_lblToken.anchor = GridBagConstraints.EAST;
		gbc_lblToken.insets = new Insets(0, 0, 5, 5);
		gbc_lblToken.gridx = 0;
		gbc_lblToken.gridy = 4;
		add(lblToken, gbc_lblToken);
		
		txtToken = new JTextField();
		GridBagConstraints gbc_txtToken = new GridBagConstraints();
		gbc_txtToken.anchor = GridBagConstraints.NORTH;
		gbc_txtToken.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtToken.insets = new Insets(0, 0, 5, 0);
		gbc_txtToken.gridx = 1;
		gbc_txtToken.gridy = 4;
		add(txtToken, gbc_txtToken);
		txtToken.setColumns(10);
		
		JLabel lblCode = new JLabel("Code:");
		GridBagConstraints gbc_lblCode = new GridBagConstraints();
		gbc_lblCode.anchor = GridBagConstraints.EAST;
		gbc_lblCode.insets = new Insets(0, 0, 5, 5);
		gbc_lblCode.gridx = 0;
		gbc_lblCode.gridy = 5;
		add(lblCode, gbc_lblCode);
		
		cmbCode = new JComboBox();
		GridBagConstraints gbc_cmbCode = new GridBagConstraints();
		gbc_cmbCode.anchor = GridBagConstraints.NORTH;
		gbc_cmbCode.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbCode.insets = new Insets(0, 0, 5, 0);
		gbc_cmbCode.gridx = 1;
		gbc_cmbCode.gridy = 5;
		add(cmbCode, gbc_cmbCode);
		
		JLabel lblResourcePath = new JLabel("Resource Path:");
		GridBagConstraints gbc_lblResourcePath = new GridBagConstraints();
		gbc_lblResourcePath.anchor = GridBagConstraints.EAST;
		gbc_lblResourcePath.insets = new Insets(0, 5, 0, 5);
		gbc_lblResourcePath.gridx = 0;
		gbc_lblResourcePath.gridy = 6;
		add(lblResourcePath, gbc_lblResourcePath);
		
		txtPath = new JTextField();
		GridBagConstraints gbc_txtPath = new GridBagConstraints();
		gbc_txtPath.anchor = GridBagConstraints.NORTH;
		gbc_txtPath.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPath.gridx = 1;
		gbc_txtPath.gridy = 6;
		add(txtPath, gbc_txtPath);
		txtPath.setColumns(10);

	}

	public String getTxtNumRequests() {
		return txtNumRequests.getText();
	}

	public void setTxtNumRequests(String value) {
		this.txtNumRequests.setText(value);
	}
	
	public String getTxtMid() {
		return txtMid.getText();
	}

	public void setTxtMid(String value) {
		this.txtMid.setText(value);
	}

	public String getTxtToken() {
		return txtToken.getText();
	}

	public void setTxtToken(String value) {
		this.txtToken.setText(value);
	}

	public String getTxtPath() {
		return txtPath.getText();
	}

	public void setTxtPath(String value) {
		this.txtPath.setText(value);
	}

	public Object getCmbType() {
		return cmbType.getSelectedItem();
	}

	public void setCmbType(Object obj) {
		this.cmbType.setSelectedItem(obj);
	}

	public Object getCmbCode() {
		return cmbCode.getSelectedItem();
	}

	public void setCmbCode(Object obj) {
		this.cmbCode.setSelectedItem(obj);
	}
	
	public void initCmbType(List<Type> types){
		for(Type obj : types)
			this.cmbType.addItem(obj);
	}
	
	public void initCmbCode(List<Code> codes){
		for(Code obj : codes)
			this.cmbCode.addItem(obj);
	}

}
