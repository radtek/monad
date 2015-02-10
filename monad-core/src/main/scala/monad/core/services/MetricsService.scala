// Copyright 2014,2015 Jun Tsai. All rights reserved.
// site: http://www.ganshane.com
package monad.core.services

import com.codahale.metrics._
import monad.support.services.ServiceLifecycle

/**
 * metrics service
 */
trait MetricsService extends ServiceLifecycle {
  def unregister(name: String)

  def register[T <: Metric](name: String, metric: T): T

  def register(name: String, histogram: Histogram): Histogram

  def registerCounter(name: String): Counter

  def registerMeter(name: String): Meter

  def registerTimer(name: String): Timer

  def registerAll(prefix: String, metrics: MetricSet)
}