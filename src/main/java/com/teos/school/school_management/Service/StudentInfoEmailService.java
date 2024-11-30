package com.teos.school.school_management.Service;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;


public class StudentInfoEmailService {
    private static final String STUDENT_EMAIL_TOPIC_ARN = "arn:aws:sns:us-east-1:825184500262:Student-Email-Info-Std";

    public static void sendEmail(String emailInfo){
        String message = "Student Info: " + emailInfo;
        String topicArn = STUDENT_EMAIL_TOPIC_ARN;

        SnsClient snsClient = SnsClient.builder()
                .region(Region.US_EAST_1)
                .build();

        pubTopic(snsClient, message, topicArn);
        snsClient.close();
    }

    public static void pubTopic(SnsClient snsClient, String message, String topicArn) {
        try {
            PublishRequest request = PublishRequest.builder()
                .message(message)
                .topicArn(topicArn)
                .build();

            PublishResponse result = snsClient.publish(request);
            System.out.println(result.messageId() + " Message sent. Status is " + result.sdkHttpResponse().statusCode());

        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}
