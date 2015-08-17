package it.unipi.jmeter.modifier.coap;

import it.unipi.jmeter.sampler.coap.ByteBufferSerializer;
import it.unipi.jmeter.sampler.coap.CoAPSampler;

import java.io.IOException;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.jmeter.engine.util.NoThreadClone;
import org.apache.jmeter.processor.PreProcessor;
import org.apache.jmeter.testelement.AbstractTestElement;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.jorphan.util.JOrphanUtils;
import org.apache.log.Logger;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.Option;
import org.eclipse.californium.core.coap.OptionNumberRegistry;
import org.eclipse.californium.core.coap.OptionSet;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.coap.CoAP.Code;
import org.eclipse.californium.core.coap.CoAP.Type;
import org.eclipse.californium.core.network.serialization.DataSerializer;
import org.eclipse.californium.core.network.serialization.Serializer;
import org.eclipse.californium.elements.RawData;

public class CoAPPreProcessor 	extends AbstractTestElement
								implements PreProcessor, NoThreadClone, TestStateListener {

	private static final Logger log = LoggingManager.getLoggerForClass();
	
	private static final String NUM_REQUESTS = "CoAPPreProcessor.num_requests";

	public static final String MID = "CoAPPreProcessor.mid";

	public static final String PATH = "CoAPPreProcessor.path";

	public static final String TOKEN = "CoAPPreProcessor.token";

	public static final String CODE = "CoAPPreProcessor.code";

	public static final String TYPE = "CoAPPreProcessor.type";

	public static final String ACCEPT = "CoAPPreProcessor.accept";

	public static final String CONTENT_FORMAT = "CoAPPreProcessor.content_type";

	public static final String ETAG = "CoAPPreProcessor.etag";

	public static final String IF_MATCH = "CoAPPreProcessor.if_match";

	public static final String OBSERVE = "CoAPPreProcessor.observe";

	public static final String PROXY_URI = "CoAPPreProcessor.proxy_uri";

	public static final String URI_HOST = "CoAPPreProcessor.uri_host";

	public static final String URI_PORT = "CoAPPreProcessor.uri_port";

	public static final String IF_NONE_MATCH = "CoAPPreProcessor.if_none_match";

	public static final String PROXY_SCHEMA = "CoAPPreProcessor.proxy_schema";

	public static final String PAYLOAD = "CoAPPreProcessor.payload";
	
	public CoAPPreProcessor() {
        super();
    }
	
	@Override
	public void testEnded() {

	}

	@Override
	public void testEnded(String arg0) {

	}

	@Override
	public void testStarted() {

	}

	@Override
	public void testStarted(String arg0) {

	}

	@Override
	public void process() {
		List<Request> requests = new ArrayList<Request>();
		//int capacity = 0;
		int num_requests = Integer.parseInt(getNumberOfRequests());
		final JMeterVariables vars = JMeterContextService.getContext().getVariables();
		vars.put("num_requests", String.valueOf(num_requests));
		for(int i=0; i<num_requests; i++)
		{
			Request request = new Request(getMethod(getCode()), getType(getType()));
			request.setMID(this.nextMid());
			request.setToken(this.nextToken());
			OptionSet options = getOptions();
	    	request.setOptions(options);
	    	request.setPayload(getPayload());
	    	byte[] bytes = new DataSerializer().serializeRequest(request);
	    	request.setBytes(bytes);
			requests.add(request);
			 if (vars != null) {
	            vars.putObject("request" + i, request);
	        }
			//capacity += request.getBytes().length;
		}

		/*ByteBuffer buf = ByteBuffer.allocateDirect(capacity);
		byte[] dst = new byte[capacity];
		
		for(RawData r : requests)
			buf.put(r.getBytes());
		
        buf.flip();
        buf.get(dst);
        String data = JOrphanUtils.baToHexString(dst);*/
        
       
		
	}
	
	private OptionSet getOptions(){
    	OptionSet options = new OptionSet();
    	if(!getPath().equals("")){
    		Option op = new Option(OptionNumberRegistry.URI_PATH, getPath());
    		options.addOption(op);
    	}
    	if(!getAccept().equals("")){
    		Option op = new Option(OptionNumberRegistry.ACCEPT, MediaTypeRegistry.parse(getAccept()));
    		options.addOption(op);
    	}
    	if(!getContentFormat().equals("")){
    		Option op = new Option(OptionNumberRegistry.CONTENT_FORMAT, MediaTypeRegistry.parse(getContentFormat()));
    		options.addOption(op);
    	}
    	if(!getObserve().equals("")){
    		try{
    			Option op = new Option(OptionNumberRegistry.OBSERVE, Integer.parseInt(getObserve()));
    			options.addOption(op);
    		}catch(NumberFormatException e){}
    	}
    	if(!getEtag().equals("")){
    		Option op = new Option(OptionNumberRegistry.ETAG, getEtag());
    		options.addOption(op);
    	}
    	if(!getIfMatch().equals("")){
    		log.error("If-Match: "+ getIfMatch());
    		Option op = new Option(OptionNumberRegistry.IF_MATCH, getIfMatch());
    		options.addOption(op);
    	}
    	if(isIfNoneMatch()){
    		Option op = new Option(OptionNumberRegistry.IF_NONE_MATCH);
    		options.addOption(op);
    	}
    	if(!getUriHost().equals("")){
    		Option op = new Option(OptionNumberRegistry.URI_HOST, getUriHost());
    		options.addOption(op);
    	}
    	if(!getUriPort().equals("")){
    		try{
    		Option op = new Option(OptionNumberRegistry.URI_PORT, Integer.parseInt(getUriPort()));
    		options.addOption(op);
    		}catch(NumberFormatException e){}
    	}
    	if(!getProxyUri().equals("")){
    		Option op = new Option(OptionNumberRegistry.PROXY_URI, getProxyUri());
    		options.addOption(op);
    	}
    	if(isProxySchema()){
    		Option op = new Option(OptionNumberRegistry.PROXY_SCHEME);
    		options.addOption(op);
    	}
    	
		return options;
    	
    }
	private Code getMethod(String code) {
		if(code.equalsIgnoreCase(Code.GET.toString()))
			return Code.GET;
		if(code.equalsIgnoreCase(Code.POST.toString()))
			return Code.POST;
		if(code.equalsIgnoreCase(Code.PUT.toString()))
			return Code.PUT;
		if(code.equalsIgnoreCase(Code.DELETE.toString()))
			return Code.DELETE;
		return null;
	}

	private Type getType(String type) {
		if(type.equalsIgnoreCase(Type.CON.toString()))
			return Type.CON;
		if(type.equalsIgnoreCase(Type.NON.toString()))
			return Type.NON;
		if(type.equalsIgnoreCase(Type.ACK.toString()))
			return Type.ACK;
		else
			return Type.RST;
		
	}
	
	private byte[] nextToken() {
		//TODO
		if(!getToken().equals("")){
			String token = getToken();
			int mid =  (Integer.parseInt(token)+1) % 65535;
			setToken(String.valueOf(mid));
			return String.valueOf(mid).getBytes();
		}
		return new byte[0];
	}

	private int nextMid() {
		if(!getMid().equals("")){
			int mid =  (Integer.parseInt(getMid())+1) % 65535;
			setMid(String.valueOf(mid));
			return mid;
		}
		return 0;
	}

	public String getNumberOfRequests() {
		return getPropertyAsString(NUM_REQUESTS);
	}

	public void setNumberOfRequests(String text) {
		setProperty(NUM_REQUESTS, text);
	}

	public String getMid() {
		return getPropertyAsString(MID);
	}

	public void setMid(String text) {
		setProperty(MID, text);
	}

	public String getPath() {
		return getPropertyAsString(PATH);
	}

	public void setPath(String text) {
		setProperty(PATH, text);
	}

	public String getToken() {
		return getPropertyAsString(TOKEN);
	}

	public void setToken(String text) {
		setProperty(TOKEN, text);
	}

	public String getCode() {
		return getPropertyAsString(CODE);
	}

	public void setCode(String text) {
		setProperty(CODE, text);
	}

	public String getType() {
		return getPropertyAsString(TYPE);
	}

	public void setType(String text) {
		setProperty(TYPE, text);
	}

	public String getAccept() {
		return getPropertyAsString(ACCEPT);
	}

	public void setAccept(String text) {
		setProperty(ACCEPT, text);
	}

	public String getContentFormat() {
		return getPropertyAsString(CONTENT_FORMAT);
	}

	public void setContentFormat(String text) {
		setProperty(CONTENT_FORMAT, text);
	}

	public String getEtag() {
		return getPropertyAsString(ETAG);
	}

	public void setEtag(String text) {
		setProperty(ETAG, text);
	}

	public String getIfMatch() {
		return getPropertyAsString(IF_MATCH);
	}

	public void setIfMatch(String text) {
		setProperty(IF_MATCH, text);
	}

	public String getObserve() {
		return getPropertyAsString(OBSERVE);
	}

	public void setObserve(String text) {
		setProperty(OBSERVE, text);
	}

	public String getProxyUri() {
		return getPropertyAsString(PROXY_URI);
	}

	public void setProxyUri(String text) {
		setProperty(PROXY_URI, text);
	}

	public String getUriHost() {
		return getPropertyAsString(URI_HOST);
	}

	public void setUriHost(String text) {
		setProperty(URI_HOST, text);
	}

	public String getUriPort() {
		return getPropertyAsString(URI_PORT);
	}

	public void setUriPort(String text) {
		setProperty(URI_PORT, text);
	}

	public boolean isIfNoneMatch() {
		return getPropertyAsBoolean(IF_NONE_MATCH);
	}

	public void setIfNoneMatch(boolean b) {
		setProperty(IF_NONE_MATCH, b);
	}

	public boolean isProxySchema() {
		return getPropertyAsBoolean(PROXY_SCHEMA);
	}

	public void setProxySchema(boolean b) {
		setProperty(PROXY_SCHEMA, b);
	}

	public String getPayload() {
		return getPropertyAsString(PAYLOAD);
	}
	
	public void setPayload(String text) {
		setProperty(PAYLOAD, text);
	}

}
