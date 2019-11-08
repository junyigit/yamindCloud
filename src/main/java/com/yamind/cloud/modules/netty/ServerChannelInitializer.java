package com.yamind.cloud.modules.netty;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;

import javax.websocket.MessageHandler;


public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) {
        // 解码编码


        ByteBuf delimiter=Unpooled.copiedBuffer("$".getBytes());//指定消息分割符处理数据
        socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(3072 , delimiter));//如果取消了分割符解码，就会出现TCP粘包之类的问题了
       // socketChannel.pipeline().addLast(new BufByteToMessageDecoder()); //
        socketChannel.pipeline().addLast(new StringDecoder());
        socketChannel.pipeline().addLast(new ServerHandler());
    }
}