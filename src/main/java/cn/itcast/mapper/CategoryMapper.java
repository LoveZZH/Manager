package cn.itcast.mapper;

import cn.itcast.pojo.Category;

import java.util.List;

public interface CategoryMapper {
    void saveCategory(Category category);

    Category selectById(int id);

    void updateCategory(Category category);

    List<Category> queryCategoryList();

    void deleteCategorys(int[] ids);
}
