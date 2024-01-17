package com.DBToExcel.DBToExcel.Repo;

import com.DBToExcel.DBToExcel.Model.Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailsRepo extends JpaRepository<Details,Long> {
}
