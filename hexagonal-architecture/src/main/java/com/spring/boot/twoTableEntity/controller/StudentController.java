package com.spring.boot.twoTableEntity.controller;

import com.spring.boot.twoTableEntity.entity.StudentEntity;
import com.spring.boot.twoTableEntity.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController{
    Logger LOG = LoggerFactory.getLogger(com.spring.boot.architecture.hexagonal.frontend.MobileConsumerImpl.class);

    @Autowired
    StudentRepository studentRepository;

    @RequestMapping("/add")
    public int addTransaction(@RequestBody StudentEntity studentEntity) {
        LOG.info("Got request to add student : {}",studentEntity);
        return studentRepository.saveAndFlush(studentEntity).getId();
    }

//    @Override
//    public String viewTransaction(int transactionId) {
//        LOG.info("Request from MOBILE to view transaction with ID : {}", transactionId);
//        Transaction transaction = transactionService.viewTransaction(transactionId);
//        LOG.info("Response : {}",transaction);
//        return transaction.toString();
//    }
}

