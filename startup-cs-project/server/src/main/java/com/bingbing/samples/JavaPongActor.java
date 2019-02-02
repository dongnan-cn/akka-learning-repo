package com.bingbing.samples;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Status;

public class JavaPongActor extends AbstractActor {

	@Override
	public Receive createReceive() {
//		when receives a Ping, respond pong, otherwise throw an exception
//		match 函数从上至下进行模式匹配。所以可以先定义特殊情况，最后定义一般
//		情况。
		
//		调用了 sender()方法后，我们就可以返回所收到的消息的响应了。
//		sender()函数会返回一个 ActorRef
		
//		tell()是最基本的单向消息传输模式。第一个参数是我们想要发送至对方信箱的消息。
//		第二个参数则是希望对方 Actor 看到的发送者。
//		ActorRef.noSender()表示没有返回地址
		
//		如果 Actor 中抛出了异常，就会通知对其进行监督的 Actor,如果发送方使用 Future 来接收响应，那么返回错误消息会导致
//		Future 的结果为失败。
		return receiveBuilder().matchEquals("Ping", s -> sender().tell("Pong", ActorRef.noSender()))
				.matchAny(x -> sender().tell(new Status.Failure(new Exception("unknown message")), self())).build();

	}

}
