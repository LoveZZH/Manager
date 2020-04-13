package cn.itcast.web;

import cn.itcast.pojo.Teacher;
import cn.itcast.pojo.TeacherCustom;
import cn.itcast.service.TeacherService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    public TeacherService teacherService;

    @RequestMapping("/queryTeacherList")
    public String queryTeacherList(Model model) throws Exception {
        List<TeacherCustom> teacherList = teacherService.queryTeacherList();
        model.addAttribute("teacherList", teacherList);
        return "teacher-check";
    }

    //查询所有的专家信息，并在前端分页显示

    /**
     * 查询产品数据（pageHelper分页）
     * 每次分页操作需要从前台传递到后台的参数：pageNum(当前页)、pageSize(每页显示的条数)
     */
    @RequestMapping("/queryTeacherListPage")
    public String queryTeacherListPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize, String name, Model model) throws Exception {
        PageInfo<TeacherCustom> pageInfo = null;
        if (name == null) {
            pageInfo = teacherService.queryTeacherListPage(pageNum, pageSize);
        } else {
            pageInfo = teacherService.search(pageNum, pageSize, name);
        }
        model.addAttribute("pageInfo", pageInfo);
        return "teacher-list-page";
    }

    //添加专家信息
    @RequestMapping("/saveTeacher")
    public String saveTeacher(Teacher teacher) throws Exception {
        teacherService.saveTeacher(teacher);
        // 跳转专家列表页面
        return "redirect:queryTeacherListPage.action";
    }

    //跳转更新专家信息
    @RequestMapping("/toUpdateTeacher")
    public String toUpdateTeacher(Model model, int id) {
        TeacherCustom teacherCustom = teacherService.selectById(id);
        model.addAttribute("teacherCustom", teacherCustom);
        return "teacher-update";
    }

    //更新专家信息
    @RequestMapping("/updateTeacher")
    public String updateTeacher(TeacherCustom teacherCustom) {
        teacherService.updateTeacher(teacherCustom);
        return "redirect:queryTeacherListPage.action";
    }

    //删除专家
    @RequestMapping("/deleteTeachers")
    public String deleteTeachers(int[] ids) {
        teacherService.deleteTeachers(ids);
        // 跳转专家列表页面
        return "redirect:queryTeacherList.action";
    }

    //专家抽查
    @RequestMapping("/findBycategory")
    public String findBycategory(int categoryId, Model model) {
        List<TeacherCustom> teacherList = teacherService.findBycategory(categoryId);
        model.addAttribute("teacherList", teacherList);
        return "teacher-check";
    }
}
