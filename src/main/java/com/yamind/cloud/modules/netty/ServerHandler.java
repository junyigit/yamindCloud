package com.yamind.cloud.modules.netty;

import com.yamind.cloud.common.utils.SpringContextUtils;
import com.yamind.cloud.modules.SpringUtil;
import com.yamind.cloud.modules.sys.service.SysDeviceService;
import com.yamind.cloud.modules.sys.service.impl.SysDeviceServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;

@Component
public class ServerHandler extends SimpleChannelInboundHandler<Object> {

    private static Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    //private RedisTemplate redisTemplate;

    //private SysDeviceService sysDeviceService = SpringContextUtils.getBean(SysDeviceServiceImpl.class);

    private QueueThreadExecutor queueThreadExecutor = SpringContextUtils.getBean(QueueThreadExecutor.class);

    private static ServerHandler serverHandler;

    private static String clientAddr="";


    /**
     * 写入txt
     */
    public ServerHandler() {
        System.out.println("调用了ServerHandler构造");
    }

  /* @PostConstruct
    public void init() {
        serverHandler = this;
        serverHandler.redisTemplate = this.redisTemplate;
        serverHandler.sysDeviceService = this.sysDeviceService;
        serverHandler.queueThreadExecutor =  this.queueThreadExecutor;
    }*/


    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) {

        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();

        //获取IP地址
        String clientIP = insocket.getAddress().getHostAddress();

        //控制台输出接受到的数据
        //System.out.println("connetIP is : " + clientIP +"server receive message :"+ msg);

        //返回接受数据给发送方
        //ctx.channel().writeAndFlush(msg);

        //赋值给clientAddr
        //clientAddr =clientIP;

        //打印数据
       System.out.println((String)msg);
       //out.println("queueThreadExecutor: "+ queueThreadExecutor);
        //将消息加入到队列
        queueThreadExecutor.addMsg((String) msg);

    }


    /**
     * 发现连接的时候调用
     * @param ctx
     */

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        InetSocketAddress insocket1 = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = insocket1.getAddress().getHostAddress();
        ChannelMap.addChannel(clientIP,ctx.channel());
        System.out.println("发现新的客户端连接，IP为["+clientIP+"]");
    }


    /**
     * 断开连接的时候调用
     * @param ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        InetSocketAddress insocket1 = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = insocket1.getAddress().getHostAddress();
        System.out.println("IP为["+clientIP+"]断开了连接");
    }

    /**
     * 发生异常
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
     //  看具体怎么处理
    }

    /**
     * 判断是否为JSON数据
     * @param content
     * @return
     */
    public boolean isJson(Object content){
        try {
            JSONObject jsonStr= JSONObject.fromObject(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}