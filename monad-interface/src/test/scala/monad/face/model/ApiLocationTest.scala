// Copyright 2012,2013,2015,2016 the original author or authors. All rights reserved.
// site: http://www.ganshane.com
package monad.face.model

import stark.utils.services.XmlLoader
import org.junit.{Assert, Test}

/**
 * api location test
 * @author jcai
 */
class ApiLocationTest {
  @Test
  def test_parse() {
    val location = XmlLoader.parseXML[ApiLocation](getClass.getResourceAsStream("/location.xml"), None)
    Assert.assertEquals(1, location.apis.size())
    val api = location.apis.get(0)
    Assert.assertEquals("南昌", api.name)
    Assert.assertEquals("http://test", api.url)
  }
}
