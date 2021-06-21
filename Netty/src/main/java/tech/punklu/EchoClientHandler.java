package tech.punklu;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 标记该类的实例可以被多个Channel共享
 */
@ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    /**
     * 将在一个连接建立时被调用。这确保了数据将会被尽可能快地写入服务器
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /**
         * 当被通知Channel是活跃的时候，发送一条消息
         */
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
    }

    /**
     * 每当接收数据时，都会调用这个方法。需要注意的是，由服务器发送的消息可能会被分块接收。
     * 也就是说，如果服务器发送了 5 字节，那么不能保证这 5 字节会被一次性接收。
     * 即使是对于这么少量的数据，channelRead0()方法也可能会被调用两次，
     * 第一次使用一个持有 3 字节的 ByteBuf（Netty 的字节容器），第二次使用一个
     * 持有 2 字节的 ByteBuf。作为一个面向流的协议，TCP 保证了字节数组将会按照服务器发送它
     * 们的顺序被接收。
     * @param channelHandlerContext
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        /**
         * 记录已接收消息的转储
         */
        System.out.println("Client received: " + byteBuf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        /**
         * 在发生异常时，记录错误并关系Channel
         */
        cause.printStackTrace();
        ctx.close();
    }
}
