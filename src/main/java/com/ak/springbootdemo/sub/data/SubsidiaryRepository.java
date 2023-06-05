package com.ak.springbootdemo.sub.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Subsidiary Repository
 */
@Repository
public interface SubsidiaryRepository extends CrudRepository<Subsidiary, Long> {
    /**
     * Find Subsidiary by inner code.
     *
     * @param innerCode unique inner code of the subsidiary
     * @return Optional of Subsidiary
     */
    Optional<Subsidiary> findByInnerCode(String innerCode);

}
