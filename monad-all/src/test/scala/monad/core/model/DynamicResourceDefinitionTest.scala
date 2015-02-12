// Copyright 2012,2013 The EGF IT Software Department.
// site: http://www.ganshane.com
package monad.core.model

import monad.face.model.DynamicResourceDefinition
import monad.support.services.XmlLoader
import org.junit.{Assert, Test}

/**
 *
 * @author jcai
 */

class DynamicResourceDefinitionTest {
  @Test
  def test_parse() {
    val drd = XmlLoader.parseXML[DynamicResourceDefinition](
      getClass.getResourceAsStream("/dynamics.xml"), None)
    Assert.assertEquals(6, drd.properties.size())

    val pro = drd.properties.get(0)
    Assert.assertEquals("xm", pro.traitName)
    Assert.assertEquals("姓名", pro.cnName)
  }
}