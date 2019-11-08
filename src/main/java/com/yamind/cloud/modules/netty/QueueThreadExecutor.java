package com.yamind.cloud.modules.netty;

import com.yamind.cloud.common.utils.SpringContextUtils;
import com.yamind.cloud.modules.SpringUtil;
import com.yamind.cloud.modules.sys.service.SysDeviceService;
import com.yamind.cloud.modules.sys.service.impl.SysDeviceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

@Component("queueThreadExecutor")
public class QueueThreadExecutor {

    private final int capacity = 20000000;

    private final BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(capacity); // 队列总容量

    private final int nThreads = Runtime.getRuntime().availableProcessors() * 2; // 默认CPU核心线程数 * 2d

   /* private final ExecutorService workerThreadPool = Executors.newFixedThreadPool(nThreads);*/ // 工作线程池

    // 自定义配置线程池
    private final ThreadPoolExecutor workerThreadPool = new ThreadPoolExecutor(nThreads,nThreads,10, TimeUnit.MINUTES, workQueue);

    @Autowired
    private SysDeviceService sysDeviceService;

    private QueueThreadExecutor() { }

    /**
     * 保存数据（线程池异步执行）
     * @param msg
     */
    public void executeSaveMsg(final String msg) {
        workerThreadPool.execute(() -> {
            sysDeviceService.saveRecvHistoryData(msg);
        });
    }

    /**
     * 关闭
     */
    public void shutdown() {
        workerThreadPool.shutdown(); // 关闭线程池
    }
}
