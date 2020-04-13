package cn.itcast.mapper;

import cn.itcast.pojo.Teacher;
import cn.itcast.pojo.TeacherCustom;

import java.util.List;

public interface TeacherMapper {
    List<TeacherCustom> queryTeacherList() throws Exception;

    void saveTeacher(Teacher teacher);

    TeacherCustom selectById(int id);

    void updateTeacher(TeacherCustom teacherCustom);

    void deleteTeachers(int[] ids);

    List<TeacherCustom> search(String name);

    List<TeacherCustom> findBycategory(int categoryId);
}
