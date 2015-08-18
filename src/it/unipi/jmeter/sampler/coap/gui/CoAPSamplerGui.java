package it.unipi.jmeter.sampler.coap.gui;


import it.unipi.jmeter.sampler.coap.CoAPSampler;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import org.eclipse.californium.core.coap.CoAP.Code;
import org.eclipse.californium.core.coap.CoAP.Type;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

/**
 * Example Sampler (non-Bean version)
 * 
 * This class is responsible for ensuring that the Sampler data is kept in step
 * with the GUI.
 * 
 * The GUI class is not invoked in non-GUI mode, so it should not perform any
 * additional setup that a test would need at run-time
 * 
 */
public class CoAPSamplerGui extends AbstractSamplerGui {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggingManager.getLoggerForClass();
	private CoAPRequestPanel dataPanel;

	public CoAPSamplerGui() {
		init();
	}

	@Override
    public String getStaticLabel() {
        return "CoAP Request";
    }

   @Override
    public String getLabelResource() {
        return getClass().getCanonicalName();
    }

	/*
	 * (non-Javadoc) Copy the data from the test element to the GUI
	 * 
	 * @see org.apache.jmeter.gui.JMeterGUIComponent#configure(org.apache.jmeter.testelement.TestElement)
	 */
	public void configure(TestElement element) {
		log.info("configure");
		CoAPRequestUDPPanel udpPanel = (CoAPRequestUDPPanel) dataPanel.getUdpPanel();
		CoAPRequestMessagePanel messagePanel = (CoAPRequestMessagePanel) dataPanel.getMessagePanel();
		CoAPRequestOptionPanel optionPanel = (CoAPRequestOptionPanel) dataPanel.getOptionPanel();
		CoAPRequestPayloadPanel payloadPanel = (CoAPRequestPayloadPanel) dataPanel.getPayloadPanel();
		
		CoAPSampler sampler = (CoAPSampler) element;
		
		udpPanel.setTxtHostname(sampler.getHostname());
		udpPanel.setTxtPort(sampler.getPort());
		udpPanel.setTxtResponseTimeout(sampler.getResponseTimeout());
		udpPanel.setChkCloseSocket(sampler.isCloseSocket());
		udpPanel.setChkWaitResponse(sampler.isWaitResponse());
		udpPanel.setChkContinueSeparate(sampler.isContinueSeparate());
		udpPanel.setChkPreProcess(sampler.isPreProcess());
		if(!sampler.isPreProcess()){
			messagePanel.setEnabled(true);
			optionPanel.setEnabled(true);
			payloadPanel.setEnabled(true);
			
			messagePanel.setTxtMid(sampler.getMid());
			messagePanel.setTxtPath(sampler.getPath());
			messagePanel.setTxtToken(sampler.getToken());
			try{
				messagePanel.setCmbCode(Code.valueOf(sampler.getCode()));
			}catch(IllegalArgumentException e){}
			try{
				messagePanel.setCmbType(Type.valueOf(sampler.getType()));
			}catch(IllegalArgumentException e){}
			
			try{
				optionPanel.setCmbAccept(sampler.getAccept());
			}catch(IllegalArgumentException e){}
			try{
				optionPanel.setCmbContentFormat(sampler.getContentFormat());
			}catch(IllegalArgumentException e){}
			optionPanel.setTxtETag(sampler.getEtag());
			optionPanel.setTxtIfMatch(sampler.getIfMatch());
			optionPanel.setTxtObserve(sampler.getObserve());
			optionPanel.setTxtProxyUri(sampler.getProxyUri());
			optionPanel.setTxtUriHost(sampler.getUriHost());
			optionPanel.setTxtUriPort(sampler.getUriPort());
			optionPanel.setChkIfNoneMatch(sampler.isIfNoneMatch());
			optionPanel.setChkProxySchema(sampler.isProxySchema());
			
			payloadPanel.setTxaPayload(sampler.getPayload());
		}
		else{
			messagePanel.setEnabled(false);
			optionPanel.setEnabled(false);
			payloadPanel.setEnabled(false);
		}
		super.configure(element); 
	}

	/*
	 * (non-Javadoc) Create the corresponding Test Element and set up its data
	 * 
	 * @see org.apache.jmeter.gui.JMeterGUIComponent#createTestElement()
	 */
	public TestElement createTestElement() {
		CoAPSampler sampler = new CoAPSampler();
		modifyTestElement(sampler);
		return sampler;
	}

	/*
	 * (non-Javadoc) Modifies a given TestElement to mirror the data in the gui
	 * components.
	 * 
	 * @see org.apache.jmeter.gui.JMeterGUIComponent#modifyTestElement(TestElement)
	 */
	public void modifyTestElement(TestElement te) {
		log.info("modifyTestElement");
		te.clear();
		configureTestElement(te);
		CoAPSampler sampler = (CoAPSampler) te;
		
		CoAPRequestUDPPanel udpPanel = (CoAPRequestUDPPanel) dataPanel.getUdpPanel();
		CoAPRequestMessagePanel messagePanel = (CoAPRequestMessagePanel) dataPanel.getMessagePanel();
		CoAPRequestOptionPanel optionPanel = (CoAPRequestOptionPanel) dataPanel.getOptionPanel();
		CoAPRequestPayloadPanel payloadPanel = (CoAPRequestPayloadPanel) dataPanel.getPayloadPanel();

		sampler.setHostname(udpPanel.getTxtHostname());
		sampler.setPort(udpPanel.getTxtPort());
		sampler.setResponseTimeout( udpPanel.getTxtResponseTimeout());
		sampler.setCloseSocket( udpPanel.getChkCloseSocket());
		sampler.setWaitResponse( udpPanel.getChkWaitResponse());
		sampler.setContinueSeparate( udpPanel.getChkContinueSeparate());
		sampler.setPreProcess(udpPanel.getChkPreProcess());
		
		if(!sampler.isPreProcess()){
		
			sampler.setMid( messagePanel.getTxtMid());
			sampler.setPath( messagePanel.getTxtPath());
			sampler.setToken( messagePanel.getTxtToken());
			try{
				sampler.setCode(((Code) messagePanel.getCmbCode()).toString());
			}catch(NullPointerException e){
				sampler.setCode("");
			}
			try{
				sampler.setType( ((Type) messagePanel.getCmbType()).toString());
			}catch(NullPointerException e){
				sampler.setType("");
			}
			
			try{
				sampler.setAccept( (String) optionPanel.getCmbAccept());
			}catch(NullPointerException e){
				sampler.setAccept("");
			}
			try{
				sampler.setContentFormat( (String) optionPanel.getCmbContentFormat());
			}catch(NullPointerException e){
				sampler.setContentFormat("");
			}
			
			sampler.setEtag( optionPanel.getTxtETag());
			sampler.setIfMatch( optionPanel.getTxtIfMatch());
			sampler.setObserve( optionPanel.getTxtObserve());
			sampler.setProxyUri( optionPanel.getTxtProxyUri());
			sampler.setUriHost( optionPanel.getTxtUriHost());
			sampler.setUriPort( optionPanel.getTxtUriPort());
			sampler.setIfNoneMatch( optionPanel.getChkIfNoneMatch());
			sampler.setProxySchema( optionPanel.getChkProxySchema());
			
			sampler.setPayload( payloadPanel.getTxaPayload());
		}
		else{
			//TODO get variable
			
		}
		
	}
	

	/*
	 * Helper method to set up the GUI screen
	 */
	private void init() {
		// Standard setup
		setLayout(new BorderLayout(0, 5));
		setBorder(makeBorder());
		add(makeTitlePanel(), BorderLayout.NORTH); // Add the standard title

		// Specific setup
		add(createDataPanel(), BorderLayout.CENTER);
		setCombos();
	}

	/*
	 * Create a data input text field
	 * 
	 * @return the panel for entering the data
	 */
	private Component createDataPanel() {
		dataPanel = new CoAPRequestPanel();
		return dataPanel;
	}
	
	private void setCombos(){
		log.info("setCombos");
		CoAPRequestMessagePanel messagePanel = (CoAPRequestMessagePanel) dataPanel.getMessagePanel();
		List<Type> types = new ArrayList<Type>();
		types.add(Type.CON);
		types.add(Type.NON);
		types.add(Type.ACK);
		types.add(Type.RST);
		messagePanel.initCmbType(types);
		List<Code> codes = new ArrayList<Code>();
		codes.add(Code.GET);
		codes.add(Code.POST);
		codes.add(Code.PUT);
		codes.add(Code.DELETE);
		messagePanel.initCmbCode(codes);
		Set<Integer> registry = MediaTypeRegistry.getAllMediaTypes();
		List<String> media_types = new ArrayList<String>();
		media_types.add("");
		for(Integer i : registry)
			media_types.add(MediaTypeRegistry.toString(i));
		CoAPRequestOptionPanel optionPanel = (CoAPRequestOptionPanel) dataPanel.getOptionPanel();
		optionPanel.initCmbAccept(media_types);
		optionPanel.initCmbContentFormat(media_types);
	}

	public void clearGui() {
		log.info("clearGui");
		super.clearGui();
		
		CoAPRequestUDPPanel udpPanel = (CoAPRequestUDPPanel) dataPanel.getUdpPanel();
		CoAPRequestMessagePanel messagePanel = (CoAPRequestMessagePanel) dataPanel.getMessagePanel();
		CoAPRequestOptionPanel optionPanel = (CoAPRequestOptionPanel) dataPanel.getOptionPanel();
		CoAPRequestPayloadPanel payloadPanel = (CoAPRequestPayloadPanel) dataPanel.getPayloadPanel();
		
		udpPanel.setTxtHostname("");
		udpPanel.setTxtPort("");
		udpPanel.setTxtResponseTimeout("");
		udpPanel.setChkCloseSocket(false);
		udpPanel.setChkWaitResponse(false);
		udpPanel.setChkContinueSeparate(false);
		udpPanel.setChkPreProcess(false);
		
		messagePanel.setEnabled(true);
		optionPanel.setEnabled(true);
		payloadPanel.setEnabled(true);
		
		messagePanel.setTxtMid("");
		messagePanel.setTxtPath("");
		messagePanel.setTxtToken("");
		try{
			messagePanel.setCmbCode(Code.GET);
		}catch(IllegalArgumentException e){}
		try{
			messagePanel.setCmbType(Type.CON);
		}catch(IllegalArgumentException e){}
		
		try{
			optionPanel.setCmbAccept("");
		}catch(IllegalArgumentException e){}
		try{
			optionPanel.setCmbContentFormat("");
		}catch(IllegalArgumentException e){}
		optionPanel.setTxtETag("");
		optionPanel.setTxtIfMatch("");
		optionPanel.setTxtObserve("");
		optionPanel.setTxtProxyUri("");
		optionPanel.setTxtUriHost("");
		optionPanel.setTxtUriPort("");
		optionPanel.setChkIfNoneMatch(false);
		optionPanel.setChkProxySchema(false);
		
		payloadPanel.setTxaPayload("");
		
	}

}