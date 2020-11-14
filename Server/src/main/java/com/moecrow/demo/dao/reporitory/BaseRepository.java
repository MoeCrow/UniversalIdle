package com.moecrow.demo.dao.reporitory;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends MongoRepository<T,ID> {
    void batchSave(List<T> obj);

    void batchDelete(List<ID> pks);

    void update(ID id, Object values);

    void increase(ID id, Object values);

    void update(Object keys, Object values);

    T find(Object keys);
}