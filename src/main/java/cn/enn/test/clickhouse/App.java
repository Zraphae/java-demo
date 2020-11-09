package cn.enn.test.clickhouse;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import ru.yandex.clickhouse.BalancedClickhouseDataSource;
import ru.yandex.clickhouse.ClickHouseConnection;
import ru.yandex.clickhouse.ClickHouseStatement;
import ru.yandex.clickhouse.domain.ClickHouseFormat;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class App {

    public static void main(String[] args) throws SQLException, IOException {

        ClickHouseProperties properties = new ClickHouseProperties();
        properties.setDatabase("default");
//        properties.setUser("root");
//        properties.setPassword("415426880");
        properties.setConnectionTimeout(100);
        properties.setSocketTimeout(300000);
        //clickhouse jdbc 包，可以用来做负载均衡；策略是随机的，但是搭建SLB之后，就可以使用nginx 的负载均衡策略了
        BalancedClickhouseDataSource ckDataSource =
                new BalancedClickhouseDataSource("jdbc:clickhouse://localhost:8123", properties);
        // 添加集群节点存活检查
        ckDataSource.scheduleActualization(10, TimeUnit.SECONDS);

        ClickHouseConnection connection = ckDataSource.getConnection();
        ClickHouseStatement statement = connection.createStatement();


        String csvContent = FileUtils.readFileToString(new File("/Users/zhaopeng/Downloads/1.csv"));
        log.info("=====>String: {}", csvContent);
        InputStream inputStream = IOUtils.toInputStream(csvContent, StandardCharsets.UTF_8);

        statement
                .write() // Write API entrypoint
                .table("default.ontime") // where to write data
                .option("format_csv_delimiter", ",") // specific param
                .data(inputStream, ClickHouseFormat.CSV)
//                .data(new File("/Users/zhaopeng/Downloads/1.csv"), ClickHouseFormat.CSV) // specify input
                .send();


    }
}
