package com.example.ph19127_mob2041.dao;

import java.util.List;

public interface DAO<T> {
    List<T> getAll();
    T getById(String id);
    long insert(T t);
    long update(T t);
    long delete(T t);
}
