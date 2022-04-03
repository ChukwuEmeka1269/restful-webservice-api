package com.restwithsergey.sergeyrest.repository;

import com.restwithsergey.sergeyrest.Model.UserModel;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserModel, Long> {
    UserModel findByEmail(String email);

    UserModel findByUserId(String userId);

    UserModel findUserModelByUserId(String userId);
}
