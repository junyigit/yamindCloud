package com.yamind.cloud.modules.netty;

import com.yamind.cloud.modules.SpringUtil;
import com.yamind.cloud.modules.sys.service.SysDeviceService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
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

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SysDeviceService sysDeviceService;

    private static ServerHandler serverHandler;

    private static String clientAddr="";

    @PostConstruct
    public void init() {
        serverHandler = this;
        serverHandler.redisTemplate =this.redisTemplate;
        serverHandler.sysDeviceService = this.sysDeviceService;
    }


    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) {


        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();


        //获取IP地址
        String clientIP = insocket.getAddress().getHostAddress();

        //控制台输出接受到的数据
        System.out.println("connetIP is : " + clientIP +"server receive message :"+ msg);

        //返回接受数据给发送方
        //ctx.channel().writeAndFlush(msg);

        //赋值给clientAddr
        clientAddr =clientIP;

        if (isJson(msg)){
            logger.info("===========输出日志==============[正确Json数据]"+msg);
            try {
                //获取的消息 转换为JsonObject
                JSONObject jsonObject = JSONObject.fromObject(msg);
                //获取IP后存入Redis
                jsonObject.put("ipAddr",clientIP);
                //判断接收的数据是否为空 Y：退出  N：左侧写入Key名和Json数据
                if (msg!=null){
                    serverHandler.redisTemplate.opsForList().leftPush(jsonObject.getString("serialId"),jsonObject.toString());
                    serverHandler.sysDeviceService.saveRecvData(jsonObject.toString());
                }
            }catch (Exception e){
                // TODO: handle exception
            }
        }else{
            logger.info("===========输出日志=============收到数据:[不是json数据]");
            serverHandler.sysDeviceService.saveRecvHistoryData((String)msg);


            //如果当前接受信息不是json格式,或者有单独的特殊指令
            if (msg.equals("test")){
                logger.info("==========输出日志============下发成功！");
            }
        }
//      ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("发现有新的连接"+ctx.name());
        InetSocketAddress insocket1 = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = insocket1.getAddress().getHostAddress();
        ChannelMap.addChannel(clientIP,ctx.channel());
    }



    public boolean isJson(Object content){
        try {
            JSONObject jsonStr= JSONObject.fromObject(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}