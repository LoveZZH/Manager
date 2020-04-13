package cn.itcast.service.Impl;

import cn.itcast.mapper.CategoryMapper;
import cn.itcast.pojo.Category;
import cn.itcast.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    public CategoryMapper categoryMapper;

    @Override
    public List<Category> queryCategoryList() throws Exception {
        return categoryMapper.queryCategoryList();
    }

    @Override
    public void saveCategory(Category category) {
        categoryMapper.saveCategory(category);
    }

    @Override
    public Category selectById(int id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public void updateCategory(Category category) {
        categoryMapper.updateCategory(category);
    }

    @Override
    @RolesAllowed("DIS")
    public PageInfo<Category> queryCategoryListPage(Integer pageNum, Integer pageSize) {
        // 向pageHelper传参
        PageHelper.startPage(pageNum,pageSize);
        // 紧跟PageHelper.startPage后的第一个查询（查询全部数据）
        List<Category> categoryList = categoryMapper.queryCategoryList();
        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        return pageInfo;
    }

    @Override
    public void deleteCategorys(int[] ids) {
        categoryMapper.deleteCategorys(ids);
    }
}
