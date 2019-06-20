package com.poc.ribbon;

import com.netflix.ribbon.ClientOptions;
import com.netflix.ribbon.Ribbon;
import com.netflix.ribbon.http.HttpRequestTemplate;
import com.netflix.ribbon.http.HttpResourceGroup;
import io.netty.buffer.ByteBuf;
import org.apache.catalina.Server;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import rx.Observable;

public class HelloWorldRibbon {

	private final static String LIST_OF_HOSTS = "localhost:9000, localhost:9001";

	public static void main(String args[]) {

		HttpResourceGroup httpResourceGroup = Ribbon.createHttpResourceGroup("pingService",
				ClientOptions.create().withMaxAutoRetriesNextServer(3).withConfigurationBasedServerList(LIST_OF_HOSTS));

		HttpRequestTemplate<ByteBuf> pingIdTemplate = httpResourceGroup
				.newTemplateBuilder("pingIdTemplate", ByteBuf.class).withMethod("GET")
				.withUriTemplate("/ping/{numberToPing}").build();

		makeRequest(pingIdTemplate);		
	}

	private static void makeRequest(HttpRequestTemplate<ByteBuf> pingIdTemplate) {
		
		List<Integer> numbers = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

		numbers.stream().map(number -> {
			ByteBuf numberPrinted = pingIdTemplate.requestBuilder().withRequestProperty("numberToPing", number).build()
					.execute();

			return numberPrinted.toString();
		}).forEach(System.out::println);
	}

}
