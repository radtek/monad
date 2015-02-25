// Copyright 2012,2013 The EGF IT Software Department.
// site: http://www.ganshane.com
package monad.sync

import com.google.protobuf.ExtensionRegistry
import monad.core.services.ServiceLifecycleHub
import monad.face.MonadFaceConstants
import monad.face.services.ResourceDefinitionLoaderListener
import monad.protocol.internal.InternalSyncProto
import monad.rpc.services.{ProtobufExtensionRegistryConfiger, RpcServerListener, RpcServerMessageFilter, RpcServerMessageHandler}
import monad.support.services.ServiceLifecycle
import monad.sync.internal.{ResourceImporterManagerImpl, SyncMessageFilter, SyncRpcServerListener}
import monad.sync.services.ResourceImporterManager
import org.apache.tapestry5.ioc.annotations.Contribute
import org.apache.tapestry5.ioc.{Configuration, OrderedConfiguration, ServiceBinder}

/**
 * 本地的同步模块
 * @author jcai
 */
object LocalMonadSyncModule {
  def bind(binder: ServiceBinder) {
    binder.bind(classOf[ResourceImporterManager], classOf[ResourceImporterManagerImpl]).withId("ResourceImporterManager")
  }

  @Contribute(classOf[ResourceDefinitionLoaderListener])
  def provideResourceDefinitionLoaderListener(
                                               configuration: OrderedConfiguration[ResourceDefinitionLoaderListener],
                                               resourceImporterManager: ResourceImporterManager) {
    configuration.add("importer", resourceImporterManager, "after:node")
  }

  @Contribute(classOf[ServiceLifecycleHub])
  def provideServiceLifecycle(configuration: OrderedConfiguration[ServiceLifecycle],
                              importerManager: ResourceImporterManager) {
    configuration.add(MonadFaceConstants.LIFE_IMPORTER, importerManager,
      "after:" + MonadFaceConstants.LIFE_INDEXER,
      "after:" + MonadFaceConstants.LIFE_GROUP_ZOOKEEPER
    )
  }

  @Contribute(classOf[RpcServerMessageHandler])
  def provideSyncMessageHandler(configuration: OrderedConfiguration[RpcServerMessageFilter]) {
    configuration.addInstance("SyncSyncRequest", classOf[SyncMessageFilter.InternalDataSyncRequestHandlerFilter])
  }

  @Contribute(classOf[RpcServerListener])
  def setupSyncServerAddress(configuration: OrderedConfiguration[RpcServerListener]) {
    configuration.addInstance("sync", classOf[SyncRpcServerListener])
  }

  @Contribute(classOf[ExtensionRegistry])
  def provideProtobufCommand(configuration: Configuration[ProtobufExtensionRegistryConfiger]) {
    configuration.add(new ProtobufExtensionRegistryConfiger {
      override def config(registry: ExtensionRegistry): Unit = {
        InternalSyncProto.registerAllExtensions(registry)
      }
    })
  }
}