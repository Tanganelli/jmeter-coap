package it.unipi.jmeter.sampler.coap;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.elements.RawData;



/**
 * Example Sampler (non-Bean version)
 * 
 * JMeter creates an instance of a sampler class for every occurrence of the
 * element in every thread. [some additional copies may be created before the
 * test run starts]
 * 
 * Thus each sampler is guaranteed to be called by a single thread - there is no
 * need to synchronize access to instance variables.
 * 
 * However, access to class fields must be synchronized.
 * 
 * @version $Revision$ $Date$
 */

public class CoAPSampler extends AbstractSampler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggingManager.getLoggerForClass();

	public static final String HOSTNAME = "CoAPSampler.hostname";

	public static final String PORT = "CoAPSampler.port";

	public static final String RESPONSE_TIMEOUT = "CoAPSampler.response_timeout";

	public static final String CLOSE_SOCKET = "CoAPSampler.close_socket";

	public static final String WAIT_RESPONSE = "CoAPSampler.wait_response";
	
	private static final String CONTINUE_SEPARATE = "CoAPSampler.continue_separate";
	
	private static final String PRE_PROCESS = "CoAPSampler.pre_process";

	public static final String MID = "CoAPSampler.mid";

	public static final String PATH = "CoAPSampler.path";

	public static final String TOKEN = "CoAPSampler.token";

	public static final String CODE = "CoAPSampler.code";

	public static final String TYPE = "CoAPSampler.type";

	public static final String ACCEPT = "CoAPSampler.accept";

	public static final String CONTENT_FORMAT = "CoAPSampler.content_type";

	public static final String ETAG = "CoAPSampler.etag";

	public static final String IF_MATCH = "CoAPSampler.if_match";

	public static final String OBSERVE = "CoAPSampler.observe";

	public static final String PROXY_URI = "CoAPSampler.proxy_uri";

	public static final String URI_HOST = "CoAPSampler.uri_host";

	public static final String URI_PORT = "CoAPSampler.uri_port";

	public static final String IF_NONE_MATCH = "CoAPSampler.if_none_match";

	public static final String PROXY_SCHEMA = "CoAPSampler.proxy_schema";

	public static final String PAYLOAD = "CoAPSampler.payload";
	
	protected final static int recvBufSize = 1024 * 4;
	
	//private static BlockingQueue<String> outgoing = new LinkedBlockingQueue<String>();
	
	private ByteBufferSerializer encoder;

	private DatagramSocket socket;

	private static int classCount = 0; // keep track of classes created
	
	private static AtomicInteger requestId = new AtomicInteger(0); 
	// (for instructional purposes only!)

	public CoAPSampler() {
		classCount++;
		
		try {
			socket = new DatagramSocket(0, null);
			if(getResponseTimeoutAsInt() != 0)
				socket.setSoTimeout(this.getResponseTimeoutAsInt());
		} catch (SocketException e) {
			log.error("Cannot open channel", e);
			e.printStackTrace();
		}
		trace("CoAPSampler()");
	}
	
	/*public CoAPSampler(CoAPPreProcessor sampler) {
			
		this.setMid(sampler.getMid());
		this.setPath(sampler.getPath());
		this.setToken(sampler.getToken());
		try{
			this.setCode((sampler.getCode()).toString());
		}catch(NullPointerException e){
			this.setCode("");
		}
		try{
			this.setType( (sampler.getType()).toString());
		}catch(NullPointerException e){
			this.setType("");
		}
		
		try{
			this.setAccept(sampler.getAccept());
		}catch(NullPointerException e){
			this.setAccept("");
		}
		try{
			this.setContentFormat(sampler.getContentFormat());
		}catch(NullPointerException e){
			this.setContentFormat("");
		}
		
		this.setEtag(sampler.getEtag());
		this.setIfMatch(sampler.getIfMatch());
		this.setObserve(sampler.getObserve());
		this.setProxyUri(sampler.getProxyUri());
		this.setUriHost(sampler.getUriHost());
		this.setUriPort(sampler.getUriPort() );
		this.setIfNoneMatch(sampler.isIfNoneMatch());
		this.setProxySchema(sampler.isProxySchema());
		
		this.setPayload(sampler.getPayload());
	}*/

	/*
	 * (non-Javadoc) Performs the sample, and returns the result
	 * 
	 * @see org.apache.jmeter.samplers.Sampler#sample(org.apache.jmeter.samplers.Entry)
	 */
	public SampleResult sample(Entry e) {
		if(encoder == null)
			encoder = new ByteBufferSerializer();
		SampleResultCoAP res = new SampleResultCoAP();
		res.setDataType(SampleResult.BINARY);
        res.setSuccessful(true);
        res.setResponseCodeOK();
        
		res.setSampleLabel(getTitle());
		final JMeterVariables vars = JMeterContextService.getContext().getVariables();
		if(isPreProcess()){
			Request nextp = null;
			while(nextp==null){
				String request = "request" + requestId.getAndIncrement();
				nextp = (Request) vars.getObject(request);
				if(nextp==null && requestId.get() > Integer.parseInt(vars.get("num_requests")))
					requestId.set(0);
			}
			res.setSamplerData(nextp.toString());
			res.setRequest(nextp);
		}
		else{
			Request nextp = encoder.createRequest(this);
			res.setSamplerData(nextp.toString());
			res.setRequest(nextp);
		}
        
        
        try {
            res.setResponseData(processIO(res));
        } catch (Exception ex) {
            if (!(ex instanceof SocketTimeoutException)) {
                log.error(getPropertyAsString(CoAPSampler.HOSTNAME), ex);
            }
            res.setSuccessful(false);
            res.setResponseCode(Integer.toString(HttpURLConnection.HTTP_GATEWAY_TIMEOUT));
            res.setResponseMessage(ex.toString());
            res.setResponseData((ex.toString() + "<br>" + ex.getStackTrace()).getBytes());
        }

        return res;
	}

	private int getResponseTimeoutAsInt() {
		try{
			return Integer.parseInt(getResponseTimeout());
		}catch(NumberFormatException e){
			
		}
		return 0;
	}

	private byte[] processIO(SampleResultCoAP res) throws Exception {
		
		if(socket.isClosed())
		{
			socket = new DatagramSocket(0, null);
			if(getResponseTimeoutAsInt() != 0)
				socket.setSoTimeout(this.getResponseTimeoutAsInt());
		}
		DatagramPacket datagram = null;
		if(!isPreProcess()){
	        RawData raw = encoder.encode(res.getRequest());
	        datagram = new DatagramPacket(new byte[0], 0);
			datagram.setData(raw.getBytes());
			datagram.setAddress(raw.getAddress());
			datagram.setPort(raw.getPort());
		}
		else{
			datagram = new DatagramPacket(new byte[0], 0);
			datagram.setData(res.getRequest().getBytes());
			datagram.setAddress(InetAddress.getByName(getHostname()));
			datagram.setPort(Integer.parseInt(getPort()));
		}
		
		res.sampleStart();
		socket.send(datagram);

        if (!isWaitResponse()) {
            res.latencyEnd();
            res.sampleEnd();

            if (isCloseSocket()) {
            	socket.close();
            }

            return new byte[0];
        }
        
        boolean separate = true;
        try {
        	while(separate)
        	{
        		datagram = new DatagramPacket(new byte[recvBufSize], recvBufSize);
        		socket.receive(datagram);
        		res.latencyEnd();

        		if(isContinueSeparate())
        			separate = encoder.readResponse(datagram);
        		else
        			separate = false;
        	}
            res.sampleEnd();
            if (isCloseSocket()) {
            	socket.close();
            }
            byte[] response = encoder.decode(datagram);
            res.setBytes(datagram.getLength());
            return response;
        } catch (Exception ex) {
        	socket.close();
            throw ex;
        }
	}

	

	/**
	 * @return a string for the sampleResult Title
	 */
	private String getTitle() {
		return this.getName();
	}

	/*
	 * Helper method
	 */
	private void trace(String s) {
		String tl = getTitle();
		String tn = Thread.currentThread().getName();
		String th = this.toString();
		log.debug(tn + " (" + classCount + ") " + tl + " " + s + " " + th);
	}



	public String getHostname() {
		return getPropertyAsString(HOSTNAME);
	}

	public void setHostname(String text) {
		setProperty(HOSTNAME, text);
	}

	public String getPort() {
		return getPropertyAsString(PORT);
	}

	public void setPort(String text) {
		setProperty(PORT, text);
	}

	public String getResponseTimeout() {
		return getPropertyAsString(RESPONSE_TIMEOUT);
	}

	public void setResponseTimeout(String text) {
		setProperty(RESPONSE_TIMEOUT, text);
	}

	public boolean isCloseSocket() {
		return getPropertyAsBoolean(CLOSE_SOCKET);
	}

	public void setCloseSocket(boolean b) {
		setProperty(CLOSE_SOCKET, b);
	}

	public boolean isWaitResponse() {
		return getPropertyAsBoolean(WAIT_RESPONSE);
	}

	public void setWaitResponse(boolean b) {
		setProperty(WAIT_RESPONSE, b);
	}
	
	public boolean isContinueSeparate() {
		return getPropertyAsBoolean(CONTINUE_SEPARATE);
	}

	public void setContinueSeparate(boolean b) {
		setProperty(CONTINUE_SEPARATE, b);
		
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
	
	public boolean isPreProcess() {
		return getPropertyAsBoolean(PRE_PROCESS);
	}
	
	public void setPreProcess(boolean b) {
		setProperty(PRE_PROCESS, b);
		
	}

}