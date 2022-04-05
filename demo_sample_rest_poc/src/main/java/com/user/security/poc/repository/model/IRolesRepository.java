package com.user.security.poc.repository.model;

import com.user.security.poc.dto.UserRole;
import com.user.security.poc.repository.SecurityRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface IRolesRepository extends SecurityRepository<Role, Long>,
        JpaSpecificationExecutor<Role> {

    Role findByName(UserRole roleName);

}
