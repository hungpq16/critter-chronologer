package com.udacity.jdnd.course3.critter.utils;

import java.util.List;

public interface IBaseAction<T> {

    List<T> findAll();

    T findById(long id);

    T save(T obj);

}
