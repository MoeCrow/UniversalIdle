package com.moecrow.demo.dao.reporitory;

import com.moecrow.demo.dao.entity.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author willz
 * @date 2020.11.13
 */
@CacheConfig(cacheNames = {"myCache"})
public interface UserRepository extends BaseRepository<User, String> {
    @Override
    @Cacheable
    User find(String id);

    @Override
    @CachePut(key = "#id")
    User update(String id, User values);

    @Override
    @CachePut(key = "#id")
    User increase(String id, User values);
}
