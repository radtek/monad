// Copyright 2014,2015 Jun Tsai. All rights reserved.
// site: http://www.ganshane.com
package monad.rpc.services

import monad.support.services.ErrorCode

/**
 * monad rpc error code
 */
object MonadRpcErrorCode {

  case object OVER_BIND_COUNT extends ErrorCode(5001)

}
