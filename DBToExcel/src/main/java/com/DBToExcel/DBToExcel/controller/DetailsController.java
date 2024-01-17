package com.DBToExcel.DBToExcel.controller;

import com.DBToExcel.DBToExcel.Model.Details;
import com.DBToExcel.DBToExcel.Repo.DetailsRepo;
import com.DBToExcel.DBToExcel.Service.ExcelGeneratorClass;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DetailsController {

    private final DetailsRepo detailsRepo;

    @PostMapping
    public String save(Details details){
        detailsRepo.save(details);
        return "saved";
    }

    @GetMapping("/get")
    public void get(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Details" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List <Details> listOfStudents = detailsRepo.findAll();
        ExcelGeneratorClass generator = new ExcelGeneratorClass(listOfStudents);
        generator.generateExcelFile(response);
//        return detailsRepo.findAll();
    }

}
