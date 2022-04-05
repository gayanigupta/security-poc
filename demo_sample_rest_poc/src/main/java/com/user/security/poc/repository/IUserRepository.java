package com.user.security.poc.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.user.security.poc.repository.model.UserModel;

@Repository
@EnableJpaRepositories
public interface IUserRepository extends SecurityRepository<UserModel, Long>,
        JpaSpecificationExecutor<UserModel> {
	//built in query
    UserModel findByUsername(String userName);
    //type query
  

    @Query("select u from UserModel u where u.username=:userName and u.password=:password")
    UserModel findUserByNameAndPassword(@Param("userName") String userName, @Param("password") String password);

    //native query
    @Modifying
    @Query(value = "select * from UserModel u where u.username=:userName and u.password=:password",
            nativeQuery = true)
    UserModel findUserByNameAndPasswordUsingNative(@Param("userName") String userName, @Param("password") String password);
}
