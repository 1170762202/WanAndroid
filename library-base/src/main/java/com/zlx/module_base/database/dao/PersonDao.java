package com.zlx.module_base.database.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.zlx.module_base.database.entity.PersonInfo;
import com.zlx.module_base.database.entity.StudentInfo;
import com.zlx.module_base.database.result.StuPersonResult;

import java.util.List;


@Dao
public interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertPerson(PersonInfo person);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertStudent(StudentInfo studentInfo);

    @Query("select * from PersonInfo")
    public List<PersonInfo> selectPersons();

    @Query("select * from StudentInfo")
    public List<StudentInfo> selectStudents();

    @Query("select StudentInfo.id as studentId," +
            "StudentInfo.personId as personId," +
            "StudentInfo.age as age," +
            "PersonInfo.name as name from StudentInfo " +
            "left join PersonInfo on PersonInfo.id=StudentInfo.personId " +
            "where StudentInfo.personId= :personId ")
    List<StuPersonResult> selectPersonStudent(long personId);

    @Query("delete from PersonInfo")
    void deleteAll();
    @Query("delete from StudentInfo")
    void deleteAll1();
}
