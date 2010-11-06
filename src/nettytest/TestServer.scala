package nettytest

import java.net.InetSocketAddress
import java.util.concurrent.Executors
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory
import org.jboss.netty.bootstrap.ServerBootstrap
import org.jboss.netty.channel._

object TestServer {

  def main(args: Array[String]): Unit = {
    val factory: ChannelFactory = new NioServerSocketChannelFactory( Executors.newCachedThreadPool, Executors.newCachedThreadPool)
    val bootstrap: ServerBootstrap = new ServerBootstrap(factory)
    bootstrap.setPipelineFactory(new TestServerPipelineFactory )
    bootstrap.setOption("child.tcpNoDelay", true)
    bootstrap.setOption("child.keepAlive", true)

    bootstrap.bind(new InetSocketAddress(8080))
  }

  class TestServerPipelineFactory extends ChannelPipelineFactory {
    def getPipeline: ChannelPipeline = {
      val pipe = Channels.pipeline
      val chan = pipe.getChannel
      pipe.addLast("handler", new NettyActorBridge(chan => new TestActorHandler(chan)))
      println("ooof")
      pipe
      //return Channels.pipeline(new TestHandler())
    }
  }
}