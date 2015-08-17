package it.unipi.jmeter.sampler.coap;

import org.apache.jmeter.samplers.SampleResult;
import org.eclipse.californium.core.coap.Request;

public class SampleResultCoAP extends SampleResult {
	private Request request;

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}
	
}
