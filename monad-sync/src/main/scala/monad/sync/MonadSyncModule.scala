// Copyright 2011,2012,2013,2015,2016 the original author or authors. All rights reserved.
// site: http://www.ganshane.com
package monad.sync

import monad.core.MonadCoreSymbols
import monad.core.internal.MonadCoreUtils
import stark.utils.services.XmlLoader
import monad.sync.config.MonadSyncConfig
import org.apache.tapestry5.ioc.annotations.Symbol

/**
 * monad synchronizer module
 * @author jcai
 * @version 0.1
 */
object MonadSyncModule {
  /*
  def buildIdFacade(rpcCreator:RpcRemoteServiceCreator)={
      rpcCreator.createRemoteInstance(classOf[IdFacade])
  }
  */
  def buildMonadSyncConfig(@Symbol(MonadCoreSymbols.SERVER_HOME) serverHome: String) = {
    val content = MonadCoreUtils.readConfigContent(serverHome,"monad-sync.xml")
    XmlLoader.parseXML[MonadSyncConfig](content, xsd = Some(getClass.getResourceAsStream("/monad/sync/monad-sync.xsd")))
  }
}
