/******************************************************************************
 * Project: SpringBoot Data Access Guide Series
 * Description: A repository interface that works with Customer entities.
 * Author: Benjamin Soto-Roberts
 * Created: 2025-12-20
 * Source: https://spring.io/guides/gs/accessing-data-jpa
 ******************************************************************************/

package org.bsr.springboot.accessingdataspringjpa.repository;

import org.bsr.springboot.accessingdataspringjpa.domain.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    List<Customer> findByLastName(String lastName);

    Customer findById(long id);
}
