package com.moecrow.demo.dao.reporitory;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
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

    @Override
    public T findByIdAndType(ID id,Integer type){
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("_id").is("id"), Criteria.where("type").is(type));
        return mongoTemplate.findOne(new Query(criteria), clazz);
    }

    /**
     * @param id  更新主键
     * @param updateFieldMap  key:需要更新的属性  value:对应的属性值
     */
    @Override
    public void update(ID id, Map<String, Object> updateFieldMap) {
        if (updateFieldMap != null && !updateFieldMap.isEmpty()) {
            Criteria criteria = new Criteria("_id").is(id);
            Update update = new Update();
            updateFieldMap.forEach(update::set);
            mongoTemplate.findAndModify(new Query(criteria), update, clazz);
        }
    }

    @Override
    public void modify(ID id, Map<String, Object> modifyFieldMap) {
        if (modifyFieldMap != null && !modifyFieldMap.isEmpty()) {
            Criteria criteria = new Criteria("_id").is(id);
            Document fields = new Document();
            modifyFieldMap.forEach(fields::append);
            BasicUpdate update = new BasicUpdate(new Document().append("$inc", fields));
            mongoTemplate.findAndModify(new Query(criteria), update, clazz);
        }
    }

    /**
     * @param queryParamMap 查询参数
     * @param updateFieldMap  更新参数
     */
    @Override
    public void update(Map<String,Object> queryParamMap, Map<String, Object> updateFieldMap) {
        if (queryParamMap != null && !queryParamMap.isEmpty()){
            List<Criteria> criteriaList = new ArrayList<>();
            for (Map.Entry<String,Object> entry:queryParamMap.entrySet()){
                criteriaList.add(Criteria.where(entry.getKey()).is(entry.getValue()));
            }

            int size = criteriaList.size();
            Criteria[] criterias = new Criteria[size];
            for (int i=0;i<size;i++){
                criterias[i] = criteriaList.get(i);
            }
            Criteria criteria = new Criteria( ).andOperator(criterias);

            if (updateFieldMap != null && !updateFieldMap.isEmpty()) {
                Update update = new Update();
                updateFieldMap.forEach(update::set);
                mongoTemplate.findAndModify(new Query(criteria), update, clazz);
            }

        }
    }

    @Override
    public T find(String key, Object value) {
        return mongoTemplate.findOne(new Query(Criteria.where(key).is(value)), clazz);
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