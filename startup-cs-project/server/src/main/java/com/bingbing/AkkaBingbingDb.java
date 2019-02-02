package com.bingbing;

import java.util.HashMap;
import java.util.Map;

import com.bingbing.message.GetRequest;
import com.bingbing.message.SetRequest;
import com.bingbing.message.exception.KeyNotFoundException;

import akka.actor.AbstractActor;
import akka.actor.Status;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class AkkaBingbingDb extends AbstractActor {

	protected final LoggingAdapter log = Logging.getLogger(context().system(), this);
	protected final Map<String, Object> map = new HashMap<>();

	private AkkaBingbingDb() {
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(SetRequest.class, message -> {
			// When receive a SetRequest,log the message, when receive other type of
			// request,
			// log the error
			log.info("Received Set request: {}", message);
			map.put(message.key, reverseString(message.value));
			sender().tell(new Status.Success(message.key), self());
		}).match(GetRequest.class, message -> {
			log.info("Received Get request: {}", message);
			Object value = map.get(message.key);
			Object response = (value != null) ? value : new Status.Failure(new KeyNotFoundException(message.key));
			sender().tell(response, self());
		}).matchAny(o -> sender().tell(new Status.Failure(new ClassNotFoundException()), self())).build();
	}
	
	
	private static String reverseString(Object obj) {
		String s = String.valueOf(obj);
		String reversed = new StringBuilder(s).reverse().toString();
		return reversed;
	}
	
	public static void main(String[] args) {
		System.out.println(reverseString("abcde"));
	}
}
