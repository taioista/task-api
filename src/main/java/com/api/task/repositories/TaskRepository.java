package com.api.task.repositories;

import java.util.List;

import com.api.task.entities.Task;
import com.api.task.enums.Status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value="select task from Task task where task.status in(:statusList)")
    public List<Task> findByStatusList(@Param("statusList") List<Status> statusList);
}
