package it.unipi.jmeter.assertion.coap;

import org.apache.jmeter.assertions.AssertionResult;
import org.apache.jmeter.assertions.ResponseAssertion;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.property.PropertyIterator;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import org.apache.oro.text.MalformedCachePatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.CoAP.Type;
import org.eclipse.californium.core.coap.EmptyMessage;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.core.network.serialization.DataParser;
import org.eclipse.californium.core.coap.Message;

public class CoAPResponseAssertion extends ResponseAssertion{

	/*
	 * Mask values for TEST_TYPE TODO: remove either MATCH or CONTAINS - they
	 * are mutually exckusive
	 */
	private final static int MATCH = 1 << 0;

	final static int CONTAINS = 1 << 1;

	private final static int NOT = 1 << 2;

	private final static int EQUALS = 1 << 3;

    private static final int  EQUALS_SECTION_DIFF_LEN
            = JMeterUtils.getPropDefault("assertion.equals_section_diff_len", 100);

    /** Signifies truncated text in diff display. */
    private static final String EQUALS_DIFF_TRUNC = "...";

    private static final String RECEIVED_STR = "****** received  : ";
    private static final String COMPARISON_STR = "****** comparison: ";
    private static final String DIFF_DELTA_START
            = JMeterUtils.getPropDefault("assertion.equals_diff_delta_start", "[[[");
    private static final String DIFF_DELTA_END
            = JMeterUtils.getPropDefault("assertion.equals_diff_delta_end", "]]]");
    
	private static final Logger log = LoggingManager.getLoggerForClass();

	public static final String MID = "CoAPResponseAssertion.mid";

	public static final String PATH = "CoAPResponseAssertion.path";

	public static final String TOKEN = "CoAPResponseAssertion.token";

	public static final String RESPONSE_CODE = "CoAPResponseAssertion.response_code";

	public static final String TYPE = "CoAPResponseAssertion.type";

	public static final String ACCEPT = "CoAPResponseAssertion.accept";

	public static final String CONTENT_FORMAT = "CoAPResponseAssertion.content_type";

	public static final String ETAG = "CoAPResponseAssertion.etag";

	public static final String IF_MATCH = "CoAPResponseAssertion.if_match";

	public static final String OBSERVE = "CoAPResponseAssertion.observe";

	public static final String PROXY_URI = "CoAPResponseAssertion.proxy_uri";

	public static final String URI_HOST = "CoAPResponseAssertion.uri_host";

	public static final String URI_PORT = "CoAPResponseAssertion.uri_port";

	public static final String IF_NONE_MATCH = "CoAPResponseAssertion.if_none_match";

	public static final String PROXY_SCHEMA = "CoAPResponseAssertion.proxy_schema";
	
	public String getMid() {
		return getPropertyAsString(MID);
	}
	public void setMid(String mid) {
		setProperty(MID, mid);
	}
	public String getToken() {
		return getPropertyAsString(TOKEN);
	}
	public void setToken(String token) {
		setProperty(TOKEN, token);
	}
	public Type getType() {
		try{
			return Type.valueOf(getPropertyAsString(TYPE));
		}catch(Exception e){
			return null;
		}
	}
	public void setType(String type) {
		if(type==null)
			setProperty(TYPE, null);
		else
			setProperty(TYPE, type);
	}
	public String getResponseCode() {
		return getPropertyAsString(RESPONSE_CODE);
	}
	public void setResponseCode(String string) {
		setProperty(RESPONSE_CODE, string);
	}
	public String getETag() {
		return getPropertyAsString(ETAG);
	}
	public void setETag(String eTag) {
		setProperty(ETAG, eTag);
	}
	public String getIfMatch() {
		return getPropertyAsString(IF_MATCH);
	}
	public void setIfMatch(String ifMatch) {
		setProperty(IF_MATCH, ifMatch);
	}
	public String getUriHost() {
		return getPropertyAsString(URI_HOST);
	}
	public void setUriHost(String uriHost) {
		setProperty(URI_HOST, uriHost);
	}
	public String getObserve() {
		return getPropertyAsString(OBSERVE);
	}
	public void setObserve(String observe) {
		setProperty(OBSERVE, observe);
	}
	public String getUriPort() {
		return getPropertyAsString(URI_PORT);
	}
	public void setUriPort(String uriPort) {
		setProperty(URI_PORT, uriPort);
	}
	public String getProxyUri() {
		return getPropertyAsString(PROXY_URI);
	}
	public void setProxyUri(String proxyUri) {
		setProperty(PROXY_URI, proxyUri);
	}
	public String getAccept() {
		return getPropertyAsString(ACCEPT);
	}
	public void setAccept(String accept) {
		setProperty(ACCEPT, accept);
	}
	public String getContentFormat() {
		return getPropertyAsString(CONTENT_FORMAT);
	}
	public void setContentFormat(String contentFormat) {
		setProperty(CONTENT_FORMAT, contentFormat);
	}
	public boolean isIfNoneMatch() {
		return getPropertyAsBoolean(IF_NONE_MATCH);
	}
	public void setIfNoneMatch(boolean ifNoneMatch) {
		setProperty(IF_NONE_MATCH, ifNoneMatch);
	}
	public boolean isProxySchema() {
		return getPropertyAsBoolean(PROXY_SCHEMA);
	}
	public void setProxySchema(boolean proxySchema) {
		setProperty(PROXY_SCHEMA, proxySchema);
	}
	
	@Override
	public AssertionResult getResult(SampleResult response) {
		AssertionResult result;

		// None of the other Assertions check the response status, so remove
		// this check
		// for the time being, at least...
		// if (!response.isSuccessful())
		// {
		// result = new AssertionResult();
		// result.setError(true);
		// byte [] ba = response.getResponseData();
		// result.setFailureMessage(
		// ba == null ? "Unknown Error (responseData is empty)" : new String(ba)
		// );
		// return result;
		// }

		result = evaluateResponse(response);
		return result;
	}


	/**
	 * Make sure the response satisfies the specified assertion requirements.
	 * 
	 * @param response
	 *            an instance of SampleResult
	 * @return an instance of AssertionResult
	 */

	AssertionResult evaluateResponse(SampleResult response) {
		boolean pass = true;
		boolean not = (NOT & getTestType()) > 0;
		EmptyMessage emptymsg = null;
		Response res = null;
		Message msg = null;
		AssertionResult result = new AssertionResult(getName());
		String toCheck = ""; // The string to check (Url or data)

		if (getAssumeSuccess()) {
			response.setSuccessful(true);// Allow testing of failure codes
		}
		DataParser deserializer = new DataParser(response.getResponseData());
		if(!deserializer.isRequest() && !deserializer.isResponse())
		{
			emptymsg = deserializer.parseEmptyMessage();
			msg = (Message) emptymsg;
		}
		else if(deserializer.isResponse()){
			try{
				res = deserializer.parseResponse();
			}catch(IllegalArgumentException e){
				log.error("Error");
				result.setFailure(true);
				result.setFailureMessage("Error, Invalid or null reply");
				return result;
			}
			
			msg = (Message) res;
		}
		else{
			// Error, received a request
			result.setError(false);
			result.setFailure(true);
			result.setFailureMessage("Received a request");
			return result;
		}
		
		result.setFailure(false);
		result.setError(false);

		// What are we testing against?
		if(getMid() != null && !getMid().equals(""))
		{
			if(Integer.parseInt(getMid()) != msg.getMID())
			{
				result.setFailure(true);
				result.setFailureMessage("MID Failure: Expected=" + Integer.parseInt(getMid()) + ", Received=" + msg.getMID());
				return result;
			}
		}
		if(getToken() != null && !getToken().equals(""))
		{
			if(!getToken().equals(msg.getTokenString()))
			{
				result.setFailure(true);
				result.setFailureMessage("Token Failure: Expected=" + getToken() + ", Received=" + msg.getTokenString());
				return result;
			}
		}
		if(getType() != null && !getType().toString().equals(""))
		{
			if(getType() != msg.getType())
			{
				result.setFailure(true);
				result.setFailureMessage("Type Failure: Expected=" + getType() + ", Received=" + msg.getType());
				return result;
			}
		}
		if(getResponseCode() != null && !getResponseCode().toString().equals(""))
		{
			if(res == null || !getResponseCode().equals(toFormattedString(res.getCode())))
			{
				result.setFailure(true);
				if(res == null)
					result.setFailureMessage("Code Failure: Empty Message as response");
				else
					result.setFailureMessage("Code Failure: Expected=" + getResponseCode() + ", Received=" + res.getCode());
				return result;
			}
		}
		if(getETag() != null && !getETag().equals(""))
		{
			if(!msg.getOptions().getETags().contains(getETag().getBytes()))
			{
				result.setFailure(true);
				result.setFailureMessage("ETag Failure: Expected=" + getETag().getBytes() + ", Received=" + msg.getOptions().getETags());
				return result;
			}
		}
		if(getIfMatch() != null && !getIfMatch().equals(""))
		{
			if(!msg.getOptions().getIfMatch().contains(getIfMatch().getBytes()))
			{
				result.setFailure(true);
				result.setFailureMessage("IfMatch Failure: Expected=" + getIfMatch().getBytes() + ", Received=" + msg.getOptions().getIfMatch());
				return result;
			}
		}
		if(getUriHost() != null && !getUriHost().equals(""))
		{
			if(!getUriHost().equals(msg.getOptions().getUriHost()))
			{
				result.setFailure(true);
				result.setFailureMessage("UriHost Failure: Expected=" + getUriHost() + ", Received=" + msg.getOptions().getUriHost());
				return result;
			}
		}
		if(getObserve() != null && !getObserve().equals(""))
		{
			if(Integer.parseInt(getObserve()) != msg.getOptions().getObserve())
			{
				result.setFailure(true);
				result.setFailureMessage("Observe Failure: Expected=" + Integer.parseInt(getObserve()) + ", Received=" + msg.getOptions().getObserve());
				return result;
			}
		}
		if(getUriPort() != null && !getUriPort().equals(""))
		{
			if(Integer.parseInt(getUriPort()) != msg.getOptions().getUriPort())
			{
				result.setFailure(true);
				result.setFailureMessage("Uri-Port Failure: Expected=" + Integer.parseInt(getUriPort()) + ", Received=" + msg.getOptions().getUriPort());
				return result;
			}
		}
		if(getProxyUri() != null && !getProxyUri().equals(""))
		{
			if(!getProxyUri().equals(msg.getOptions().getProxyUri()))
			{
				result.setFailure(true);
				result.setFailureMessage("Proxy-Uri Failure: Expected=" + getProxyUri() + ", Received=" + msg.getOptions().getProxyUri());
				return result;
			}
		}
		if(getAccept() != null && !getAccept().toString().equals(""))
		{
			if(Integer.parseInt(getAccept()) != msg.getOptions().getAccept())
			{
				result.setFailure(true);
				result.setFailureMessage("Accept Failure: Expected=" + Integer.parseInt(getAccept()) + ", Received=" + msg.getOptions().getAccept());
				return result;
			}
		}
		if(getContentFormat() != null && !getContentFormat().toString().equals(""))
		{
			if(Integer.parseInt(getContentFormat()) != msg.getOptions().getContentFormat())
			{
				result.setFailure(true);
				result.setFailureMessage("ContentFormat Failure: Expected=" + Integer.parseInt(getContentFormat()) + ", Received=" + msg.getOptions().getContentFormat());
				return result;
			}
		}
		if(isIfNoneMatch())
		{
			if(!msg.getOptions().hasIfNoneMatch())
			{
				result.setFailure(true);
				result.setFailureMessage("If-None-Match Failure: Expected=" + isIfNoneMatch() + ", Received=" + msg.getOptions().hasIfNoneMatch());
				return result;
			}
		}
		if(isProxySchema())
		{
			if(!msg.getOptions().hasProxyScheme())
			{
				result.setFailure(true);
				result.setFailureMessage("Proxy-Schema Failure: Expected=" + isProxySchema() + ", Received=" + msg.getOptions().hasProxyScheme());
				return result;
			}
		}
		if(res == null && getTestStrings().size() > 0)
		{
			result.setFailure(true);
			result.setFailureMessage("Payload Failure: Empty Message as response");
			return result;
		}
		toCheck = res.getPayloadString();

		boolean contains = isContainsType(); // do it once outside loop
		boolean equals = isEqualsType();
		boolean debugEnabled = log.isDebugEnabled();
		if (debugEnabled){
			log.debug("Type:" + (contains?"Contains":"Match") + (not? "(not)": ""));
		}
		
		try {
			// Get the Matcher for this thread
			Perl5Matcher localMatcher = JMeterUtils.getMatcher();
			PropertyIterator iter = getTestStrings().iterator();
			while (iter.hasNext()) {
				String stringPattern = iter.next().getStringValue();
				Pattern pattern = JMeterUtils.getPatternCache().getPattern(stringPattern, Perl5Compiler.READ_ONLY_MASK);
				boolean found;
				if (contains) {
					found = localMatcher.contains(toCheck, pattern);
                } else if (equals) {
                    found = toCheck.equals(stringPattern);
				} else {
					found = localMatcher.matches(toCheck, pattern);
				}
				pass = not ? !found : found;
				if (!pass) {
					if (debugEnabled){log.debug("Failed: "+pattern);}
					result.setFailure(true);
					result.setFailureMessage(getFailText(stringPattern,toCheck));
					break;
				}
				if (debugEnabled){log.debug("Passed: "+pattern);}
			}
		} catch (MalformedCachePatternException e) {
			result.setError(true);
			result.setFailure(false);
			result.setFailureMessage("Bad test configuration " + e);
		}
		return result;
	}
	
	private String toFormattedString(ResponseCode obj) {
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
		return "";
	}
	/**
	 * Generate the failure reason from the TestType
	 * 
	 * @param stringPattern
	 * @return the message for the assertion report 
	 */
	// TODO strings should be resources (but currently must not contain commas or the CSV file will be broken)
	private String getFailText(String stringPattern, String toCheck) {
		
		StringBuffer sb = new StringBuffer(200);
		sb.append("Test failed: Payload");

		switch (getTestType()) {
		case CONTAINS:
			sb.append(" expected to contain ");
			break;
		case NOT | CONTAINS:
			sb.append(" expected not to contain ");
			break;
		case MATCH:
			sb.append(" expected to match ");
			break;
		case NOT | MATCH:
			sb.append(" expected not to match ");
			break;
		case EQUALS:
			sb.append(" expected to equal ");
			break;
		case NOT | EQUALS:
			sb.append(" expected not to equal ");
			break;
		default:// should never happen...
			sb.append(" expected something using ");
		}

		sb.append("/");
		
		if (isEqualsType()){
			sb.append(equalsComparisonText(toCheck, stringPattern));
		} else {
			sb.append(stringPattern);
		}
		
		sb.append("/");
		
		return sb.toString();
	}
	
    private static String trunc(final boolean right, final String str)
    {
        if (str.length() <= EQUALS_SECTION_DIFF_LEN)
            return str;
        else if (right)
            return str.substring(0, EQUALS_SECTION_DIFF_LEN) + EQUALS_DIFF_TRUNC;
        else
            return EQUALS_DIFF_TRUNC + str.substring(str.length() - EQUALS_SECTION_DIFF_LEN, str.length());
    }
    
	/**
     *   Returns some helpful logging text to determine where equality between two strings
     * is broken, with one pointer working from the front of the strings and another working
     * backwards from the end.
     *
     * @param received      String received from sampler.
     * @param comparison    String specified for "equals" response assertion.
     * @return  Two lines of text separated by newlines, and then forward and backward pointers
     *      denoting first position of difference.
     */
    private static StringBuffer equalsComparisonText(final String received, final String comparison)
    {
        final StringBuffer      text;
        int                     firstDiff;
        int                     lastRecDiff = -1;
        int                     lastCompDiff = -1;
        final int               recLength = received.length();
        final int               compLength = comparison.length();
        final int               minLength = Math.min(recLength, compLength);
        final String            startingEqSeq;
        String                  recDeltaSeq = "";
        String                  compDeltaSeq = "";
        String                  endingEqSeq = "";
        final StringBuffer      pad;


        text = new StringBuffer(Math.max(recLength, compLength) * 2);
        for (firstDiff = 0; firstDiff < minLength; firstDiff++)
            if (received.charAt(firstDiff) != comparison.charAt(firstDiff))
                break;
        if (firstDiff == 0)
            startingEqSeq = "";
        else
            startingEqSeq = trunc(false, received.substring(0, firstDiff));

        lastRecDiff = recLength - 1;
        lastCompDiff = compLength - 1;

        while ((lastRecDiff > firstDiff) && (lastCompDiff > firstDiff)
                && received.charAt(lastRecDiff) == comparison.charAt(lastCompDiff))
        {
            lastRecDiff--;
            lastCompDiff--;
        }
        endingEqSeq = trunc(true, received.substring(lastRecDiff + 1, recLength));
        if (endingEqSeq.length() == 0)
        {
            recDeltaSeq = trunc(true, received.substring(firstDiff, recLength));
            compDeltaSeq = trunc(true, comparison.substring(firstDiff, compLength));
        }
        else
        {
            recDeltaSeq = trunc(true, received.substring(firstDiff, lastRecDiff + 1));
            compDeltaSeq = trunc(true, comparison.substring(firstDiff, lastCompDiff + 1));
        }
        pad = new StringBuffer(Math.abs(recDeltaSeq.length() - compDeltaSeq.length()));
        for (int i = 0; i < pad.capacity(); i++)
            pad.append(' ');
        if (recDeltaSeq.length() > compDeltaSeq.length())
            compDeltaSeq += pad.toString();
        else
            recDeltaSeq += pad.toString();

        text.append("\n\n");
        text.append(RECEIVED_STR);
        text.append(startingEqSeq);
        text.append(DIFF_DELTA_START);
        text.append(recDeltaSeq);
        text.append(DIFF_DELTA_END);
        text.append(endingEqSeq);
        text.append("\n\n");
        text.append(COMPARISON_STR);
        text.append(startingEqSeq);
        text.append(DIFF_DELTA_START);
        text.append(compDeltaSeq);
        text.append(DIFF_DELTA_END);
        text.append(endingEqSeq);
        text.append("\n\n");
        return text;
    }
	
}