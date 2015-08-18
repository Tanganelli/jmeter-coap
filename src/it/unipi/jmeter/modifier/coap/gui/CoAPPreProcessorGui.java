package it.unipi.jmeter.modifier.coap.gui;


import it.unipi.jmeter.modifier.coap.CoAPPreProcessor;
import java.awt.BorderLayout;
import java.awt.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.jmeter.processor.gui.AbstractPreProcessorGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.CoAP.Code;
import org.eclipse.californium.core.coap.CoAP.Type;

public class CoAPPreProcessorGui extends AbstractPreProcessorGui {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggingManager.getLoggerForClass();
	private CoAPPreProcessorPanel dataPanel;

    public CoAPPreProcessorGui() {
        super();
        init();
    }

    @Override
    public String getStaticLabel() {
        return "CoAP PreProcessor";
    }

    @Override
    public String getLabelResource() {
        return getClass().getCanonicalName();
    }

    @Override
    public void configure(TestElement element) {
        super.configure(element);
        log.info("configure");

		CoAPPreProcessorMessagePanel messagePanel = (CoAPPreProcessorMessagePanel) dataPanel.getMessagePanel();
		CoAPPreProcessorOptionPanel optionPanel = (CoAPPreProcessorOptionPanel) dataPanel.getOptionPanel();
		CoAPPreProcessorPayloadPanel payloadPanel = (CoAPPreProcessorPayloadPanel) dataPanel.getPayloadPanel();
		
		CoAPPreProcessor sampler = (CoAPPreProcessor) element;
		
		messagePanel.setTxtNumRequests(sampler.getNumberOfRequests());
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

    @Override
    public TestElement createTestElement() {
        CoAPPreProcessor preproc = new CoAPPreProcessor();
        modifyTestElement(preproc);
        return preproc;
    }

    @Override
    public void modifyTestElement(TestElement te) {
    	te.clear();
		configureTestElement(te);
		CoAPPreProcessor sampler = (CoAPPreProcessor) te;

		CoAPPreProcessorMessagePanel messagePanel = (CoAPPreProcessorMessagePanel) dataPanel.getMessagePanel();
		CoAPPreProcessorOptionPanel optionPanel = (CoAPPreProcessorOptionPanel) dataPanel.getOptionPanel();
		CoAPPreProcessorPayloadPanel payloadPanel = (CoAPPreProcessorPayloadPanel) dataPanel.getPayloadPanel();
		
		sampler.setNumberOfRequests(messagePanel.getTxtNumRequests());
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
        configureTestElement(te);
        
    }

    @Override
    public void clearGui() {
    	log.info("clearGui");
		super.clearGui();
		
		CoAPPreProcessorMessagePanel messagePanel = (CoAPPreProcessorMessagePanel) dataPanel.getMessagePanel();
		CoAPPreProcessorOptionPanel optionPanel = (CoAPPreProcessorOptionPanel) dataPanel.getOptionPanel();
		CoAPPreProcessorPayloadPanel payloadPanel = (CoAPPreProcessorPayloadPanel) dataPanel.getPayloadPanel();
		
		messagePanel.setTxtNumRequests("");
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

    private void init() {
    	// Standard setup
		setLayout(new BorderLayout(0, 5));
		setBorder(makeBorder());
		add(makeTitlePanel(), BorderLayout.NORTH); // Add the standard title

		// Specific setup
		add(createDataPanel(), BorderLayout.CENTER);
		setCombos();
    }
    
    private Component createDataPanel() {
		dataPanel = new CoAPPreProcessorPanel();
		return dataPanel;
	}
	
	private void setCombos(){
		log.info("setCombos");
		CoAPPreProcessorMessagePanel messagePanel = (CoAPPreProcessorMessagePanel) dataPanel.getMessagePanel();
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
		CoAPPreProcessorOptionPanel optionPanel = (CoAPPreProcessorOptionPanel) dataPanel.getOptionPanel();
		optionPanel.initCmbAccept(media_types);
		optionPanel.initCmbContentFormat(media_types);
	}
    
}
