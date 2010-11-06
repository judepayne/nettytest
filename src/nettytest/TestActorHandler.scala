package nettytest

import org.jboss.netty.channel.Channel
import actors.Actor
import nettytest.NettyMessage


class TestActorHandler(val channel: Channel) extends Actor {
  start

  def act = { loop {
    react {
      case NettyMessage.MessageReceived(msg) => println("Actor: " + this.toString + ". msg: " + msg.getMessage.toString)

      }
    }
  }
}