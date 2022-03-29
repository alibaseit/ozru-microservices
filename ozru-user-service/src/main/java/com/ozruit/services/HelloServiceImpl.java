package com.ozruit.services;

import com.ozruit.grpc.HelloRequest;
import com.ozruit.grpc.HelloResponse;
import com.ozruit.grpc.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
       LOGGER.info("First Name: {} ", request.getFirstName());

       HelloResponse helloResponse =  HelloResponse.newBuilder()
               .setGreeting("Hello " + request.getFirstName() + " " + request.getLastName())
               .build();


       responseObserver.onNext(helloResponse);
       responseObserver.onCompleted();

    }
}
