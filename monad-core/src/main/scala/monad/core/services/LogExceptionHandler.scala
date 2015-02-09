// Copyright 2012,2013,2014,2015 Jun Tsai. All rights reserved.
// site: http://www.ganshane.com
package monad.core.services

import com.lmax.disruptor.ExceptionHandler
import monad.support.services.{LoggerSupport, MonadException}

/**
 * 针对异常的处理，仅仅输出
 * @author jcai
 */
class LogExceptionHandler extends ExceptionHandler with LoggerSupport {

  def handleEventException(ex: Throwable, sequence: Long, event: Any) {
    ex match {
      case e: MonadException =>
        error(e.toString)
      case e: InterruptedException =>
      //do nothing
      case _ =>
        error(ex.getMessage, ex)
    }
  }

  def handleOnStartException(ex: Throwable) {
    error(ex.getMessage, ex)
  }

  def handleOnShutdownException(ex: Throwable) {
    error(ex.getMessage, ex)
  }
}
