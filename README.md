# Spring-Data-MongoDB
我们的这个是关于Spring Data MongoDB  的学习和配置，其中我实现了分页和BaseDao的接口和方法，可以供大家学习和参考


##BaseDao接口
package com.yellowcong.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.yellowcong.model.Pager;

/**
 * 建立公共的BaseDao接口，所有的Dao都需要继承这个类
 * @author 狂飙のyellowcong
 * 2015年8月19日
 *
 * @param <T>
 */
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


##ApplicatonContext.xml配置


<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:mongo="http://www.springframework.org/schema/data/mongo"
	xsi:schemaLocation="http://www.springframework.org/schema/beans  
            http://www.springframework.org/schema/beans/spring-beans-3.0.xsd  
            http://www.springframework.org/schema/data/mongo       
            http://www.springframework.org/schema/data/mongo/spring-mongo-1.0.xsd   
            http://www.springframework.org/schema/context  
            http://www.springframework.org/schema/context/spring-context-3.0.xsd">
	
	<!-- 标注扫描的类 -->
	<!-- 开启注解 -->
	<context:annotation-config />
	<context:component-scan base-package="com.yellowcong" />
	
	<!-- 配置数据库的地址和端口 -->
	<mongo:mongo host="127.0.0.1" port="27017" >
		<!-- 设定连接属性 -->
		<mongo:options connections-per-host="8"
                   threads-allowed-to-block-for-connection-multiplier="4"
                   connect-timeout="1000"
                   max-wait-time="1500"
                   auto-connect-retry="true"
                   socket-keep-alive="true"
                   socket-timeout="1500"
                   slave-ok="true"
                   write-number="1"
                   write-timeout="0"
                   write-fsync="true"/>
	</mongo:mongo>
	
	<!-- mongo的工厂，通过它来取得mongo实例,dbname为mongodb的数据库名，没有的话会自动创建 -->
	<!-- 基于这个 properties配置有问题，不知道啥原因 -->
	<!-- 
	<context:property-placeholder location="classpath:jdbc-config-local.properties" />
	 -->
	 
	 <!-- 如果没有创建数据库，就会默认创建数据库
	   username="root"  
	   password="root"
	  -->
	<mongo:db-factory id="mongoDbFactory"  
	                  host="127.0.0.1"  
	                  port="27017"  
	                  dbname="test"  
	                
	                  mongo-ref="mongo"/>  
	<!-- mongodb的主要操作对象，所有对mongodb的增删改查的操作都是通过它完成
		设定我们的 mongoDbFactory 对象
	 -->
	<bean id="mongoTemplate" class="org.springframework.data.mongodb.core.MongoTemplate">
		<constructor-arg name="mongoDbFactory" ref="mongoDbFactory" />
	</bean>
</beans>  
 

 
 
 ##pom.xml配置
 <?xml version="1.0" encoding="UTF-8"?>
 <project xmlns="http://maven.apache.org/POM/4.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.yellowcong.mongodb</groupId>
  <artifactId>day08_18_mongodb</artifactId>
  <packaging>jar</packaging>
  <version>0.0.1-SNAPSHOT</version>
  <name>day08_18_mongodb</name>
  <url>http://maven.apache.org</url>
  
    <repositories>  
    <!-- 设定工厂， 这个工厂是 spring的 -->
    <repository>
        <id>spring-snapshots</id>
        <name>Spring Snapshots</name>
        <url>http://repo.spring.io/snapshot</url>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>  
  </repositories> 
  <dependencies>
  
  <!-- 导入注解对象 -->
  <dependency>
	<groupId>javax.annotation</groupId>
	<artifactId>javax.annotation-api</artifactId>
	<version>1.2</version>
</dependency>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.10</version>
      <scope>test</scope>
      </dependency>
      
      <!-- 配置mongodb 1.7.2 释放版本 -->
   <dependency>
        <groupId>org.springframework.data</groupId>
        <artifactId>spring-data-mongodb</artifactId>
        <version>1.7.2.RELEASE</version>
    </dependency>
    
	
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-aop</artifactId>
		<version>4.1.6.RELEASE</version>
	</dependency>
	
	<!-- Spring测试包 -->
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-test</artifactId>
		<version>4.1.6.RELEASE</version>
	</dependency>
	
	
	<!-- 导入bean的 操作工具,方便操作反射 -->
	<dependency>
	<groupId>commons-beanutils</groupId>
	<artifactId>commons-beanutils</artifactId>
	<version>1.9.2</version>
	</dependency>
	
		<!-- 加入log4j -->
  	<dependency>
		<groupId>log4j</groupId>
		<artifactId>log4j</artifactId>
		<version>1.2.16</version>
	</dependency>
  </dependencies>
  
  
</project>
