package com.bingbing.message;

import java.io.Serializable;

//immutable object
//contains a simple key-value entry
public class SetRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 833298468128982919L;
	public final String key;
	public final Object value;

	public SetRequest(String key, Object value) {
		this.key = key;
		this.value = value;
	}
}
