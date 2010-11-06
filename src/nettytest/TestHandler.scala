package nettytest

import org.jboss.netty.channel._

class TestHandler extends SimpleChannelHandler{

  override def messageReceived(ctx: ChannelHandlerContext, e: MessageEvent): Unit = {
    println("I got ya!")
  }

  override def exceptionCaught(ctx: ChannelHandlerContext, e: ExceptionEvent): Unit = {
    e.getCause.printStackTrace()
    val ch: Channel = e.getChannel
    ch.close
  }

}