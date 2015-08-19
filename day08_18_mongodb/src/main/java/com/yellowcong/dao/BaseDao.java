package com.yellowcong.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.yellowcong.model.Pager;

public interface BaseDao<T> {

    /** 
     * 通过条件查询实体(集合)
     *  
     * @param query 
     */  
    public List<T> list(Query query) ;  
    /**
     * 获取所有的数据
     * @return
     */
    public List<T> list() ;  
  
    /** 
     * 通过一定的条件查询一个实体 
     *  
     * @param query 
     * @return 
     */  
    public T findOne(Query query) ;  
  
    /** 
     * 通过条件查询更新数据 
     *  
     * @param query 
     * @param update 
     * @return 
     */  
    public void update(Query query, Update update) ; 
    
    /**
     * 通过给定的对象进行更新
     * @param entry
     */
    public void update(T entry);
  
    /** 
     * 保存一个对象到mongodb 
     * 使用的是mongotemplate的insert方法
     * 
     * 添加的时候，会制动增长id 
     * id  必须是 int  id 字段
     *  
     * @param entity 
     * @return 
     */  
    public T save(T entity) ;  
  
    /** 
     * 通过ID获取记录 
     *  
     * @param id 
     * @return 
     */  
    public T load(int id) ;  
  
    /** 
     * 通过ID获取记录,并且指定了集合名(表的意思) 
     *  
     * @param id 
     * @param collectionName 
     *            集合名 
     * @return 
     */  
    public T findById(String id, String collectionName) ;  
      
    /** 
     * 分页查询 
     * @param page 
     * @param query 
     * @return 
     */  
    //public Page<T> findPage(Page<T> page,Query query);  
      
    /** 
     * 求数据总和 ，根据给定的 Query查询
     * @param query 
     * @return 
     */  
    public long count(Query query);
    
    /**
     * 获取所有数据条数
     * @return
     */
    public long count();
    
    /**
     * 通过id删除数据
     * @param id
     */
    public void delete(int id);
    /**
     * 一次删除多条数据
     * @param ids
     */
    public void deletes(int [] ids);
    /**
     * 通过传递过来的 对象来删除数据
     * @param entry
     */
    public void delete(T entry);
    /**
     * 带分页查询
     * @return
     */
    public Pager<T>  queryByPager();
    /**
     * 不带分页的查询方法
     * @param query
     * @return
     */
    public Pager<T>  queryByPager(Query query);
}
