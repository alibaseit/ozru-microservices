package com.ozruit.services;

import com.ozruit.grpc.*;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ozruit.model.UserData;

import java.util.Arrays;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final List<UserData> users = Arrays.asList(
            new UserData(1, "Ali", "Dodo"),
            new UserData(11, "Ali", "Kiki"),
            new UserData(2, "Turkan", "Dodo"),
            new UserData(3, "Ozan", "Dodo"),
            new UserData(4, "Rubar", "Dodo")
    );

    @Override
    public void getUser(GetUserRequest request, StreamObserver<GetUserResponse> responseObserver) {
        LOGGER.info("GET User userId: " + request.getUserId());

        User user = users.stream().
                filter(u -> request.getUserId() == u.getUserId())
                .findFirst()
                .map(u -> User.newBuilder()
                        .setUserId(u.getUserId())
                        .setFirstName(u.getFirstName())
                        .setLastName(u.getLastName()).build())
                .orElse(null);


        if (user == null) {
            LOGGER.error("User not found! userId: {}", request.getUserId());
            responseObserver.onError(new RuntimeException("No User found!"));
        } else {
            responseObserver.onNext(GetUserResponse.newBuilder().setUser(user).build());
            responseObserver.onCompleted();
        }

    }

    @Override
    public void findUsersByName(FindUsersByNameRequest request, StreamObserver<FindUsersByNameResponse> responseObserver) {
        LOGGER.info("Find users by first name. firstName: {} ", request.getUserName());

        List<User> userList = users.stream()
                .filter(u -> request.getUserName().equalsIgnoreCase(u.getFirstName()))
                .map(u -> User.newBuilder()
                        .setUserId(u.getUserId())
                        .setFirstName(u.getFirstName())
                        .setLastName(u.getLastName()).build())
                .collect(Collectors.toList());

        FindUsersByNameResponse response = FindUsersByNameResponse.newBuilder().addAllUsers(userList).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
