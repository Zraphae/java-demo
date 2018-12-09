package cn.enn.test.zookeeper;

import cn.enn.test.zookeeper.curator.pathcache.PathCacheWatcher;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZkCachePathWatcherTests {

    public static void main(String[] args) throws InterruptedException {


        PathCacheWatcher pathCacheWatcher = PathCacheWatcher.builder().watchPath("/test").build();
        pathCacheWatcher.start();

        Thread.sleep(100 * 1000L);

        byte[] zkDataArr = pathCacheWatcher.getZkDataArr();

        log.info("==>zkData: {}", new String(zkDataArr));

        pathCacheWatcher.shutdown();

    }
}
