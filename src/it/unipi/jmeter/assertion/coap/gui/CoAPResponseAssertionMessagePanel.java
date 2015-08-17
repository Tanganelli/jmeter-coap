package it.unipi.jmeter.assertion.coap.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.border.EtchedBorder;

import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.CoAP.Type;

import java.awt.Font;
import java.util.List;

public class CoAPResponseAssertionMessagePanel extends JPanel {

	private JTextField txtMid;
	private JTextField txtToken;
	private JComboBox cmbType;
	private JComboBox cmbCode;

	/**
	 * Create the panel.
	 */
	public CoAPResponseAssertionMessagePanel() {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{180, 342, 0};
		gridBagLayout.rowHeights = new int[]{0, 27, 28, 28, 27, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblMessageParameters = new JLabel("Message");
		lblMessageParameters.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		GridBagConstraints gbc_lblMessageParameters = new GridBagConstraints();
		gbc_lblMessageParameters.anchor = GridBagConstraints.WEST;
		gbc_lblMessageParameters.insets = new Insets(5, 5, 5, 5);
		gbc_lblMessageParameters.gridx = 0;
		gbc_lblMessageParameters.gridy = 0;
		add(lblMessageParameters, gbc_lblMessageParameters);
		
		JLabel lblMessageType = new JLabel("Type:");
		GridBagConstraints gbc_lblMessageType = new GridBagConstraints();
		gbc_lblMessageType.anchor = GridBagConstraints.EAST;
		gbc_lblMessageType.insets = new Insets(0, 0, 5, 5);
		gbc_lblMessageType.gridx = 0;
		gbc_lblMessageType.gridy = 1;
		add(lblMessageType, gbc_lblMessageType);
		
		cmbType = new JComboBox();
		GridBagConstraints gbc_cmbType = new GridBagConstraints();
		gbc_cmbType.anchor = GridBagConstraints.NORTH;
		gbc_cmbType.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbType.insets = new Insets(0, 0, 5, 0);
		gbc_cmbType.gridx = 1;
		gbc_cmbType.gridy = 1;
		add(cmbType, gbc_cmbType);
		
		JLabel lblMid = new JLabel("MID:");
		GridBagConstraints gbc_lblMid = new GridBagConstraints();
		gbc_lblMid.anchor = GridBagConstraints.EAST;
		gbc_lblMid.insets = new Insets(0, 0, 5, 5);
		gbc_lblMid.gridx = 0;
		gbc_lblMid.gridy = 2;
		add(lblMid, gbc_lblMid);
		
		txtMid = new JTextField();
		GridBagConstraints gbc_txtMid = new GridBagConstraints();
		gbc_txtMid.anchor = GridBagConstraints.NORTH;
		gbc_txtMid.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMid.insets = new Insets(0, 0, 5, 0);
		gbc_txtMid.gridx = 1;
		gbc_txtMid.gridy = 2;
		add(txtMid, gbc_txtMid);
		txtMid.setColumns(10);
		
		JLabel lblToken = new JLabel("Token:");
		GridBagConstraints gbc_lblToken = new GridBagConstraints();
		gbc_lblToken.anchor = GridBagConstraints.EAST;
		gbc_lblToken.insets = new Insets(0, 0, 5, 5);
		gbc_lblToken.gridx = 0;
		gbc_lblToken.gridy = 3;
		add(lblToken, gbc_lblToken);
		
		txtToken = new JTextField();
		GridBagConstraints gbc_txtToken = new GridBagConstraints();
		gbc_txtToken.anchor = GridBagConstraints.NORTH;
		gbc_txtToken.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtToken.insets = new Insets(0, 0, 5, 0);
		gbc_txtToken.gridx = 1;
		gbc_txtToken.gridy = 3;
		add(txtToken, gbc_txtToken);
		txtToken.setColumns(10);
		
		JLabel lblCode = new JLabel("Code:");
		GridBagConstraints gbc_lblCode = new GridBagConstraints();
		gbc_lblCode.anchor = GridBagConstraints.EAST;
		gbc_lblCode.insets = new Insets(0, 0, 0, 5);
		gbc_lblCode.gridx = 0;
		gbc_lblCode.gridy = 4;
		add(lblCode, gbc_lblCode);
		
		cmbCode = new JComboBox();
		GridBagConstraints gbc_cmbCode = new GridBagConstraints();
		gbc_cmbCode.anchor = GridBagConstraints.NORTH;
		gbc_cmbCode.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbCode.gridx = 1;
		gbc_cmbCode.gridy = 4;
		add(cmbCode, gbc_cmbCode);

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
		this.cmbType.addItem("");
		for(Type obj : types)
			this.cmbType.addItem(obj);
	}
	
	public void initCmbCode(List<ResponseCode> codes){
		this.cmbCode.addItem("");
		for(ResponseCode obj : codes)
		{
			this.cmbCode.addItem(getResponseCodeText(obj));
		}
	}
	
	private String getResponseCodeText(ResponseCode obj){
		switch (obj.value) {
			case 65: return obj.toString() + " - CREATED";
			case 66: return obj.toString() + " - DELETED";
			case 67: return obj.toString() + " - VALID";
			case 68: return obj.toString() + " - CHANGED";
			case 69: return obj.toString() + " - CONTENT";
			case 95: return obj.toString() + " - CONTINUE";
			case 128: return obj.toString() + " - BAD_REQUEST";
			case 129: return obj.toString() + " - UNAUTHORIZED";
			case 130: return obj.toString() + " - BAD_OPTION";
			case 131: return obj.toString() + " - FORBIDDEN";
			case 132: return obj.toString() + " - NOT_FOUND";
			case 133: return obj.toString() + " - METHOD_NOT_ALLOWED";
			case 134: return obj.toString() + " - NOT_ACCEPTABLE";
			case 136: return obj.toString() + " - REQUEST_ENTITY_INCOMPLETE";
			case 140: return obj.toString() + " - PRECONDITION_FAILED";
			case 141: return obj.toString() + " - REQUEST_ENTITY_TOO_LARGE";
			case 143: return obj.toString() + " - UNSUPPORTED_CONTENT_FORMAT";
			case 160: return obj.toString() + " - INTERNAL_SERVER_ERROR";
			case 161: return obj.toString() + " - NOT_IMPLEMENTED";
			case 162: return obj.toString() + " - BAD_GATEWAY";
			case 163: return obj.toString() + " - SERVICE_UNAVAILABLE";
			case 164: return obj.toString() + " - GATEWAY_TIMEOUT";
			case 165: return obj.toString() + " - PROXY_NOT_SUPPORTED";
		}
		return null;
	}

}
