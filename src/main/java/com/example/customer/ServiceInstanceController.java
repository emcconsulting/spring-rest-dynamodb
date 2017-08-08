package com.example.customer;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;

@RestController
@RequestMapping("/si")
public class ServiceInstanceController {
	
	  @Autowired
	  private DynamoDBMapper dbMapper;

	  @Autowired
	  private AmazonDynamoDB dynamoDB;

	@Autowired
	  private ServiceInstanceRepo repository;
	
	@RequestMapping(path = "/createtable", method = RequestMethod.GET)
	  public ResponseEntity<String> initTable() {
		createTable();
		return new ResponseEntity<>("test", CREATED);
	}
		
	@RequestMapping(path = "/add", method = RequestMethod.GET)
	  public ResponseEntity<String> customeradd() {
		ServiceInstance c = new ServiceInstance();
		  c.setServiceInstanceId("100");
		  c.setAddressId("101");
		  c.setEmail("a@b.com");
	    repository.save(c);
	    
	    ServiceInstance c1 = new ServiceInstance();
		  c1.setServiceInstanceId("200");
		  c1.setAddressId("201");
		  c1.setEmail("c@d.com");
	    repository.save(c1);
	    
	    ServiceInstance c2 = new ServiceInstance();
		  c2.setServiceInstanceId("300");
		  c2.setAddressId("201");
		  c2.setEmail("d@e.com");
	    repository.save(c2);
	    
	    return new ResponseEntity<>("test", CREATED);
	       
	  }
	
	 @RequestMapping(path = "/{id}", method = RequestMethod.GET)
	  public ResponseEntity<ServiceInstance> read(@PathVariable String id) {

		 ServiceInstance si =  repository.read(id);
	    return new ResponseEntity<>(si, OK);
	    
	  }
	 
	 @RequestMapping(path = "email/{email}", method = RequestMethod.GET)
	  public ResponseEntity<List<ServiceInstance>> readByEmail(@PathVariable String email) {
		 email = email + ".com";
		 List<ServiceInstance> list =  repository.readByEmail(email);
	    return new ResponseEntity<>(list, OK);
	    
	  }
	 
	 @RequestMapping(path = "addressId/{addrId}", method = RequestMethod.GET)
	  public ResponseEntity<List<ServiceInstance>> readByAddress(@PathVariable String addrId) {
		 List<ServiceInstance> list =  repository.readByAddress(addrId);
	    return new ResponseEntity<>(list, OK);
	    
	  }
	 @RequestMapping(path = "delete", method = RequestMethod.GET)
	 public void deleteTable(){
		 dbMapper.generateDeleteTableRequest(ServiceInstance.class);
	 }
	 
	  public void createTable() {

	    CreateTableRequest request = dbMapper
	        .generateCreateTableRequest(ServiceInstance.class)
	        .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
	    request.getGlobalSecondaryIndexes().get(0).setProvisionedThroughput(new ProvisionedThroughput(10l, 10l));
	    request.getGlobalSecondaryIndexes().get(1).setProvisionedThroughput(new ProvisionedThroughput(10l, 10l));
	    
	    request.getGlobalSecondaryIndexes().get(0).setProjection(new Projection().withProjectionType("ALL"));
	    request.getGlobalSecondaryIndexes().get(1).setProjection(new Projection().withProjectionType("ALL"));
	    try {
	      DescribeTableResult result = dynamoDB.describeTable(request.getTableName());
	    } catch (ResourceNotFoundException expectedException) {
	      CreateTableResult result = dynamoDB.createTable(request);
	      System.out.println("Table creation triggered {}, {}" +  request.getTableName() + result.getTableDescription().getTableStatus());
	    }
	  }
}
