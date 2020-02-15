package com.spring.boot.architecture.hexagonal.backend;

import com.spring.boot.architecture.hexagonal.entity.Transaction;

    public interface IDBPort {
        int addTransaction(Transaction transaction);
        Transaction getTransaction(int transactionId);
    }
