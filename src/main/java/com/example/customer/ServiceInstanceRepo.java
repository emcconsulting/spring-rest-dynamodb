package com.example.customer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;

@Repository
public class ServiceInstanceRepo {
	 @Autowired
	  private DynamoDBMapper dbMapper;
	 
	 
	 public void save(ServiceInstance si) {

	    System.out.println("in save");
	    dbMapper.save(si);
	  }
	 
	 public ServiceInstance read(String id) {

		    return dbMapper.load(ServiceInstance.class, id);
	 }
	 
	 public List<ServiceInstance> readByEmail(String email) {

		 final ServiceInstance si = new ServiceInstance();
		 si.setEmail(email);
		 final DynamoDBQueryExpression<ServiceInstance> queryExpression = new DynamoDBQueryExpression<>();
		 queryExpression.setHashKeyValues(si);
		 queryExpression.setIndexName("emailIdx");
		 queryExpression.setConsistentRead(false);   // cannot use consistent read on GSI
		 final PaginatedQueryList<ServiceInstance> results = dbMapper.query(ServiceInstance.class,
		                                                        queryExpression);
		 
		 Iterator<ServiceInstance> iter = results.iterator();
		 List<ServiceInstance> list = new ArrayList<ServiceInstance>();
		 while(iter.hasNext()){
			 list.add(iter.next());
		 }
		 
		 return list;

	 }
	 
	 
	 public List<ServiceInstance> readByAddress(String addrId) {

		 final ServiceInstance si = new ServiceInstance();
		 si.setAddressId(addrId);
		 final DynamoDBQueryExpression<ServiceInstance> queryExpression = new DynamoDBQueryExpression<>();
		 queryExpression.setHashKeyValues(si);
		 queryExpression.setIndexName("adrIdx");
		 queryExpression.setConsistentRead(false);   // cannot use consistent read on GSI
		 final PaginatedQueryList<ServiceInstance> results = dbMapper.query(ServiceInstance.class,
		                                                        queryExpression);
		 
		 Iterator<ServiceInstance> iter = results.iterator();
		 List<ServiceInstance> list = new ArrayList<ServiceInstance>();
		 while(iter.hasNext()){
			 list.add(iter.next());
		 }
		 
		 return list;

	 }
	 
	 
}
