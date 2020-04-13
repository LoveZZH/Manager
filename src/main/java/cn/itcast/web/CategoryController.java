package cn.itcast.web;

import cn.itcast.pojo.Category;
import cn.itcast.service.CategoryService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    public CategoryService  categoryService;

    @RequestMapping("/queryCategoryList")
    public String queryCategoryList(Model model) throws Exception {
        List<Category> categoryList = categoryService.queryCategoryList();
        model.addAttribute("categoryList",categoryList);
        return "category-list";
    }

    /**
     * 查询产品数据（pageHelper分页）
     * 每次分页操作需要从前台传递到后台的参数：pageNum(当前页)、pageSize(每页显示的条数)
     */
    @RequestMapping("/queryCategoryListPage")
    public String queryCategoryListPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize, Model model) throws Exception {
        PageInfo<Category> pageInfo = categoryService.queryCategoryListPage(pageNum,pageSize);
        model.addAttribute("pageInfo", pageInfo);
        return "category-list";
    }

    @RequestMapping(value = "/queryCategoryList2")
    @ResponseBody
    public List<Category>  queryCategoryList2() throws Exception {
        List<Category> categoryList = categoryService.queryCategoryList();
        return categoryList;
    }
    @RequestMapping("saveCategory")
    public String saveCategory(Category category){
        categoryService.saveCategory(category);
        return "redirect:queryCategoryList.action";
    }

    //跳转更新分类信息
    @RequestMapping("/toUpdateCategory")
    public String toUpdateCategory(Model model,int categoryId){
        Category category = categoryService.selectById(categoryId);
        model.addAttribute("category",category);
        return "category-update";
    }

    //更新分类
    @RequestMapping("/updateCategory")
    public String updateCategory(Category category){
        categoryService.updateCategory(category);
        return "redirect:queryCategoryList.action";
    }
    //删除分类
    @RequestMapping("/deleteCategorys")
    public String deleteCategorys(int[] ids){
        categoryService.deleteCategorys(ids);
        // 跳转专家列表页面
        return "redirect:queryCategoryListPage.action";
    }
}
