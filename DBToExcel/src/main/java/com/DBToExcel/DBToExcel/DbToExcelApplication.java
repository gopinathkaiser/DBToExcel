package com.DBToExcel.DBToExcel;

import com.DBToExcel.DBToExcel.Model.Details;
import com.DBToExcel.DBToExcel.Repo.DetailsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class DbToExcelApplication implements CommandLineRunner {

	private final DetailsRepo detailsRepo;

	public static void main(String[] args) {
		SpringApplication.run(DbToExcelApplication.class, args);
	}

	@Override
	public void run(String...a) {
		for (int i = 0; i <= 10; i++) {
			Details student = new Details();
			student.setStudentName("Student Name");
			student.setEmail("student@mail.com");
			student.setMobileNo("999999");
			detailsRepo.save(student);
		}
	}
}
