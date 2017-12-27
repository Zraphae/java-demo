package cn.enn.test.parquet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.example.data.GroupFactory;
import org.apache.parquet.example.data.simple.SimpleGroupFactory;
import org.apache.parquet.hadoop.ParquetFileWriter;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.example.GroupReadSupport;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.MessageTypeParser;

public class App {
	static Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) throws Exception {

		parquetWriter("/parquet-out3", "/home/raphae/testdata/data");
//		parquetReader("/parquet-out1");
	}

	static void parquetReader(String inPath) throws Exception {
		GroupReadSupport readSupport = new GroupReadSupport();
		Configuration configuration = new Configuration();
		configuration.addResource(new Path("/opt/hadoop/etc/hadoop/core-site.xml"));
		ParquetReader<Group> build = ParquetReader.builder(readSupport, new Path(inPath)).withConf(configuration)
				.build();
		Group line = null;
		int count = 0;
		while ((line = build.read()) != null) {
			Group time = line.getGroup("time", 0);
			count++;
			// 通过下标和字段名称都可以获取
			logger.info("====>" + line.getString(0, 0) + "\t" + line.getString(1, 0) + "\t" + time.getInteger(0, 0)
					+ "\t" + time.getString(1, 0) + "\t");

			logger.info("---->" + line.getString("city", 0) + "\t" + line.getString("ip", 0) + "\t"
					+ time.getInteger("ttl", 0) + "\t" + time.getString("ttl2", 0) + "\t");

			logger.info("######" + line.toString());
		}
		logger.info("read parquet file end, count:" + count);
	}

	/**
	 * 
	 * @param outPath
	 *            输出Parquet格式
	 * @param inPath
	 *            输入普通文本文件
	 * @throws IOException
	 */
	static void parquetWriter(String outPath, String inPath) throws IOException {
		MessageType schema = MessageTypeParser.parseMessageType("message Pair {\n" + " required binary city (UTF8);\n"
				+ " required binary ip (UTF8);\n" + " repeated group time {\n" + " required int32 ttl;\n"
				+ " required binary ttl2;\n" + "}\n" + "}");
		GroupFactory factory = new SimpleGroupFactory(schema);
		Path path = new Path(outPath);
		Configuration configuration = new Configuration();
		configuration.addResource(new Path("/opt/hadoop/etc/hadoop/core-site.xml"));

		ParquetWriter<Group> writer = ExampleParquetWriter.builder(path).withType(schema)
				.withWriteMode(ParquetFileWriter.Mode.OVERWRITE).withConf(configuration)
				.withRowGroupSize(20 * 1024 * 1024).build();
		// 把本地文件读取进去，用来生成parquet格式文件
		BufferedReader br = new BufferedReader(new FileReader(new File(inPath)));
		String line = "";
		Random r = new Random();
		while ((line = br.readLine()) != null) {
			String[] strs = line.split(" ");
			for (int i = 0; i < 100000000; i++) {
				if (strs.length > 1) {
					Group group = factory.newGroup().append("city", r.nextInt(90)+ "_" + strs[0]).append("ip", r.nextInt(90)+ "_" + strs[1]);
					Group tmpG = group.addGroup("time");
					tmpG.append("ttl", r.nextInt(90) + 1);
					tmpG.append("ttl2", r.nextInt(90) + "_a");
					writer.write(group);
				}
			}
		}
		logger.info("write parquet file end");
		br.close();
		writer.close();
	}

}
