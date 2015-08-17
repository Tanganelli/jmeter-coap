package it.unipi.jmeter.modifier.coap.gui;

import javax.swing.JPanel;
import javax.swing.BoxLayout;

public class CoAPPreProcessorPanel extends JPanel {

	private JPanel messagePanel;
	private JPanel optionPanel;
	private JPanel payloadPanel;
	/**
	 * Create the panel.
	 */
	public CoAPPreProcessorPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		messagePanel = new CoAPPreProcessorMessagePanel();
		add(messagePanel);
		
		optionPanel = new CoAPPreProcessorOptionPanel();
		add(optionPanel);
		
		payloadPanel = new CoAPPreProcessorPayloadPanel();
		add(payloadPanel);

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


}
