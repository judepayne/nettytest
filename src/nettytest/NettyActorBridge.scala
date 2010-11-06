package nettytest

import _root_.nettytest.NettyMessage.MessageReceived
import actors.Actor
import org.jboss.netty.channel._


abstract sealed class NettyMessage
object NettyMessage {
  case class MessageReceived(e: MessageEvent) extends NettyMessage
  case object ChannelConnected extends NettyMessage
  case object ChannelDisconnected extends NettyMessage
}

class NettyActorBridge (val actorFactory: (Channel) => Actor) extends SimpleChannelHandler {

  private var actorHandler: Actor = null

  def getActor: Actor = actorHandler

  def send(message: NettyMessage) = {
    actorHandler ! message
  }

  override def messageReceived(ctx: ChannelHandlerContext, e:  MessageEvent) = send(MessageReceived(e))

  override def channelConnected(ctx: ChannelHandlerContext, e:  ChannelStateEvent) = {
    actorHandler = actorFactory(ctx.getChannel)
    send(ChannelConnected)
  }

  override def channelDisconnected(ctx: ChannelHandlerContext, e: ChannelStateEvent) = send(ChannelDisconnected)

}