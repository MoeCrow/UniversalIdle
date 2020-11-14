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

    void update(ID id, Map<String, Object> updateFieldMap);

    void modify(ID id, Map<String, Object> modifyFieldMap);

    void update(Map<String,Object> queryParamMap, Map<String, Object> updateFieldMap);

    T findByIdAndType(ID id,Integer type);

    T find(String key, Object value);
}