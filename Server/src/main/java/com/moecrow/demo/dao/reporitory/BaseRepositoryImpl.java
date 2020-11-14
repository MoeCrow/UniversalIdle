package com.moecrow.demo.dao.reporitory;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;


public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleMongoRepository<T,ID> implements BaseRepository<T,ID> {

    protected final MongoOperations mongoTemplate;

    protected final MongoEntityInformation<T, ID> entityInformation;

    private Class<T> clazz;

    public BaseRepositoryImpl(MongoEntityInformation<T, ID> metadata, MongoOperations mongoOperations) {
        super(metadata, mongoOperations);
        this.mongoTemplate=mongoOperations;
        this.entityInformation = metadata;
        clazz = entityInformation.getJavaType();
    }

    private void forEachValidFields(Object obj, BiConsumer<? super String, ? super Object> action) {
        JSONObject json = (JSONObject) JSONObject.toJSON(obj);
        json.forEach((k, v)-> {
            if (v != null) {
                action.accept(k, v);
            }
        });
    }

    @Override
    public void update(ID id, Object values) {
        Criteria criteria = new Criteria("_id").is(id);

        Update update = new Update();
        forEachValidFields(values, update::set);

        mongoTemplate.findAndModify(new Query(criteria), update, clazz);
    }

    @Override
    public void increase(ID id, Object values) {
        Criteria criteria = new Criteria("_id").is(id);

        Update update = new Update();
        forEachValidFields(values, (k, v)->update.inc(k, (Number)v));

        mongoTemplate.findAndModify(new Query(criteria), update, clazz);
    }

    @Override
    public void update(Object keys, Object values) {
        List<Criteria> criteriaList = new ArrayList<>();
        forEachValidFields(keys, (k, v)->criteriaList.add(Criteria.where(k).is(v)));
        Criteria criteria = new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));

        Update update = new Update();
        forEachValidFields(values, update::set);

        mongoTemplate.findAndModify(new Query(criteria), update, clazz);
    }

    @Override
    public T find(Object keys) {
        List<Criteria> criteriaList = new ArrayList<>();
        forEachValidFields(keys, (k, v)->criteriaList.add(Criteria.where(k).is(v)));
        Criteria criteria = new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));

        return mongoTemplate.findOne(new Query(criteria), clazz);
    }

    @Override
    public void batchSave(List<T> obj) {
        if (obj != null && obj.size() != 0) {
            //过滤掉集合中的空对象
            obj = obj.stream().filter(Objects::nonNull).collect(Collectors.toList());
            saveAll(obj);
        }
    }

    @Override
    public void batchDelete(List<ID> pks) {
        if (pks != null && pks.size() != 0) {
            Query query = new Query(Criteria.where("_id").in(pks));
            mongoTemplate.findAllAndRemove(query,clazz);
        }
    }
}