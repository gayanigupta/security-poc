package com.user.security.poc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Base JPA repository for dataloader api
 * @param <T>
 * @param <I>
 */
@NoRepositoryBean
@EnableJpaRepositories
public interface SecurityRepository<T, I extends Serializable> extends JpaRepository<T, I> {

}
