package cn.enn.test.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileChannelApp {
	
	public static void main(String[] args) {
		ByteBuffer buffer = ByteBuffer.allocate(48);
		RandomAccessFile aFile = null;
		StringBuffer strBuffer = new StringBuffer();
		try {
			aFile = new RandomAccessFile("resouces/log4j.properties", "rw");
			FileChannel fileChannel = aFile.getChannel();
			log.info("file size: {}", fileChannel.size());
			int readByte = -1;
			while((readByte = fileChannel.read(buffer)) > 0) {
				log.info("read byte size: {}", readByte);
				buffer.flip();
				strBuffer.append(new String(buffer.array()));
				buffer.clear();
			}
			buffer.clear();
			fileChannel.close();
			aFile.close();
			log.info("\n{}", strBuffer.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
}
