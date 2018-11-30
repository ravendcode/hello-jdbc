package com.github.ravendcode.hello.dao;

import com.github.ravendcode.hello.entity.Employee;

import java.util.List;

public interface CrudDao<T, U> {
    void createOne(T object);
    List<T> getAll();
    T findById(U id);
    void updateOne(T object);
    void deleteOne(T object);
}
