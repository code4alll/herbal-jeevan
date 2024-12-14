package com.ecommerce.HerbalJeevan.Repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.HerbalJeevan.Model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	Category findByNameAndParentCategory(String subCategoryName, Category parentCategory);

	Category findByName(String categoryName);

	@Query("Select name from Category where parentCategory is null")
	List<String> findAllParentCateory();
	
	@Query("select name from Category ")
	List<String> findAllCatetory();

	Category findByUrlSlug(String categoryName);
	
    List<Category> findByParentCategory(Category parentCategory);

    List<Category> findByParentCategoryId(String string);
    
    @Query("SELECT c.name FROM Category c WHERE c.parentCategory = :parentCategory AND c.name LIKE %:name%")
    Set<String> findByCategoryAndName(@Param("parentCategory") Category parentCategory, @Param("name") String name);
    
    @Query("SELECT c.name FROM Category c WHERE c.name LIKE %:name%")
	Set<String> findByCategoryAndName(@Param("name")String name);
    
    @Query("SELECT c FROM Category c WHERE c.urlSlug LIKE :slugPrefix%")
    List<Category> findSubCategoriesBySlugPrefix(@Param("slugPrefix") String slugPrefix);


	
}

