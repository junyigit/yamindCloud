package com.yamind.cloud.modules.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class SendMsgHandler extends ChannelOutboundHandlerAdapter {



    private static Logger logger = LoggerFactory.getLogger(SendMsgHandler.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg,
                      ChannelPromise promise) throws Exception {

        if (msg instanceof byte[]) {
            byte[] bytesWrite = (byte[])msg;
            ByteBuf buf = ctx.alloc().buffer(bytesWrite.length);
            logger.info("向设备下发的信息为："+NettyServer.bytesToHexString(bytesWrite));

            buf.writeBytes(bytesWrite);
            ctx.writeAndFlush(buf).addListener(new ChannelFutureListener(){
                @Override
                public void operationComplete(ChannelFuture future)
                        throws Exception {
                    logger.info("");
                    logger.info("===========输出日志==============[ 数据传输下发成功！]");
                }
            });
        }
    }
}
