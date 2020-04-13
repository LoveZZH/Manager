package cn.itcast.service;

import cn.itcast.pojo.Teacher;
import cn.itcast.pojo.TeacherCustom;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TeacherService {

    List<TeacherCustom> queryTeacherList() throws Exception;

    void saveTeacher(Teacher teacher);

    TeacherCustom selectById(int id);

    void updateTeacher(TeacherCustom teacher);

    PageInfo<TeacherCustom> queryTeacherListPage(Integer pageNum, Integer pageSize) throws Exception;

    void deleteTeachers(int[] ids);

    PageInfo<TeacherCustom> search(Integer pageNum, Integer pageSize, String name);

    List<TeacherCustom>  findBycategory(int categoryId);
}
