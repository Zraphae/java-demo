package cn.enn.test.nio2.example;

import java.io.IOException;

import cn.enn.test.nio2.IMessageProcessor;
import cn.enn.test.nio2.Message;
import cn.enn.test.nio2.Server;
import cn.enn.test.nio2.http.HttpMessageReaderFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by jjenkov on 19-10-2015.
 */
@Slf4j
public class Main {

	public static void main(String[] args) throws IOException {

		String httpResponse = "HTTP/1.1 200 OK\r\n" + "Content-Length: 38\r\n" + "Content-Type: text/html\r\n" + "\r\n"
				+ "<html><body>Hello World!</body></html>";

		byte[] httpResponseBytes = httpResponse.getBytes("UTF-8");

		IMessageProcessor messageProcessor = (request, writeProxy) -> {
			log.info("Message Received from socket: " + request.socketId);

			Message response = writeProxy.getMessage();
			response.socketId = request.socketId;
			response.writeToMessage(httpResponseBytes);
			writeProxy.enqueue(response);
		};

		Server server = new Server(9999, new HttpMessageReaderFactory(), messageProcessor);

		server.start();

	}

}
