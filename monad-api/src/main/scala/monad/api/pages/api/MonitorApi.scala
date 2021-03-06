// Copyright 2011,2012,2013,2015,2016 the original author or authors. All rights reserved.
// site: http://www.ganshane.com
/*
 * Copyright 2002-2010 Jun Tsai. 
 * site: http://www.ganshane.com
 */

package monad.api.pages.api


import com.google.gson.JsonObject
import monad.api.MonadApiConstants
import monad.api.base.BaseApi
import monad.api.services.{MonadApiExceptionCode, SearcherFacade}
import stark.utils.services.StarkException
import org.apache.tapestry5.ioc.annotations.Inject
import org.apache.tapestry5.ioc.internal.util.InternalUtils
import org.apache.tapestry5.services.Request


/**
 * monitor api
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
class MonitorApi extends BaseApi {
  @Inject
  var searchFacade: SearcherFacade = _
  @Inject
  var request: Request = _

  /**
   *
   * @see monad.core.node.api.base.BaseApi#doExecuteApi()
   */
  override def doExecuteApi(): JsonObject = {
    val indexName = request.getParameter("i")
    val json = new JsonObject
    if (!InternalUtils.isBlank(indexName)) {
      json.addProperty(MonadApiConstants.JSON_KEY_TOTAL_RECORD_NUM, searchFacade.getDocumentNum)
      return json
    }
    throw new StarkException("请加入 i (index name)参数", MonadApiExceptionCode.MISSING_RESOURCE_PARAMETER)
  }
}
