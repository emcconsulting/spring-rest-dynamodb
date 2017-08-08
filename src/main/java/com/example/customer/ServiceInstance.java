package com.example.customer;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "ServiceInstance")
public class ServiceInstance {
	
	@DynamoDBHashKey(attributeName = "serviceInstanceId")
	private String serviceInstanceId;
	
	@DynamoDBIndexHashKey(globalSecondaryIndexName="adrIdx")
	private String addressId;
	
	@DynamoDBIndexHashKey(globalSecondaryIndexName="emailIdx")
	private String email;

	public String getServiceInstanceId() {
		return serviceInstanceId;
	}

	public void setServiceInstanceId(String serviceInstanceId) {
		this.serviceInstanceId = serviceInstanceId;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
