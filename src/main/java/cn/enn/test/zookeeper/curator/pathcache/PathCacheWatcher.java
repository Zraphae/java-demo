package cn.enn.test.zookeeper.curator.pathcache;

import com.google.common.base.Preconditions;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.data.Stat;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
@Builder
public class PathCacheWatcher {

    private String watchPath;

    @Builder.Default
    private String connectString = "127.0.0.1";

    @Builder.Default
    private int baseSleepTimeMs = 1000;

    @Builder.Default
    private int maxRetries = 3;

    @Builder.Default
    private boolean cacheData = true;

    @Builder.Default
    private ExecutorService listenerThreadPool = Executors.newSingleThreadExecutor();

    private final ExecutorService handle = Executors.newSingleThreadExecutor();

    private final CountDownLatch latch = new CountDownLatch(1);

    private byte[] zkDataArr;

    public void start() {
        Preconditions.checkNotNull(this.watchPath, "watchPath can't be null");

        log.info("pathCacheWatcher starting ....");

        this.handle.submit(() -> {
            ExponentialBackoffRetry backoffRetry = new ExponentialBackoffRetry(this.baseSleepTimeMs, this.maxRetries);

            CuratorFramework client = null;
            PathChildrenCache cache = null;

            try {
                client = CuratorFrameworkFactory.newClient(this.connectString, backoffRetry);
                client.start();

                cache = new PathChildrenCache(client, watchPath, this.cacheData);
                cache.start();

                this.addListener(cache);

                latch.await();
            } catch (Exception e) {
                log.error("pathCacheWatcher start failed ", e);
            } finally {
                CloseableUtils.closeQuietly(cache);
                CloseableUtils.closeQuietly(client);
            }
        });

    }

    public void shutdown() {
        this.latch.countDown();
        this.listenerThreadPool.shutdownNow();
        this.handle.shutdownNow();
        log.info("pathCacheWatcher shutdown ....");
    }

    private void addListener(PathChildrenCache cache) {

        PathChildrenCacheListener listener = (client, event) -> {
            switch (event.getType()) {

                case CHILD_ADDED: {
                    log.info("Node added: " + ZKPaths.getNodeFromPath(event.getData().getPath()));
                    this.zkDataArr = event.getData().getData();
                    break;
                }

                case CHILD_UPDATED: {
                    log.info("Node changed: " + ZKPaths.getNodeFromPath(event.getData().getPath()));
                    this.zkDataArr = event.getData().getData();
                    break;
                }

                case CHILD_REMOVED: {
                    log.info("Node removed: " + ZKPaths.getNodeFromPath(event.getData().getPath()));
                    break;
                }
            }
        };
        cache.getListenable().addListener(listener, this.listenerThreadPool);
    }

    public byte[] getZkDataArr() {
        return Objects.isNull(this.zkDataArr) ? new byte[0] : this.zkDataArr;
    }
}
