package com.jic.tnw.db.repository;

import com.jic.tnw.db.mysql.enums.TasksStatus;
import com.jic.tnw.db.mysql.tables.pojos.Tasks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by lee5hx on 2017/10/30.
 */
public interface TasksRepository extends JooqRepository<Tasks> {

    Page<Tasks> find(Pageable pageable,String name,String triggedUserName,TasksStatus...tasksStatus);



}
