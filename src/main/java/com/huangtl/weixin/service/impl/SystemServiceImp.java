package com.huangtl.weixin.service.impl;

import com.huangtl.weixin.service.SystemService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Service("systemService")
public class SystemServiceImp implements SystemService {

    @Override
    public Integer getAllDbTableSize() {
        return null;
    }

    @Override
    public <T> Serializable save(T entity) {
        return null;
    }

    @Override
    public <T> void saveOrUpdate(T entity) {

    }

    @Override
    public <T> void delete(T entity) {

    }

    @Override
    public <T> void batchSave(List<T> entitys) {

    }

    @Override
    public <T> T get(Class<T> class1, Serializable id) {
        return null;
    }

    @Override
    public <T> T getEntity(Class entityName, Serializable id) {
        return null;
    }

    @Override
    public <T> T findUniqueByProperty(Class<T> entityClass, String propertyName, Object value) {
        return null;
    }

    @Override
    public <T> List<T> findByProperty(Class<T> entityClass, String propertyName, Object value) {
        return null;
    }

    @Override
    public <T> List<T> loadAll(Class<T> entityClass) {
        return null;
    }

    @Override
    public <T> void deleteEntityById(Class entityName, Serializable id) {

    }

    @Override
    public <T> void deleteAllEntitie(Collection<T> entities) {

    }

    @Override
    public <T> void updateEntitie(T pojo) {

    }

    @Override
    public <T> List<T> findByQueryString(String hql) {
        return null;
    }

    @Override
    public int updateBySqlString(String sql) {
        return 0;
    }

    @Override
    public <T> List<T> findListbySql(String query) {
        return null;
    }

    @Override
    public <T> List<T> findByPropertyisOrder(Class<T> entityClass, String propertyName, Object value, boolean isAsc) {
        return null;
    }

    @Override
    public <T> List<T> getList(Class clas) {
        return null;
    }

    @Override
    public <T> T singleResult(String hql) {
        return null;
    }

    @Override
    public <T> List<T> findHql(String hql, Object... param) {
        return null;
    }

    @Override
    public <T> List<T> executeProcedure(String procedureSql, Object... params) {
        return null;
    }
}
