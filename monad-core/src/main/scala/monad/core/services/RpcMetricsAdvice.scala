// Copyright 2014,2015 Jun Tsai. All rights reserved.
// site: http://www.ganshane.com
package monad.core.services

import org.apache.tapestry5.ioc.MethodAdviceReceiver

/**
 * advice rpc using metrics
 */
trait RpcMetricsAdvice {
  def advice(receiver: MethodAdviceReceiver)
}