package it.unipi.jmeter.sampler.coap.gui;

import javax.swing.JPanel;
import javax.swing.BoxLayout;

public class CoAPRequestPanel extends JPanel {
	
	private JPanel udpPanel;
	private JPanel messagePanel;
	private JPanel optionPanel;
	private JPanel payloadPanel;

	/**
	 * Create the panel.
	 */
	public CoAPRequestPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		udpPanel = new CoAPRequestUDPPanel(this);
		add(udpPanel);
		
		messagePanel = new CoAPRequestMessagePanel();
		add(messagePanel);
		
		optionPanel = new CoAPRequestOptionPanel();
		add(optionPanel);
		
		payloadPanel = new CoAPRequestPayloadPanel();
		add(payloadPanel);

	}

	public JPanel getUdpPanel() {
		return udpPanel;
	}

	public void setUdpPanel(JPanel udpPanel) {
		this.udpPanel = udpPanel;
	}

	public JPanel getMessagePanel() {
		return messagePanel;
	}

	public void setMessagePanel(JPanel messagePanel) {
		this.messagePanel = messagePanel;
	}

	public JPanel getOptionPanel() {
		return optionPanel;
	}

	public void setOptionPanel(JPanel optionPanel) {
		this.optionPanel = optionPanel;
	}

	public JPanel getPayloadPanel() {
		return payloadPanel;
	}

	public void setPayloadPanel(JPanel payloadPanel) {
		this.payloadPanel = payloadPanel;
	}
	
	public void setEnabledPanels(boolean enabled){
		messagePanel.setEnabled(enabled);
		optionPanel.setEnabled(enabled);
		payloadPanel.setEnabled(enabled);
	}
}
