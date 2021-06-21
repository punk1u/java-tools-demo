package tech.punklu;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class EchoServer {

    private final int port;

    public EchoServer(int port){
        this.port = port;
    }

    public static void main(String[] args) throws Exception{
        if (args.length != 1){
            System.err.println("Usage:" + EchoServer.class.getSimpleName() + "<port>");
            /**
             * 设置端口值
             */
            int port = Integer.parseInt(args[0]);
            /**
             * 调用服务器的start()方法
             */
            new EchoServer(port).start();
        }
    }

    public void start() throws Exception{
        final EchoServerHandler serverHandler = new EchoServerHandler();
        /**
         * 创建用于NIO传输的NioEventLoopGroup
         */
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            /**
             * 创建ServerBootstrap
             */
            ServerBootstrap b = new ServerBootstrap();
            /**
             * 指定NioEventLoopGroup来接受和处理服务器的新连接
             */
            b.group(group)
                    /**
                     * 将Channel的类型指定为NioServerSocketChannel
                     */
                    .channel(NioServerSocketChannel.class)
                    /**
                     * 使用指定的端口设置套接字地址
                     */
                    .localAddress(new InetSocketAddress(port))
                    /**
                     * 添加一个EchoServerHandler到子Channel的ChannelPipeline
                     *
                     * 当一个新的连接被接受时，一个新的Channel将会被创建，而ChannelInitializer将会把一个EchoServerHandler
                     * 的治理添加到该Channel的ChannelPipeline中，这个ChannelHandler将会收到有关入站消息的通知
                     */
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            /**
                             * EchoServerHandler被标注为@Shareable，所以对于所有的客户端来说，
                             * 都会使用同一个EchoServerHandler
                             *
                             */
                            socketChannel.pipeline().addLast(serverHandler);
                        }
                    });
            /**
             * 异步地绑定服务器；调用sync()方法阻塞等待直到绑定完成
             */
            ChannelFuture f = b.bind().sync();
            /**
             * 获取Channel的CloseFuture，并且阻塞当前线程直到它完成
             */
            f.channel().closeFuture().sync();
        }finally {
            /**
             * 关闭EventLoopGroup
             */
            group.shutdownGracefully().sync();
        }
    }
}
