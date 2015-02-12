// Copyright 2012,2013 The EGF IT Software Department.
// site: http://www.ganshane.com
package monad.core.model

import javax.xml.bind.annotation.{XmlAttribute, XmlElement, XmlType}

/**
 * 针对某一分组的定义
 * @author jcai
 */
@XmlType(name = "GroupConfig")
class GroupConfig {
  /** 组的唯一标示 **/
  @XmlAttribute
  var id: String = _
  /** 组的中文名称 **/
  @XmlElement(name = "cn_name")
  var cnName: String = _
  /** 提供api服务的URL **/
  @XmlElement(name = "api_url")
  var apiUrl: String = _
}
