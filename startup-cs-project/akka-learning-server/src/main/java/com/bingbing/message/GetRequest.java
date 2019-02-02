package com.bingbing.message;

import java.io.Serializable;

public class GetRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2012892967109447468L;
	public final String key;

	public GetRequest(String key) {
		this.key = key;
	}
}
