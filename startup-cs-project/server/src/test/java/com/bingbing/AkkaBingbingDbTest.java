package com.bingbing;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.bingbing.message.SetRequest;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;

public class AkkaBingbingDbTest {
	ActorSystem system = ActorSystem.create();
	String keySent = "hahaha";
	String valueSent = "hihihi";

	@Test
	public void itShouldPlaceKeyValueFromSetMessageIntoMap() {
		TestActorRef<AkkaBingbingDb> actorRef = TestActorRef.create(system, Props.create(AkkaBingbingDb.class));
		System.out.println(actorRef.path());
		actorRef.tell(new SetRequest(keySent, valueSent), ActorRef.noSender());
		actorRef.tell(new SetRequest("123", "456"), ActorRef.noSender());
		AkkaBingbingDb akkademyDb = actorRef.underlyingActor();
		assertEquals(akkademyDb.map.get(keySent), valueSent);
		assertEquals(akkademyDb.map.get("123"), "456");
	}
}
