package com.rb.passin.repositories;

import com.rb.passin.domain.checkin.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface CheckinRepository extends JpaRepository<CheckIn, Integer> {
}
