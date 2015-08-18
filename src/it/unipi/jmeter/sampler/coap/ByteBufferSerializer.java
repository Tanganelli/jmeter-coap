package it.unipi.jmeter.sampler.coap;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import org.eclipse.californium.core.coap.CoAP.Code;
import org.eclipse.californium.core.coap.CoAP.Type;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.Option;
import org.eclipse.californium.core.coap.OptionNumberRegistry;
import org.eclipse.californium.core.coap.OptionSet;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.network.serialization.DataParser;
import org.eclipse.californium.core.network.serialization.Serializer;
import org.eclipse.californium.elements.RawData;

public class ByteBufferSerializer {
	private static final Logger log = LoggingManager.getLoggerForClass();
	
	public RawData encode(Request request){
		Serializer serializer = new Serializer();
    	RawData data = serializer.serialize(request);
		return data;
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
	
	private OptionSet getOptions(CoAPSampler sampler){
    	OptionSet options = new OptionSet();
    	if(!sampler.getPath().equals("")){
    		Option op = new Option(OptionNumberRegistry.URI_PATH, sampler.getPath());
    		options.addOption(op);
    	}
    	if(!sampler.getAccept().equals("")){
    		Option op = new Option(OptionNumberRegistry.ACCEPT, MediaTypeRegistry.parse(sampler.getAccept()));
    		options.addOption(op);
    	}
    	if(!sampler.getContentFormat().equals("")){
    		Option op = new Option(OptionNumberRegistry.CONTENT_FORMAT, MediaTypeRegistry.parse(sampler.getContentFormat()));
    		options.addOption(op);
    	}
    	if(!sampler.getObserve().equals("")){
    		try{
    			Option op = new Option(OptionNumberRegistry.OBSERVE, Integer.parseInt(sampler.getObserve()));
    			options.addOption(op);
    		}catch(NumberFormatException e){}
    	}
    	if(!sampler.getEtag().equals("")){
    		Option op = new Option(OptionNumberRegistry.ETAG, sampler.getEtag());
    		options.addOption(op);
    	}
    	if(!sampler.getIfMatch().equals("")){
    		Option op = new Option(OptionNumberRegistry.IF_MATCH, sampler.getIfMatch());
    		options.addOption(op);
    	}
    	if(sampler.isIfNoneMatch()){
    		Option op = new Option(OptionNumberRegistry.IF_NONE_MATCH);
    		options.addOption(op);
    	}
    	if(!sampler.getUriHost().equals("")){
    		Option op = new Option(OptionNumberRegistry.URI_HOST, sampler.getUriHost());
    		options.addOption(op);
    	}
    	if(!sampler.getUriPort().equals("")){
    		try{
    		Option op = new Option(OptionNumberRegistry.URI_PORT, Integer.parseInt(sampler.getUriPort()));
    		options.addOption(op);
    		}catch(NumberFormatException e){}
    	}
    	if(!sampler.getProxyUri().equals("")){
    		Option op = new Option(OptionNumberRegistry.PROXY_URI, sampler.getProxyUri());
    		options.addOption(op);
    	}
    	if(sampler.isProxySchema()){
    		Option op = new Option(OptionNumberRegistry.PROXY_SCHEME);
    		options.addOption(op);
    	}
    	
		return options;
    	
    }

	public byte[] decode(DatagramPacket datagram) {
		return Arrays.copyOf(datagram.getData(), datagram.getLength());
	}

	public Request createRequest(CoAPSampler sampler) {
		Request request = new Request(getMethod(sampler.getCode()), getType(sampler.getType()));
    	OptionSet options = getOptions(sampler);
    	request.setOptions(options);
    	request.setMID(Integer.parseInt(sampler.getMid()));
    	request.setToken(sampler.getToken().getBytes());
    	request.setPayload(sampler.getPayload());
    	try {
			request.setDestination(InetAddress.getByName(sampler.getHostname()));
		} catch (UnknownHostException e) {
			log.error("Destination Ip is not valid", e);
		}
    	request.setDestinationPort(Integer.parseInt(sampler.getPort()));
    	return request;
	}

	public boolean readResponse(DatagramPacket datagram) {
		DataParser deserializer = new DataParser(datagram.getData());
		if(deserializer.isReply() && deserializer.isEmpty())
			return true;
		return false;
	}

}
