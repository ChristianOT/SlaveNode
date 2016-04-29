package org.gradle.dataBaseRepositories;

import java.util.List;

import org.gradle.dataBaseObjects.MyObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyObjectRepository extends JpaRepository<MyObject, Long> {

	List<MyObject> findByContent(String url);

}
