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

    private final int capacity = 50000000;

    private final BlockingQueue<String> queue = new ArrayBlockingQueue<>(capacity); // 队列总容量

    private final int nThreads = Runtime.getRuntime().availableProcessors() * 2; // 默认CPU核心线程数 * 2d

    private final ExecutorService workerThreadPool = Executors.newFixedThreadPool(nThreads); // 工作线程池

    private final  QueueThread queueThread = new QueueThread(); // 队列线程

    @Autowired
    private SysDeviceService sysDeviceService;

    private QueueThreadExecutor() { }

    /**
     * 启动队列线程（消费）
     */
    public  void start() {
        queueThread.start(); // 启动队列线程
    }

    /**
     * 关闭
     */
    public void shutdown() {
        queueThread.calcel(); // 关闭队列线程
        workerThreadPool.shutdown(); // 关闭线程池
    }


    public  boolean addMsg(String msg) {
        return queue.add(msg);
    }


    private class QueueThread extends Thread {

        private final Logger logger = LoggerFactory.getLogger(QueueThread.class);

        @Override
        public void run() {
            try {
                logger.info("队列线程已启动.......");
                while (!Thread.currentThread().isInterrupted()) { // 如果线程没有被中断
                    final String str = queue.take();  // 获取队列数据
                    /*
                     *  如果队列线程处理速度与生产速度不匹配，使用线程池进行异步处理保存数据
                     *  就注释这里，你是一个线程处理所有数据，设备不只有一个，
                     *  设备发数据太快消费线程处理过慢的话就放入线程池异步处理
                     */
                    workerThreadPool.execute(() -> {
                        sysDeviceService.saveRecvHistoryData(str);
                    });
                }
            } catch (InterruptedException e) {
             //   e.printStackTrace();
                logger.info("线程被中断,"+ e);
            }
        }

        /**
         * 中断线程
         */
        public void calcel() {
            interrupt();
        }

    }
}
