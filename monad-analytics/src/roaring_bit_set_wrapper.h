// Copyright 2016 the original author or authors. All rights reserved.
// site: http://www.ganshane.com
#ifndef MONAD_ROARING_BIT_SET_WRAPPER_H_
#define MONAD_ROARING_BIT_SET_WRAPPER_H_
#include <assert.h>

#include <vector>
#include "bit_set_wrapper.h"
#include "bit_set_region.h"
#include "roaring_bit_set.h"
#include "top_bit_set.h"

namespace monad {
  template<typename T>
  struct BitSetRegion;

  template<typename WRAPPER, typename BIT_SET>
  class BitSetWrapperIterator;

  class RoaringBitSetWrapper;
  class TopBitSetWrapper;

  template<typename T>
  class BitSetWrapperHolder;

  class RoaringBitSetWrapper:public BitSetWrapper<RoaringBitSetWrapper,RoaringBitSet> {
  public:
    RoaringBitSetWrapper();
    void NewSeg(int32_t region, int32_t num_words);
    uint32_t NewSeg(int32_t region,const char* bb);
    uint32_t NewSegFromJava(int32_t region,int8_t * long_byte_data){return NewSeg(region, (const char*) long_byte_data);}
    void FastSet(int32_t index);
    void Set(int32_t index);
    void Optimize();
    void Commit();
    bool FastGet(int32_t index);
    void SetWeight(int32_t weight);
    int32_t BitCount();
    /**
     * 得到段的数量
     */
    uint32_t SegCount();
    /**
     * 读取集合中的前N个
     * @param result 传入的结果的对象
     * @param n 取前N个
     * @return 实际取到的个数
     */
    monad::RegionDoc** Top(int32_t n, int32_t& data_len);
    /**
     * 通过给定的 TopBitSetWrapper来创建一个BitSEtWrapper
     * @param wrapper  topBitSetWrapper
     * @return RoaringBitSetWrapper
     */
    static RoaringBitSetWrapper* FromTopBitSetWrapper(TopBitSetWrapper* wrapper);

  private:
    BitSetWrapperIterator<RoaringBitSetWrapper, RoaringBitSet>* Iterator();
    friend class BitSetWrapper<RoaringBitSetWrapper,RoaringBitSet>;
  };
}//namespace monad
#endif //MONAD_ROARING_BIT_SET_WRAPPER_H_
