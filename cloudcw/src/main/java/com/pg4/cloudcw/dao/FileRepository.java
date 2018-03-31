package com.pg4.cloudcw.dao;

import org.springframework.data.repository.CrudRepository;

import com.pg4.cloudcw.entity.File;

public interface FileRepository extends CrudRepository<File, Integer> {

}
