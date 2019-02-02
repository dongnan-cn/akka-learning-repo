package com.bingbing.test;

import java.util.concurrent.CompletableFuture;

import org.junit.Test;

import com.bingbing.JClient;

public class JClientIntegrationTest {
	JClient client = new JClient("127.0.0.1:2552");

	@Test
	public void itShouldSetRecord() throws Exception {
		client.set("123", "abcde");
		String result = (String) ((CompletableFuture) client.get("123")).get();
//		Integer result = (Integer) ((CompletableFuture) client.get("123")).get();
		System.out.println(result);
		assert (result.equals("edcba"));
	}
}
