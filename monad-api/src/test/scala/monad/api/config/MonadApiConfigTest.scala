// Copyright 2012,2013,2015 the original author or authors. All rights reserved.
// site: http://www.ganshane.com
package monad.api.config

import monad.support.services.XmlLoader
import org.junit.{Assert, Test}

/**
 *
 * @author jcai
 */
class MonadApiConfigTest {
  @Test
  def test_parse() {
    val config = XmlLoader.parseXML[MonadApiConfig](getClass.getResourceAsStream("/test-monad-api.xml"), None)
    Assert.assertEquals(config.groupApi, "http://localhost:9080/api")
  }
}