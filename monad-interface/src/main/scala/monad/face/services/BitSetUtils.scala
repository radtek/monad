package monad.face.services

import java.io.{ByteArrayOutputStream, OutputStream}
import java.nio.ByteBuffer

import monad.face.internal.MonadSparseFixedBitSet

/**
 * bitset utils
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-07-14
 */
object BitSetUtils {
  def serialize(sparseFixedBitSet: MonadSparseFixedBitSet):ByteBuffer={
    val os = new ByteArrayOutputStream(102400)
    serialize(sparseFixedBitSet,os)
    ByteBuffer.wrap(os.toByteArray)
  }
  def serialize(sparseFixedBitSet: MonadSparseFixedBitSet,outputStream:OutputStream){
    if(sparseFixedBitSet == null)
      new MonadSparseFixedBitSet(1).serialize(outputStream)
    else
      sparseFixedBitSet.serialize(outputStream)
  }
  def deserialize(bytes:ByteBuffer):MonadSparseFixedBitSet={
    MonadSparseFixedBitSet.deserialize(bytes)
  }
}