package com.bingbing;

import java.util.concurrent.CompletionStage;
import static akka.pattern.Patterns.ask;
import com.bingbing.message.GetRequest;
import com.bingbing.message.SetRequest;
import scala.compat.java8.FutureConverters;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;

public class JClient {
	private final ActorSystem system = ActorSystem.create("LocalSystem");
	private final ActorSelection remoteDb;

	public JClient(String remoteAddress) {
		System.out.println("===================="+remoteAddress);
		remoteDb = system.actorSelection("akka.tcp://akkademy@" + remoteAddress + "/user/akkademy-db");
	}

	public CompletionStage set(String key, Object value) {
		return FutureConverters.toJava(ask(remoteDb, new SetRequest(key, value), 2000));
	}

	public CompletionStage<Object> get(String key) {
		return FutureConverters.toJava(ask(remoteDb, new GetRequest(key), 2000));
	}
}
