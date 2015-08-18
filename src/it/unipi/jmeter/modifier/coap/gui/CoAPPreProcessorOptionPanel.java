package it.unipi.jmeter.modifier.coap.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.border.EtchedBorder;

import java.awt.Font;
import java.util.List;

public class CoAPPreProcessorOptionPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtETag;
	private JTextField txtIfMatch;
	private JTextField txtUriHost;
	private JTextField txtObserve;
	private JTextField txtUriPort;
	private JTextField txtProxyUri;
	private JComboBox cmbAccept;
	private JComboBox cmbContentFormat;
	private JCheckBox chkIfNoneMatch;
	private JCheckBox chkProxySchema;

	/**
	 * Create the panel.
	 */
	public CoAPPreProcessorOptionPanel() {
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{180, 312, 0};
		gridBagLayout.rowHeights = new int[]{0, 27, 27, 28, 28, 28, 23, 28, 28, 28, 23, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblOptionParameters = new JLabel("Options");
		lblOptionParameters.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		GridBagConstraints gbc_lblOptionParameters = new GridBagConstraints();
		gbc_lblOptionParameters.anchor = GridBagConstraints.WEST;
		gbc_lblOptionParameters.insets = new Insets(5, 5, 5, 5);
		gbc_lblOptionParameters.gridx = 0;
		gbc_lblOptionParameters.gridy = 0;
		add(lblOptionParameters, gbc_lblOptionParameters);
		
		JLabel lblAccepts = new JLabel("Accept:");
		GridBagConstraints gbc_lblAccepts = new GridBagConstraints();
		gbc_lblAccepts.anchor = GridBagConstraints.EAST;
		gbc_lblAccepts.insets = new Insets(0, 0, 5, 5);
		gbc_lblAccepts.gridx = 0;
		gbc_lblAccepts.gridy = 1;
		add(lblAccepts, gbc_lblAccepts);
		
		cmbAccept = new JComboBox();
		GridBagConstraints gbc_cmbAccept = new GridBagConstraints();
		gbc_cmbAccept.anchor = GridBagConstraints.NORTH;
		gbc_cmbAccept.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbAccept.insets = new Insets(0, 0, 5, 0);
		gbc_cmbAccept.gridx = 1;
		gbc_cmbAccept.gridy = 1;
		add(cmbAccept, gbc_cmbAccept);
		
		JLabel lblContenttype = new JLabel("Content-Format:");
		GridBagConstraints gbc_lblContenttype = new GridBagConstraints();
		gbc_lblContenttype.anchor = GridBagConstraints.EAST;
		gbc_lblContenttype.insets = new Insets(0, 0, 5, 5);
		gbc_lblContenttype.gridx = 0;
		gbc_lblContenttype.gridy = 2;
		add(lblContenttype, gbc_lblContenttype);
		
		cmbContentFormat = new JComboBox();
		GridBagConstraints gbc_cmbContentFormat = new GridBagConstraints();
		gbc_cmbContentFormat.anchor = GridBagConstraints.NORTH;
		gbc_cmbContentFormat.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbContentFormat.insets = new Insets(0, 0, 5, 0);
		gbc_cmbContentFormat.gridx = 1;
		gbc_cmbContentFormat.gridy = 2;
		add(cmbContentFormat, gbc_cmbContentFormat);
		
		JLabel lblObserve = new JLabel("Observe:");
		GridBagConstraints gbc_lblObserve = new GridBagConstraints();
		gbc_lblObserve.anchor = GridBagConstraints.EAST;
		gbc_lblObserve.insets = new Insets(0, 0, 5, 5);
		gbc_lblObserve.gridx = 0;
		gbc_lblObserve.gridy = 3;
		add(lblObserve, gbc_lblObserve);
		
		txtObserve = new JTextField();
		GridBagConstraints gbc_txtObserve = new GridBagConstraints();
		gbc_txtObserve.anchor = GridBagConstraints.NORTH;
		gbc_txtObserve.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtObserve.insets = new Insets(0, 0, 5, 0);
		gbc_txtObserve.gridx = 1;
		gbc_txtObserve.gridy = 3;
		add(txtObserve, gbc_txtObserve);
		txtObserve.setColumns(10);
		
		JLabel lblEtag = new JLabel("ETag:");
		GridBagConstraints gbc_lblEtag = new GridBagConstraints();
		gbc_lblEtag.anchor = GridBagConstraints.EAST;
		gbc_lblEtag.insets = new Insets(0, 0, 5, 5);
		gbc_lblEtag.gridx = 0;
		gbc_lblEtag.gridy = 4;
		add(lblEtag, gbc_lblEtag);
		
		txtETag = new JTextField();
		GridBagConstraints gbc_txtETag = new GridBagConstraints();
		gbc_txtETag.anchor = GridBagConstraints.NORTH;
		gbc_txtETag.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtETag.insets = new Insets(0, 0, 5, 0);
		gbc_txtETag.gridx = 1;
		gbc_txtETag.gridy = 4;
		add(txtETag, gbc_txtETag);
		txtETag.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("If-Match:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 5;
		add(lblNewLabel, gbc_lblNewLabel);
		
		txtIfMatch = new JTextField();
		GridBagConstraints gbc_txtIfMatch = new GridBagConstraints();
		gbc_txtIfMatch.anchor = GridBagConstraints.NORTH;
		gbc_txtIfMatch.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIfMatch.insets = new Insets(0, 0, 5, 0);
		gbc_txtIfMatch.gridx = 1;
		gbc_txtIfMatch.gridy = 5;
		add(txtIfMatch, gbc_txtIfMatch);
		txtIfMatch.setColumns(10);
		
		JLabel lblIfnonematch = new JLabel("If-None-Match:");
		GridBagConstraints gbc_lblIfnonematch = new GridBagConstraints();
		gbc_lblIfnonematch.anchor = GridBagConstraints.EAST;
		gbc_lblIfnonematch.insets = new Insets(0, 0, 5, 5);
		gbc_lblIfnonematch.gridx = 0;
		gbc_lblIfnonematch.gridy = 6;
		add(lblIfnonematch, gbc_lblIfnonematch);
		
		chkIfNoneMatch = new JCheckBox("");
		GridBagConstraints gbc_chkIfNoneMatch = new GridBagConstraints();
		gbc_chkIfNoneMatch.anchor = GridBagConstraints.NORTH;
		gbc_chkIfNoneMatch.fill = GridBagConstraints.HORIZONTAL;
		gbc_chkIfNoneMatch.insets = new Insets(0, 0, 5, 0);
		gbc_chkIfNoneMatch.gridx = 1;
		gbc_chkIfNoneMatch.gridy = 6;
		add(chkIfNoneMatch, gbc_chkIfNoneMatch);
		
		JLabel lblUrihost = new JLabel("Uri-Host:");
		GridBagConstraints gbc_lblUrihost = new GridBagConstraints();
		gbc_lblUrihost.anchor = GridBagConstraints.EAST;
		gbc_lblUrihost.insets = new Insets(0, 0, 5, 5);
		gbc_lblUrihost.gridx = 0;
		gbc_lblUrihost.gridy = 7;
		add(lblUrihost, gbc_lblUrihost);
		
		txtUriHost = new JTextField();
		GridBagConstraints gbc_txtUriHost = new GridBagConstraints();
		gbc_txtUriHost.anchor = GridBagConstraints.NORTH;
		gbc_txtUriHost.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUriHost.insets = new Insets(0, 0, 5, 0);
		gbc_txtUriHost.gridx = 1;
		gbc_txtUriHost.gridy = 7;
		add(txtUriHost, gbc_txtUriHost);
		txtUriHost.setColumns(10);
		
		JLabel lblUriport = new JLabel("Uri-Port:");
		GridBagConstraints gbc_lblUriport = new GridBagConstraints();
		gbc_lblUriport.anchor = GridBagConstraints.EAST;
		gbc_lblUriport.insets = new Insets(0, 0, 5, 5);
		gbc_lblUriport.gridx = 0;
		gbc_lblUriport.gridy = 8;
		add(lblUriport, gbc_lblUriport);
		
		txtUriPort = new JTextField();
		GridBagConstraints gbc_txtUriPort = new GridBagConstraints();
		gbc_txtUriPort.anchor = GridBagConstraints.NORTH;
		gbc_txtUriPort.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUriPort.insets = new Insets(0, 0, 5, 0);
		gbc_txtUriPort.gridx = 1;
		gbc_txtUriPort.gridy = 8;
		add(txtUriPort, gbc_txtUriPort);
		txtUriPort.setColumns(10);
		
		JLabel lblProxyuri = new JLabel("Proxy-Uri:");
		GridBagConstraints gbc_lblProxyuri = new GridBagConstraints();
		gbc_lblProxyuri.anchor = GridBagConstraints.EAST;
		gbc_lblProxyuri.insets = new Insets(0, 0, 5, 5);
		gbc_lblProxyuri.gridx = 0;
		gbc_lblProxyuri.gridy = 9;
		add(lblProxyuri, gbc_lblProxyuri);
		
		txtProxyUri = new JTextField();
		GridBagConstraints gbc_txtProxyUri = new GridBagConstraints();
		gbc_txtProxyUri.anchor = GridBagConstraints.NORTH;
		gbc_txtProxyUri.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtProxyUri.insets = new Insets(0, 0, 5, 0);
		gbc_txtProxyUri.gridx = 1;
		gbc_txtProxyUri.gridy = 9;
		add(txtProxyUri, gbc_txtProxyUri);
		txtProxyUri.setColumns(10);
		
		JLabel lblUseProxyschema = new JLabel("Use Proxy-Schema:");
		GridBagConstraints gbc_lblUseProxyschema = new GridBagConstraints();
		gbc_lblUseProxyschema.anchor = GridBagConstraints.EAST;
		gbc_lblUseProxyschema.insets = new Insets(0, 5, 0, 5);
		gbc_lblUseProxyschema.gridx = 0;
		gbc_lblUseProxyschema.gridy = 10;
		add(lblUseProxyschema, gbc_lblUseProxyschema);
		
		chkProxySchema = new JCheckBox("");
		GridBagConstraints gbc_chkProxySchema = new GridBagConstraints();
		gbc_chkProxySchema.anchor = GridBagConstraints.NORTH;
		gbc_chkProxySchema.fill = GridBagConstraints.HORIZONTAL;
		gbc_chkProxySchema.gridx = 1;
		gbc_chkProxySchema.gridy = 10;
		add(chkProxySchema, gbc_chkProxySchema);

	}

	public String getTxtETag() {
		return txtETag.getText();
	}

	public void setTxtETag(String value) {
		this.txtETag.setText(value);
	}

	public String getTxtIfMatch() {
		return txtIfMatch.getText();
	}

	public void setTxtIfMatch(String value) {
		this.txtIfMatch.setText(value);
	}

	public String getTxtUriHost() {
		return txtUriHost.getText();
	}

	public void setTxtUriHost(String value) {
		this.txtUriHost.setText(value);
	}

	public String getTxtObserve() {
		return txtObserve.getText();
	}

	public void setTxtObserve(String value) {
		this.txtObserve.setText(value);
	}

	public String getTxtUriPort() {
		return txtUriPort.getText();
	}

	public void setTxtUriPort(String value) {
		this.txtUriPort.setText(value);
	}

	public String getTxtProxyUri() {
		return txtProxyUri.getText();
	}

	public void setTxtProxyUri(String value) {
		this.txtProxyUri.setText(value);
	}

	public Object getCmbAccept() {
		return cmbAccept.getSelectedItem();
	}

	public void setCmbAccept(Object obj) {
		this.cmbAccept.setSelectedItem(obj);
	}

	public Object getCmbContentFormat() {
		return cmbContentFormat.getSelectedItem();
	}

	public void setCmbContentFormat(Object obj) {
		this.cmbContentFormat.setSelectedItem(obj);
	}

	public boolean getChkIfNoneMatch() {
		return chkIfNoneMatch.isSelected();
	}

	public void setChkIfNoneMatch(boolean b) {
		this.chkIfNoneMatch.setSelected(b);
	}

	public boolean getChkProxySchema() {
		return chkProxySchema.isSelected();
	}

	public void setChkProxySchema(boolean b) {
		this.chkProxySchema.setSelected(b);
	}
	
	public void initCmbAccept(List<String> media_types){
		for(String obj : media_types)
			this.cmbAccept.addItem(obj);
	}
	
	public void initCmbContentFormat(List<String> media_types){
		for(String obj : media_types)
			this.cmbContentFormat.addItem(obj);
	}

}

