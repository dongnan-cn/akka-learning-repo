package com.bingbing.message.exception;

import java.io.Serializable;

public class KeyNotFoundException extends Exception implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9169368976175398024L;
	public final String key;

	public KeyNotFoundException(String key) {
		this.key = key;
	}
}
