package com.Hindol.DynamoDB.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfiguration {

    @Value("${aws.dynamoDB.access.key}")
    private String accessKey;

    @Value("${aws.dynamoDB.secret.key}")
    private String secretKey;

    @Value("${aws.dynamoDB.service.endpoint}")
    private String SERVICE_ENDPOINT;

    @Value("${aws.dynamoDB.signing.region}")
    private String region;




    @Bean
    public DynamoDBMapper mapper() {
        return new DynamoDBMapper(awsDynamoDBConfiguration());
    }

    private AmazonDynamoDB awsDynamoDBConfiguration() {
        return AmazonDynamoDBClientBuilder
                .standard().
                withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(SERVICE_ENDPOINT, region))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();
    }
}
