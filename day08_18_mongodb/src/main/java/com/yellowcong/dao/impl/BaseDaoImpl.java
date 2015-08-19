package com.yellowcong.dao.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.yellowcong.dao.BaseDao;
import com.yellowcong.model.Pager;
import com.yellowcong.model.SystemContext;
/**
 * 这个BaseDaoImpl实现了MongoDb大部分有用的方法，我们可以好好学习学习，我花了两天的时间解决的这些问题
 * @author 狂飙のyellowcong
 * 2015年8月19日
 *
 * @param <T>
 */

public class BaseDaoImpl<T> implements BaseDao<T> {
	private Class<T> clazz;

	// 获取我们的class对象
	private Class<T> getClazz() {
		if (this.clazz == null) {
			ParameterizedType type = (ParameterizedType) getClass()
					.getGenericSuperclass();
			this.clazz = ((Class) type.getActualTypeArguments()[0]);
		}
		return this.clazz;
	}

	/**
	 * spring mongodb　集成操作类　
	 */
	private MongoTemplate mongoTemplate;

	@Resource(name = "mongoTemplate")
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	// 提供mongoTempldate获取更多的操作
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public List<T> list(Query query) {
		return this.mongoTemplate.find(query, this.getClazz());
	}

	public T findOne(Query query) {
		return this.mongoTemplate.findOne(query, this.getClazz());
	}

	public void update(Query query, Update update) {
		mongoTemplate.findAndModify(query, update, this.getClazz());
	}

	/**
	 * 添加实体 
	 */
	public T save(T entity) {
		try {
			// save方法会便利表单，如果存在就会更新
			// mongoTemplate.save(entity);

			// insert 方法不需要遍历表单
			// 通过Query获取长度
			// long coun = mongoTemplate.count(query, this.getClazz());
			// 通过DBCollection获取数据长度
			// long count =
			// mongoTemplate.getCollection(mongoTemplate.getCollectionName(getClazz())).count();
			
			this.getId();
			this.getClazz().getMethod("setId", Integer.TYPE)
					.invoke(entity, this.getId());
			//这个地方有点问题，我们如果查询的数据的数目和最大id小的问题
			
			
			// 获取对象，然后加上id
			mongoTemplate.insert(entity);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return entity;
	}
	
	/**
	 * 通过id来加载数据对象
	 */
	public T load(int id) {
		return mongoTemplate.findById(id, this.getClazz());
	}
	
	/**
	 * 通过id来查找我们的数据对象
	 */
	public T findById(String id, String collectionName) {
		return mongoTemplate.findById(id, this.getClazz(), collectionName);
	}

	/**
	 * 这个是通过Query查询
	 */
	public long count(Query query) {
		return mongoTemplate.count(query, this.getClazz());
	}

	/**
	 * 获取本集合中的条数
	 */
	public long count() {
		return count(new Query(Criteria.where("").is("")));
	}

	// update就像一个 集合一样，添加数据
	public void delete(int id) {
		mongoTemplate.remove(new Query(Criteria.where("_id").is(id)),
				this.getClazz());
	}
	/**
	 * 直接删除传递过来的实体对象
	 */
	public void delete(T entry) {
		mongoTemplate.remove(entry);
	}
	
	/**
	 * 更新我们的实体类
	 */
	public void update(T entry) {
		try {
			// 更新操作，我们需要先获取到我们的 id 获取到数据库中的数据 ，然后更新
			// int id =
			// Integer.parseInt(this.getClazz().getDeclaredMethod("getId").invoke(entry).toString());
			// System.out.println(id);
			// 通过BeanUtils直接过去数据
			int id = Integer.parseInt(BeanUtils.getProperty(entry, "id")
					.toString());
			// 获取所有的字段，然后设定到update中
			// 将所有获取的数据 放下Update中
			Update update = new Update();
			Field[] fields = this.getClazz().getDeclaredFields();
			for (Field field : fields) {
				String fieldName = field.getName();
				// 其中我们不能将 id这个属性添加到里面,因为id是不可以更新的
				if (fieldName != null && !"id".equals(fieldName)) {
					// 使用set更新器,如果没有就会添加，有就会更新
					update.set(fieldName,
							BeanUtils.getProperty(entry, fieldName));
				}
			}
			// 然后更新我们的对象
			this.mongoTemplate.updateFirst(
					new Query(Criteria.where("_id").is(id)), update,
					this.getClazz());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * 删除多条数据
	 */
	public void deletes(int[] ids) {
		for (int id : ids) {
			delete(id);
		}
	}
	/**
	 * 获取集合中所有的数据
	 */
	public List<T> list() {
		return this.mongoTemplate.findAll(getClazz());
	}
	
	/**
	 * queryByPager()方法的重载
	 */
	public Pager<T> queryByPager() {
		Query query = Query.query(Criteria.where("").is(""));
		return this.queryByPager(query);
	}

	public Pager<T> queryByPager(Query query) {
		// 获取页面的大小和 页面
		Integer pageSize = SystemContext.getPageSize();
		Integer pageNow = SystemContext.getPageNow();
		String order = SystemContext.getOrder();
		String sort =SystemContext.getSort();
		//获取条数，由于不同的条件，所产生的条数是不一样的
		int count = Integer.parseInt(this.count(query) + "");
		Sort mySort = null;
		// 每页显示默认为10条数据
		if (pageSize == null || pageSize <= 0) {
			pageSize = 10;
		}
		if (pageNow == null || pageNow < 0) {
			pageNow = 1;
		}
		if(order == null || "".equals(order.trim())){
			order = "id";
		}
		
		
		int offset = (pageNow - 1) * pageSize;

		// 页面大小是多少
		query.limit(pageSize);
		// 从多少条开始
		query.skip(offset);
		
		//通过Sort这个 类可以完成排序的问题
		if(sort != null && sort.equals("desc")){
			mySort = new Sort(Direction.DESC,order);
		}else{
			mySort = new Sort(Direction.ASC,order);
		}
		
		//设定排序
		query.with(mySort);
		List<T> datas = this.mongoTemplate.find(query, getClazz());
		// 设定数据
		Pager<T> pager = new Pager<T>();
		pager.setPageSize(pageSize);
		pager.setPageNow(pageNow);
		//设定页面的数
		pager.setPageCount((count-1)/pageSize+1);
		pager.setRowCount(count);
		pager.setData(datas);
		
		return pager;
	}
	
	/**
	 * 获取集合中的最大的id ,解决不能自动增长的问题
	 * @return 
	 */
	private int getId(){
		
		try {
			int counts = Integer.parseInt(this.count()+"");
			int lastId = 0;
			
			//查询最后一条数据
			Query query = new Query();
			query.limit(1);
			query.skip(counts-1);
			List<T> objs = this.mongoTemplate.find(query, getClazz());
			if(objs != null && objs.size() >0){
				Object obj = objs.get(0);
				lastId = Integer.parseInt(BeanUtils.getProperty(obj, "id")+"");
			}
			
			return lastId+1;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
