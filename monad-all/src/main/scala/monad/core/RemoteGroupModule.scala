// Copyright 2012,2013 The EGF IT Software Department.
// site: http://www.ganshane.com
package monad.core

import monad.core.services.{GroupServerApi, GroupZookeeperTemplate, ServiceLifecycleHub}
import monad.face.MonadFaceConstants
import monad.group.internal.remote.RemoteGroupServiceApiImpl
import monad.support.services.ServiceLifecycle
import org.apache.tapestry5.ioc.annotations.Contribute
import org.apache.tapestry5.ioc.{OrderedConfiguration, ServiceBinder}

/**
 *
 * @author jcai
 */
object RemoteGroupModule {
  def bind(binder: ServiceBinder) {
    binder.bind(classOf[GroupZookeeperTemplate]).withId("GroupZookeeperTemplate")
    //binder.bind(classOf[HttpRestClient],classOf[HttpRestClientImpl]).withId("HttpRestClient")
    binder.bind(classOf[GroupServerApi], classOf[RemoteGroupServiceApiImpl]).withId("GroupServerApi")
  }

  @Contribute(classOf[ServiceLifecycleHub])
  def provideServiceLifecycle(configuration: OrderedConfiguration[ServiceLifecycle],
                              groupZookeeperTemplate: GroupZookeeperTemplate) {
    configuration.add(MonadFaceConstants.LIFE_GROUP_ZOOKEEPER, groupZookeeperTemplate,
      "after:" + MonadFaceConstants.LIFE_GROUP_NOTIFIER,
      "after:" + MonadFaceConstants.LIFE_CLOUD
    )
  }
}
