#ifndef MONAD_SDK_IMPL_H_
#define MONAD_SDK_IMPL_H_

#include <regex>
#include "leveldb/db.h"
#include "roaring/roaring.h"

#include "monad_sdk_code.h"

namespace monad{
  class MonadSDK{
  public:
    MonadSDK(const char* path,const uint32_t cache_ram=50 * 1024 * 1024);
    MONAD_CODE PutCollection(uint32_t region_id,const char* data,const size_t size);
    MONAD_CODE PutId(const char* id_card ,size_t size);
    MONAD_CODE PutKV(const leveldb::Slice& key,const leveldb::Slice& value);
    MONAD_CODE GetKV(const leveldb::Slice& key,std::string* value);
    bool ContainId(const char *id_card,size_t size);
    static leveldb::Status Destroy(const char* path);
    virtual ~MonadSDK();

  private:
    uint32_t CalculateDays(std::smatch& results);
    void AddCache(uint32_t region_id,roaring_bitmap_t* value);
    void RemoveCache(uint32_t region_id);
    roaring_bitmap_t* GetBitmapFromCache(uint32_t region_id);
    void ClearCache();

    leveldb::DB* db;
    uint32_t y1900_days;
    uint32_t max_cache_ram;
  };
}
#endif //MONAD_SDK_IMPL_H_