package com.bingbing;

import static akka.pattern.Patterns.ask;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.bingbing.samples.JavaPongActor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import scala.compat.java8.FutureConverters;
import scala.concurrent.Future;

public class PongActorTest {
	ActorSystem system = ActorSystem.create();
	ActorRef actorRef = system.actorOf(Props.create(JavaPongActor.class));

	@Test
	public void shouldReplyToPingWithPong() throws Exception {
		/*
		 * 现在向 Actor 询问其对于某个消息的响应 这一做法相当直接，我们调用 ask 方法，传入以下参数： 消息发送至的 Actor 引用；
		 * 想要发送给 Actor 的消息； Future 的超时参数：等待结果多久以后就认为询问 ask 会返回一个 Scala
		 * Future，作为响应的占位符。在 Actor 的代码中，Actor 会 向 sender()发送回一条消息，这条消息就是在 ask 返回的 Scala
		 * Future 中将接收到的 响应。
		 */
		Future sFuture = ask(actorRef, "Ping", 1000);

		// 将Future转换成为CompletableFuture， CompletionStage 是 CompletableFuture 实现的接口
		// 而且这是一个只读的接口。为了调用 get 方法，我们将结果的类型转换为 CompletableFuture。
		final CompletionStage<String> cs = FutureConverters.toJava(sFuture);
		final CompletableFuture<String> jFuture = (CompletableFuture<String>) cs;
		// 调用 get()方法将测试线程阻塞，并得到结果。
		assert (jFuture.get(1000, TimeUnit.MILLISECONDS).equals("Pong"));
	}

	@Test(expected = ExecutionException.class)
	public void shouldReplyToUnknownMessageWithFailure() throws Exception {
		Future sFuture = ask(actorRef, "unknown", 1000);
		final CompletionStage<String> cs = FutureConverters.toJava(sFuture);
		final CompletableFuture<String> jFuture = (CompletableFuture<String>) cs;
		jFuture.get(1000, TimeUnit.MILLISECONDS);
	}

	@Test
	public void printToConsole() throws Exception {
		askPong("Ping").thenAccept(x -> System.out.println("replied with: " + x));
		Thread.sleep(100);
	}

	public CompletionStage<String> askPong(String message) {
		Future sFuture = ask(actorRef, "Ping", 1000);
		CompletionStage<String> cs = FutureConverters.toJava(sFuture);
		return cs;
	}
}
