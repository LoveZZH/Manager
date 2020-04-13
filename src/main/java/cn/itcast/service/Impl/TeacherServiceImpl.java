package cn.itcast.service.Impl;

import cn.itcast.mapper.TeacherMapper;
import cn.itcast.pojo.Teacher;
import cn.itcast.pojo.TeacherCustom;
import cn.itcast.service.TeacherService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    public TeacherMapper teacherMapper;

    public List<TeacherCustom> queryTeacherList() throws Exception {
        return teacherMapper.queryTeacherList();
    }

    @Override
    public void saveTeacher(Teacher teacher) {
        teacherMapper.saveTeacher(teacher);
    }

    @Override
    public TeacherCustom selectById(int id) {
        return teacherMapper.selectById(id);
    }

    @Override
    public void updateTeacher(TeacherCustom teacherCustom) {
        teacherMapper.updateTeacher(teacherCustom);
    }

    @Override
    public PageInfo<TeacherCustom> queryTeacherListPage(Integer pageNum, Integer pageSize) throws Exception {
        // 向pageHelper传参
        PageHelper.startPage(pageNum,pageSize);
        // 紧跟PageHelper.startPage后的第一个查询（查询全部数据）
        List<TeacherCustom> teacherList = teacherMapper.queryTeacherList();
        PageInfo<TeacherCustom> pageInfo = new PageInfo<>(teacherList);
        return pageInfo;
    }

    @Override
    public void deleteTeachers(int[] ids) {
        teacherMapper.deleteTeachers(ids);
    }

    @Override
    public PageInfo<TeacherCustom> search(Integer pageNum, Integer pageSize, String name) {
        // 向pageHelper传参
        PageHelper.startPage(pageNum,pageSize);
        List<TeacherCustom> teacherList = teacherMapper.search(name);
        PageInfo<TeacherCustom> pageInfo = new PageInfo<>(teacherList);
        return pageInfo;
    }

    @Override
    public List<TeacherCustom> findBycategory(int categoryId) {
        return teacherMapper.findBycategory(categoryId);
    }


}
