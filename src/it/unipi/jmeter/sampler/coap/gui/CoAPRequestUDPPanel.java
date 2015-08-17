package it.unipi.jmeter.sampler.coap.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import java.awt.Font;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class CoAPRequestUDPPanel extends JPanel {
	private JTextField txtHostname;
	private JTextField txtPort;
	private JCheckBox chkWaitResponse;
	private JCheckBox chkCloseSocket;
	private JLabel lblTimeout;
	private JTextField txtResponseTimeout;
	private JLabel lblWaitResponse;
	private JLabel lblCloseSocket;
	private JLabel lblUdpParameters;
	private JLabel lblIfSeparateResponse;
	private JCheckBox chkContinueSeparate;
	private JLabel lblPreprocessRequests;
	private JCheckBox chkPreProcess;
	private CoAPRequestPanel parentPanel;
	/**
	 * Create the panel.
	 * @param coAPRequestPanel 
	 */
	public CoAPRequestUDPPanel(CoAPRequestPanel coAPRequestPanel) {
		parentPanel = coAPRequestPanel;
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{230, 151, 0};
		gridBagLayout.rowHeights = new int[]{0, 28, 28, 23, 0, 23, 0, 28, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		lblUdpParameters = new JLabel("UDP");
		lblUdpParameters.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		GridBagConstraints gbc_lblUdpParameters = new GridBagConstraints();
		gbc_lblUdpParameters.anchor = GridBagConstraints.WEST;
		gbc_lblUdpParameters.insets = new Insets(5, 5, 5, 5);
		gbc_lblUdpParameters.gridx = 0;
		gbc_lblUdpParameters.gridy = 0;
		add(lblUdpParameters, gbc_lblUdpParameters);
		
		JLabel lblNewLabel = new JLabel("Hostname:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		add(lblNewLabel, gbc_lblNewLabel);
		
		txtHostname = new JTextField();
		GridBagConstraints gbc_txtHostname = new GridBagConstraints();
		gbc_txtHostname.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtHostname.insets = new Insets(0, 0, 5, 0);
		gbc_txtHostname.gridx = 1;
		gbc_txtHostname.gridy = 1;
		add(txtHostname, gbc_txtHostname);
		txtHostname.setColumns(10);
		
		JLabel lblPort = new JLabel("Port:");
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.anchor = GridBagConstraints.EAST;
		gbc_lblPort.insets = new Insets(0, 0, 5, 5);
		gbc_lblPort.gridx = 0;
		gbc_lblPort.gridy = 2;
		add(lblPort, gbc_lblPort);
		
		txtPort = new JTextField();
		GridBagConstraints gbc_txtPort = new GridBagConstraints();
		gbc_txtPort.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPort.insets = new Insets(0, 0, 5, 0);
		gbc_txtPort.gridx = 1;
		gbc_txtPort.gridy = 2;
		add(txtPort, gbc_txtPort);
		txtPort.setColumns(10);
		
		lblWaitResponse = new JLabel("Wait Response?");
		GridBagConstraints gbc_lblWaitResponse = new GridBagConstraints();
		gbc_lblWaitResponse.anchor = GridBagConstraints.EAST;
		gbc_lblWaitResponse.insets = new Insets(0, 0, 5, 5);
		gbc_lblWaitResponse.gridx = 0;
		gbc_lblWaitResponse.gridy = 3;
		add(lblWaitResponse, gbc_lblWaitResponse);
		
		chkWaitResponse = new JCheckBox("");
		GridBagConstraints gbc_chkWaitResponse = new GridBagConstraints();
		gbc_chkWaitResponse.anchor = GridBagConstraints.WEST;
		gbc_chkWaitResponse.insets = new Insets(0, 0, 5, 0);
		gbc_chkWaitResponse.gridx = 1;
		gbc_chkWaitResponse.gridy = 3;
		add(chkWaitResponse, gbc_chkWaitResponse);
		
		lblIfSeparateResponse = new JLabel("If Separate Response continue?");
		GridBagConstraints gbc_lblIfSeparateResponse = new GridBagConstraints();
		gbc_lblIfSeparateResponse.anchor = GridBagConstraints.EAST;
		gbc_lblIfSeparateResponse.insets = new Insets(0, 0, 5, 5);
		gbc_lblIfSeparateResponse.gridx = 0;
		gbc_lblIfSeparateResponse.gridy = 4;
		add(lblIfSeparateResponse, gbc_lblIfSeparateResponse);
		
		chkContinueSeparate = new JCheckBox("");
		GridBagConstraints gbc_chkContinueSeparate = new GridBagConstraints();
		gbc_chkContinueSeparate.anchor = GridBagConstraints.WEST;
		gbc_chkContinueSeparate.insets = new Insets(0, 0, 5, 0);
		gbc_chkContinueSeparate.gridx = 1;
		gbc_chkContinueSeparate.gridy = 4;
		add(chkContinueSeparate, gbc_chkContinueSeparate);
		
		lblCloseSocket = new JLabel("Close Socket?");
		GridBagConstraints gbc_lblCloseSocket = new GridBagConstraints();
		gbc_lblCloseSocket.anchor = GridBagConstraints.EAST;
		gbc_lblCloseSocket.insets = new Insets(0, 0, 5, 5);
		gbc_lblCloseSocket.gridx = 0;
		gbc_lblCloseSocket.gridy = 5;
		add(lblCloseSocket, gbc_lblCloseSocket);
		
		chkCloseSocket = new JCheckBox("");
		GridBagConstraints gbc_chkCloseSocket = new GridBagConstraints();
		gbc_chkCloseSocket.anchor = GridBagConstraints.WEST;
		gbc_chkCloseSocket.insets = new Insets(0, 0, 5, 0);
		gbc_chkCloseSocket.gridx = 1;
		gbc_chkCloseSocket.gridy = 5;
		add(chkCloseSocket, gbc_chkCloseSocket);
		
		lblTimeout = new JLabel("Response Timeout [ms]:");
		GridBagConstraints gbc_lblTimeout = new GridBagConstraints();
		gbc_lblTimeout.anchor = GridBagConstraints.EAST;
		gbc_lblTimeout.insets = new Insets(0, 5, 5, 5);
		gbc_lblTimeout.gridx = 0;
		gbc_lblTimeout.gridy = 6;
		add(lblTimeout, gbc_lblTimeout);
		
		txtResponseTimeout = new JTextField();
		GridBagConstraints gbc_txtResponseTimeout = new GridBagConstraints();
		gbc_txtResponseTimeout.insets = new Insets(0, 0, 5, 0);
		gbc_txtResponseTimeout.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtResponseTimeout.gridx = 1;
		gbc_txtResponseTimeout.gridy = 6;
		add(txtResponseTimeout, gbc_txtResponseTimeout);
		
		lblPreprocessRequests = new JLabel("Pre-Process requests?");
		GridBagConstraints gbc_lblPreprocessRequests = new GridBagConstraints();
		gbc_lblPreprocessRequests.anchor = GridBagConstraints.EAST;
		gbc_lblPreprocessRequests.insets = new Insets(0, 0, 0, 5);
		gbc_lblPreprocessRequests.gridx = 0;
		gbc_lblPreprocessRequests.gridy = 7;
		add(lblPreprocessRequests, gbc_lblPreprocessRequests);
		
		chkPreProcess = new JCheckBox("");
		chkPreProcess.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				parentPanel.setEnabledPanels(!chkPreProcess.isSelected());
			}
		});
		GridBagConstraints gbc_chkPreProcess = new GridBagConstraints();
		gbc_chkPreProcess.anchor = GridBagConstraints.WEST;
		gbc_chkPreProcess.gridx = 1;
		gbc_chkPreProcess.gridy = 7;
		add(chkPreProcess, gbc_chkPreProcess);
		
	}
	public String getTxtHostname() {
		return txtHostname.getText();
	}
	public void setTxtHostname(String value) {
		this.txtHostname.setText(value);
	}
	public String getTxtPort() {
		return txtPort.getText();
	}
	public void setTxtPort(String value) {
		this.txtPort.setText(value);
	}
	public boolean getChkWaitResponse() {
		return chkWaitResponse.isSelected();
	}
	public void setChkWaitResponse(boolean b) {
		this.chkWaitResponse.setSelected(b);
	}
	public boolean getChkContinueSeparate() {
		return chkContinueSeparate.isSelected();
	}
	public void setChkContinueSeparate(boolean b) {
		this.chkContinueSeparate.setSelected(b);
	}
	public boolean getChkCloseSocket() {
		return chkCloseSocket.isSelected();
	}
	public void setChkCloseSocket(boolean b) {
		this.chkCloseSocket.setSelected(b);
	}
	public String getTxtResponseTimeout() {
		return txtResponseTimeout.getText();
	}
	public void setTxtResponseTimeout(String value) {
		this.txtResponseTimeout.setText(value);
	}
	public boolean getChkPreProcess() {
		return chkPreProcess.isSelected();
	}
	public void setChkPreProcess(boolean b) {
		this.chkPreProcess.setSelected(b);
	}
}
