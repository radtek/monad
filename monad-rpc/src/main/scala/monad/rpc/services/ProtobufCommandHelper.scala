// Copyright 2014,2015 Jun Tsai. All rights reserved.
// site: http://www.ganshane.com
package monad.rpc.services

import com.google.protobuf.GeneratedMessage
import monad.protocol.internal.CommandProto.BaseCommand

/**
 * protocol command helper trait
 */
trait ProtobufCommandHelper {
  def wrap[T](extension: GeneratedMessage.GeneratedExtension[BaseCommand, T], value: T): BaseCommand = {
    BaseCommand.newBuilder().setExtension(extension, value).build()
  }
}