package monad.face.services

import monad.face.internal.MonadSparseFixedBitSet
import org.apache.lucene.util.SparseFixedBitSet
import org.junit.{Assert, Test}

/**
 *
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-07-14
 */
class BitSetUtilsTest {
  @Test
  def test_bitset1: Unit = {
    val bitSet = new SparseFixedBitSet(10000)
    bitSet.set(1000)
    bitSet.set(2000)
    bitSet.set(3000)
    bitSet.set(4000)
    bitSet.set(5000)
    bitSet.set(6000)
    bitSet.set(7000)
    bitSet.set(8000)
    bitSet.set(9000)

    Assert.assertEquals(9000,bitSet.nextSetBit(8001))
  }
  @Test
  def test_bitset: Unit ={
    val bitSet = new MonadSparseFixedBitSet(1000)
    bitSet.set(188)
    bitSet.set(288)
    bitSet.set(888)
    val bb = BitSetUtils.serialize(bitSet)
    val bitSet2 = BitSetUtils.deserialize(bb)
    Assert.assertTrue(bitSet2.get(888))
    Assert.assertTrue(bitSet2.get(188))
    Assert.assertTrue(bitSet2.get(288))
    Assert.assertFalse(bitSet2.get(388))
  }
}