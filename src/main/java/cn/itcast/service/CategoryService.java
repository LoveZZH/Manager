package cn.itcast.service;

import cn.itcast.pojo.Category;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CategoryService {
    List<Category> queryCategoryList() throws Exception;

    void saveCategory(Category category);

    Category selectById(int id);

    void updateCategory(Category category);

    PageInfo<Category> queryCategoryListPage(Integer pageNum, Integer pageSize);

    void deleteCategorys(int[] ids);
}
