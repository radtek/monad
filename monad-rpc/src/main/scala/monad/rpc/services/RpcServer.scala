// Copyright 2014,2015 the original author or authors. All rights reserved.
// site: http://www.ganshane.com
package monad.rpc.services

import com.google.protobuf.GeneratedMessage.GeneratedExtension
import monad.protocol.internal.CommandProto.BaseCommand
import monad.support.services.ServiceLifecycle
import org.jboss.netty.channel.ChannelFuture

/**
 * rpc server
 */
trait RpcServer extends ServiceLifecycle {
}

trait RpcServerListener {
  def afterStart()

  def afterStop()
}

trait CommandResponse {
  def writeMessage[T](commandRequest: BaseCommand, extension: GeneratedExtension[BaseCommand, T], value: T): ChannelFuture
}

trait RpcServerMessageHandler {
  /**
   * @param commandRequest message command
   * @return handled if true .
   */
  def handle(commandRequest: BaseCommand, response: CommandResponse): Boolean
}

trait RpcServerMessageFilter {
  def handle(commandRequest: BaseCommand,
             response: CommandResponse,
             handler: RpcServerMessageHandler): Boolean
}
