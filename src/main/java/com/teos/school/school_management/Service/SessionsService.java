package com.teos.school.school_management.Service;

import java.util.Optional;
import java.util.List;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.teos.school.school_management.Model.SesionesAlumnos;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

public class SessionsService {
    private static final String REGION = "us-east-1";
    private static final String ACCESS_KEY = "";
    private static final String SECRET_KEY = "";
    private static final String SESSION_TOKEN = "";


    private static AmazonDynamoDB createDynamoDBClient() {
        BasicSessionCredentials awsCreds = new BasicSessionCredentials(ACCESS_KEY, SECRET_KEY, SESSION_TOKEN);
        return AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(REGION)
                .build();
    }

    public static void createRecord(SesionesAlumnos sesionesAlumnos) {
        try {
            AmazonDynamoDB client = createDynamoDBClient();
            DynamoDBMapper mapper = new DynamoDBMapper(client);
            mapper.save(sesionesAlumnos);
        } catch (ResourceNotFoundException e) {
            System.err.println("Be sure that it exists and that you've typed its name correctly!");
            System.exit(1);
        } catch (AmazonServiceException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public static Optional<SesionesAlumnos> getRecord(String session) {
        try {
            AmazonDynamoDB client = createDynamoDBClient();
            DynamoDBMapper mapper = new DynamoDBMapper(client);

            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
            scanExpression.addFilterCondition(
                "sessionString", 
                new Condition()
                    .withComparisonOperator("EQ")
                    .withAttributeValueList(new AttributeValue().withS(session))
            );

            List<SesionesAlumnos> result = mapper.scan(SesionesAlumnos.class, scanExpression);
            if (result.isEmpty()) {
                return Optional.empty();
            }

            return Optional.of(result.get(0));
        } catch (ResourceNotFoundException e) {
            System.err.println("Be sure that it exists and that you've typed its name correctly!");
            System.exit(1);
        } catch (AmazonServiceException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        return Optional.empty();
    }

    public static void updateRecord(SesionesAlumnos session){
        try {
            AmazonDynamoDB client = createDynamoDBClient();
            DynamoDBMapper mapper = new DynamoDBMapper(client);
            mapper.save(session);
        } catch (ResourceNotFoundException e) {
            System.err.println("Be sure that it exists and that you've typed its name correctly!");
            System.exit(1);
        } catch (AmazonServiceException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}