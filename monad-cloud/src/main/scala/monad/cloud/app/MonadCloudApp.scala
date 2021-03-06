// Copyright 2012,2013,2014,2015,2016 the original author or authors. All rights reserved.
// site: http://www.ganshane.com
package monad.cloud.app

import monad.cloud.MonadCloudModule
import monad.core.MonadCoreSymbols
import monad.core.services.{BootstrapTextSupport, GlobalLoggerConfigurationSupport}
import stark.utils.services.{SystemEnvDetectorSupport, TapestryIocContainerSupport}
import org.slf4j.LoggerFactory

/**
 * 集群服务器
 * @author jcai
 */
object MonadCloudApp
  extends TapestryIocContainerSupport
  with GlobalLoggerConfigurationSupport
  with SystemEnvDetectorSupport
  with BootstrapTextSupport {
  //http://bigtext.org/?font=smslant&text=CLOUD
  final val CLOUD_TEXT_LOGO = "@|green " + """
                                             |  _______   ____  __  _____
                                             | / ___/ /  / __ \/ / / / _ \
                                             |/ /__/ /__/ /_/ / /_/ / // /
                                             |\___/____/\____/\____/____/|@ @|red %s|@ (v @|yellow %s|@)
                                           """.stripMargin

  def main(args: Array[String]) {
    val serverHome = System.getProperty(MonadCoreSymbols.SERVER_HOME, "support")
    System.setProperty(MonadCoreSymbols.SERVER_HOME, serverHome)
    val config = MonadCloudModule.buildMonadCloudConfig(serverHome)
    configLogger(config.logFile, "CLOUD", "monad", "ganshane")

    val logger = LoggerFactory getLogger getClass
    logger.info("Starting cloud server ....")
    val classes = Array[Class[_]](
      Class.forName("monad.cloud.LocalCloudModule"),
      Class.forName("monad.cloud.MonadCloudModule")
    )
    startUpContainer(classes: _*)
    val port = config.port

    /*
    printTextWithNative("cloud@" + port,
      "META-INF/maven/com.ganshane.monad/monad-cloud/version.properties",
      0, logger)
    */
    val version = readVersionNumber("META-INF/maven/com.ganshane.monad/monad-cloud/version.properties")
    printTextWithNative(logger, CLOUD_TEXT_LOGO, "cloud@" + port, version)
    logger.info("Cluster server started ")
    join()
  }
}
