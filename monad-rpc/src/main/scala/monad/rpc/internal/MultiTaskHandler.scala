package monad.rpc.internal

import java.util.concurrent.{ConcurrentHashMap, CountDownLatch, Future, TimeUnit}

import monad.protocol.internal.CommandProto.BaseCommand
import monad.rpc.services.RpcClientMerger
import org.jboss.netty.channel.Channel

/**
 * 针对多个服务器进行请求处理
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-02-26
 */
object MultiTaskHandler {
  private val tasks = new ConcurrentHashMap[Long, InternalRequestMerger[_]]()

  def findTask(taskId: Long) = tasks.get(taskId)

  def createMergerTask[T](taskId: Long, serverSize: Int, rpcMerger: RpcClientMerger[T]) = {
    val future = new InternalRequestMerger[T](rpcMerger)
    tasks.put(taskId, future)
    future.startRequest(taskId, serverSize)

    future
  }

  private[internal] class InternalRequestMerger[T](rpcMerger: RpcClientMerger[T]) extends Future[T] {
    private var cancelled = false
    private var countDownLatch: CountDownLatch = _


    /**
     * 对获得的消息进行处理
     */
    def handle(commandRequest: BaseCommand, channel: Channel): Unit = rpcMerger.handle(commandRequest, channel)

    override def get(): T = {
      countDownLatch.await()
      rpcMerger.get
    }

    /**
     * 开始请求
     * @param taskId 任务ID号
     * @param serverSize 服务器的个数
     */
    def startRequest(taskId: Long, serverSize: Int): Unit = {
      countDownLatch = new CountDownLatch(serverSize)
      tasks.put(taskId, this)
    }


    override def cancel(mayInterruptIfRunning: Boolean): Boolean = {
      cancelled = true

      cancelled
    }

    override def isCancelled: Boolean = {
      cancelled
    }


    override def get(timeout: Long, unit: TimeUnit): T = {
      //TODO 增加超时设置
      countDownLatch.await(timeout, unit)
      rpcMerger.get
    }

    override def isDone: Boolean = {
      countDownLatch.getCount == 0
    }

    def countDown(): Unit = {
      countDownLatch.countDown()
    }
  }

}


