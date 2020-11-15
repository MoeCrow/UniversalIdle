package com.moecrow.demo.dao.reporitory;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends MongoRepository<T,ID> {
    void batchSave(List<T> obj);

    void batchDelete(List<ID> pks);

    T update(ID id, T values);

    T increase(ID id, T values);

    void update(T keys, T values);

    T find(T keys);

    T find(ID id);
}