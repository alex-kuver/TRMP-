package ru.vlsu.psytest.repository;

import ru.vlsu.psytest.domain.ResultTest;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ResultTest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultTestRepository extends JpaRepository<ResultTest, Long>, JpaSpecificationExecutor<ResultTest> {

    @Query("select resultTest from ResultTest resultTest where resultTest.user.login = ?#{principal.username}")
    List<ResultTest> findByUserIsCurrentUser();
}
