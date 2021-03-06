package org.openstates.api;

/**
 * All exceptions generated by this OpenStates client
 * will be wrapped in this exception class.
 *
 */
public class OpenStatesException extends Exception {
	private static final long serialVersionUID = 1L;
	int rcode;
	public MethodMap methodMap;
	public ArgMap argMap;
	public Class<?> responseType;

	public OpenStatesException(Exception e, MethodMap methodMap, ArgMap argMap, Class<?> responseType ) {
		super(e);
		this.rcode = -1;
		this.methodMap = methodMap;
		this.argMap = argMap;
		this.responseType = responseType;
	}

	public OpenStatesException(Exception e, String msg, MethodMap methodMap, ArgMap argMap, Class<?> responseType ) {
		super(msg, e);
		this.rcode = -1;
		this.methodMap = methodMap;
		this.argMap = argMap;
		this.responseType = responseType;
	}

	public OpenStatesException (int rcode, String msg, MethodMap methodMap, ArgMap argMap, Class<?> responseType) {
		super(msg);
		this.rcode = rcode;
		this.methodMap = methodMap;
		this.argMap = argMap;
		this.responseType = responseType;
	}
	
}
