package com.ecommerce.HerbalJeevan.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.HerbalJeevan.Model.Category;
import com.ecommerce.HerbalJeevan.Repository.CategoryRepository;
import com.ecommerce.HerbalJeevan.Utility.IdGeneratorUtils;

@Service
public class CategoryService {
	
	
	

    @Autowired
    private CategoryRepository categoryRepository;
    
	@Autowired
	IdGeneratorUtils IdGenerator;

    
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                                 .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
    
    public Map<String, Double> getgstAndCommision(String categoryName,Double gst,Double commision) {
//    	Map<String,String> commissionMap=new HashMap<>();
//        Map<String,String> gstmap=new HashMap<>();

        
        
//    commissionMap.put("Automotive".toLowerCase(),"5");
//    commissionMap.put("Baby Center".toLowerCase(),"5");
//    commissionMap.put("Beauty & Frangrances".toLowerCase(),"2.5");
//    commissionMap.put("Consumer Electronics".toLowerCase(),"2.5");
//    commissionMap.put("Fashion Accessories".toLowerCase(),"10");
//    commissionMap.put("Food & Beverage".toLowerCase(),"2");
//    commissionMap.put("Home,Garden & Furniture".toLowerCase(),"5");
//    commissionMap.put("Machinery & Equipment".toLowerCase(),"5");
//    commissionMap.put("Office & Stationery".toLowerCase(),"3");
//    commissionMap.put("Personal care".toLowerCase(),"3");
//    commissionMap.put("Pet & Aniaml care".toLowerCase(),"5");
//    commissionMap.put("Sports & fitness".toLowerCase(),"5");
//    commissionMap.put("Tools & Home improvement".toLowerCase(),"5");
//    commissionMap.put("TOYS".toLowerCase(),"5");
//    
//    
//    commissionMap.put("consumerelectronics".toLowerCase(), "2.5");
//    commissionMap.put("fashionandaccessories".toLowerCase(), "10");
//    commissionMap.put("automotive".toLowerCase(), "5");
//    commissionMap.put("foodandbeverages".toLowerCase(), "2");
//    commissionMap.put("babycenter".toLowerCase(), "5");
//    commissionMap.put("beautyandfragrances".toLowerCase(), "2.5");
//    commissionMap.put("homegardenandfurniture".toLowerCase(), "5");
//    commissionMap.put("machineryandequipment".toLowerCase(), "5");
//    commissionMap.put("officeandstationery".toLowerCase(), "3");
//    commissionMap.put("personalcare".toLowerCase(), "3");
//    commissionMap.put("petandanimalcare".toLowerCase(), "5");
//    commissionMap.put("sportsandfitness".toLowerCase(), "5");
//    commissionMap.put("toys".toLowerCase(), "5");
//    commissionMap.put("toolsandhomeimprovement".toLowerCase(), "5");
//
//    gstmap.put("Automotive".toLowerCase(),"28");
//    gstmap.put("Baby Center".toLowerCase(),"12");
//    gstmap.put("Beauty & Frangrances".toLowerCase(),"28");
//    gstmap.put("Consumer Electronics".toLowerCase(),"28");
//    gstmap.put("Fashion Accessories".toLowerCase(),"5");
//    gstmap.put("Food & Beverage".toLowerCase(),"18");
//    gstmap.put("Home,Garden & Furniture".toLowerCase(),"28");
//    gstmap.put("Machinery & Equipment".toLowerCase(),"18");
//    gstmap.put("Office & Stationery".toLowerCase(),"18");
//    gstmap.put("Personal care".toLowerCase(),"18");
//    gstmap.put("Pet & Aniaml care".toLowerCase(),"18");
//    gstmap.put("Sports & fitness".toLowerCase(),"12");
//    gstmap.put("Tools & Home improvement".toLowerCase(),"12");
//    gstmap.put("TOYS".toLowerCase(),"18");
//    
//    
//    
//    gstmap.put("consumerelectronics".toLowerCase(), "28");
//    gstmap.put("fashionandaccessories".toLowerCase(), "5");
//    gstmap.put("automotive".toLowerCase(), "28");
//    gstmap.put("foodandbeverages".toLowerCase(), "18");
//    gstmap.put("babycenter".toLowerCase(), "12");
//    gstmap.put("beautyandfragrances".toLowerCase(), "28");
//    gstmap.put("homegardenandfurniture".toLowerCase(), "28");
//    gstmap.put("machineryandequipment".toLowerCase(), "18");
//    gstmap.put("officeandstationery".toLowerCase(), "18");
//    gstmap.put("personalcare".toLowerCase(), "18");
//    gstmap.put("petandanimalcare".toLowerCase(), "18");
//    gstmap.put("sportsandfitness".toLowerCase(), "12");
//    gstmap.put("toys".toLowerCase(), "18");
//    gstmap.put("toolsandhomeimprovement".toLowerCase(), "12");
    
//    if(categoryName!=null&&commissionMap.containsKey(categoryName.toLowerCase())) {
//    	commision=Double.parseDouble(commissionMap.get(categoryName.toLowerCase()));
//    }
//    
//    if(categoryName!=null&&gstmap.containsKey(categoryName.toLowerCase())) {
//    	gst=Double.parseDouble(gstmap.get(categoryName.toLowerCase()));
//    }
    
    
    Map<String, Double> resultMap = new HashMap<>();
    resultMap.put("gst", gst);
    resultMap.put("commission", commision);

    return resultMap;
    

    
    }
    
    
    public Category resolveCategory(String urlSlug) {
        String[] categories = urlSlug.split("/");
        Category parentCategory = null;
        String parentUrlSlug = null;
        Double commission = 0d;
        Double gst = 0d;
        int level = 0;
        
        Category innercategory = categoryRepository.findByUrlSlug(urlSlug.toLowerCase());
        
        if(innercategory!=null) {
        	return innercategory;
        }

        for (int i = 0; i < categories.length; i++) {
            String categoryName = categories[i].toLowerCase();

            // Check if category exists
            Category category = categoryRepository.findByName(categoryName);

            if (category == null) {
                // Create category if not exists
                if (parentCategory == null) {
                    // Create parent category
                    Map<String, Double> resultMap = getgstAndCommision(categoryName, gst, commission);
                    gst = resultMap.get("gst");
                    commission = resultMap.get("commission");

                    parentCategory = new Category();
                    parentCategory.setName(categoryName);
                    parentCategory.setCommision(commission);
                    parentCategory.setGst(gst);
                    parentCategory.setCategoryId(IdGenerator.generateCategoryId(categoryName.toUpperCase()));
                    parentCategory.setUrlSlug(categoryName);
                    parentCategory.setLevel(level);
                    parentCategory = categoryRepository.save(parentCategory);
                    parentUrlSlug = parentCategory.getUrlSlug();
                } else {
                	
//                    Category subCategory = categoryRepository.findByNameAndParentCategory(categoryName, parentCategory);

                    // Create subcategory
                    category = new Category();
                    category.setName(categoryName);
                    category.setParentCategory(parentCategory);
                    category.setCommision(parentCategory.getCommision());
                    category.setGst(parentCategory.getGst());
                    category.setCategoryId(IdGenerator.generateCategoryId(categoryName.toUpperCase()));
                    category.setUrlSlug(parentUrlSlug + "/" + categoryName);
                    category.setLevel(parentCategory.getLevel() + 1);
                    category = categoryRepository.save(category);
                    parentCategory = category;
                    parentUrlSlug = category.getUrlSlug();
                }
            } else {
                // Set parent category for the next iteration
                parentCategory = category;
                parentUrlSlug = category.getUrlSlug();
            }
        }

        return parentCategory;
    }
    
    
    public List<Category> getAllSubCategories(Category category) {
    	
//        List<Category> subCategories = new ArrayList<>();
        
        List<Category> children = categoryRepository.findSubCategoriesBySlugPrefix(category.getUrlSlug());
        
        return children;
    }

    @SuppressWarnings("unused")
	private void addSubCategories(Category category, List<Category> subCategories) {
        subCategories.add(category);
        List<Category> children = categoryRepository.findByParentCategory(category);
        
        
        for (Category subCategory : children) {
            addSubCategories(subCategory, subCategories);
        }
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    
public Category addCategoryUsingCatSlug(String catSlug) {
String[] categories = catSlug.split("/");
Category parentCategory = null;
String parentUrlSlug = null;
Double commission = 0d;
Double gst = 0d;
int level = 0;

for (int i = 0; i < categories.length; i++) {
    String categoryName = categories[i].toLowerCase();

    if (parentCategory == null) {
        parentCategory = categoryRepository.findByName(categoryName);
        if (parentCategory == null) {
            // Create parent category if not exists
            Map<String, Double> resultMap = getgstAndCommision(categoryName, gst, commission);
            gst = resultMap.get("gst");
            commission = resultMap.get("commission");

            parentCategory = new Category();
            parentCategory.setName(categoryName);
            parentCategory.setCommision(commission);
            parentCategory.setGst(gst);
            parentCategory.setCategoryId(IdGenerator.generateCategoryId(categoryName.toUpperCase()));
            parentCategory.setUrlSlug(IdGenerator.generateUrlSlug(null, categoryName));
            parentCategory.setLevel(level);
            parentCategory = categoryRepository.save(parentCategory);
        }
        parentUrlSlug = parentCategory.getUrlSlug();
    } else {
        // Check if subcategory exists
        Category subCategory = categoryRepository.findByNameAndParentCategory(categoryName, parentCategory);
        if (subCategory == null) {
            // Create subcategory if not exists
            subCategory = new Category();
            subCategory.setName(categoryName);
            subCategory.setParentCategory(parentCategory);
            subCategory.setCommision(parentCategory.getCommision());
            subCategory.setGst(parentCategory.getGst());
            subCategory.setCategoryId(IdGenerator.generateCategoryId(categoryName.toUpperCase()));
            subCategory.setUrlSlug(IdGenerator.generateUrlSlug(parentUrlSlug, categoryName));
            subCategory.setLevel(parentCategory.getLevel() + 1);
            subCategory = categoryRepository.save(subCategory);
        }
        // Set parent category for the next iteration
        parentCategory = subCategory;
        parentUrlSlug = subCategory.getUrlSlug();
    }
}

return parentCategory;
}


    public Category addCategoryWithSubCategories(String categoryName, List<String> subCategoryNames) {
        Category parentCategory = categoryRepository.findByName(categoryName.toLowerCase());
        
        Double commission=0d;
    	Double gst=0d;
    	String parenturl=null;
    	
    	

        

        if (parentCategory == null) {
        	
        	System.out.println("Before method call - GST: " + gst + ", Commission: " + commission);

        	Map<String, Double> resultMap = getgstAndCommision(categoryName.toLowerCase(), gst, commission);
        	gst = resultMap.get("gst");
        	commission = resultMap.get("commission");
        	
        	System.out.println("After method call - GST: " + gst + ", Commission: " + commission);	            parentCategory = new Category();
            parentCategory.setName(categoryName.toLowerCase());
            parentCategory.setCommision(commission);
            parentCategory.setGst(gst);
            parentCategory.setCategoryId(IdGenerator.generateCategoryId(categoryName.toUpperCase()));
            parentCategory.setUrlSlug(IdGenerator.generateUrlSlug(categoryName.toLowerCase(), null));
            parenturl=parentCategory.getUrlSlug();
            parentCategory = categoryRepository.save(parentCategory);
            
            
        }
        else {
        	commission=parentCategory.getCommision();
        	gst=parentCategory.getGst();
        	parenturl=parentCategory.getUrlSlug();
        	
        }

        if (subCategoryNames != null && !subCategoryNames.isEmpty()) {
            List<Category> subCategories = new ArrayList<>();

            for (String subCategoryName : subCategoryNames) {
                Category subCategory = categoryRepository.findByNameAndParentCategory(subCategoryName.toLowerCase(), parentCategory);
                if (subCategory == null) {
                    subCategory = new Category();
                    subCategory.setName(subCategoryName.toLowerCase());
                    subCategory.setParentCategory(parentCategory);
                    subCategory.setUrlSlug(IdGenerator.generateUrlSlug(parenturl, subCategoryName.toLowerCase()));
                    subCategory.setCommision(commission);
                    subCategory.setGst(gst);
                    subCategory.setCategoryId(IdGenerator.generateCategoryId(categoryName.toUpperCase()));

                    subCategories.add(subCategory);

                }
            }

            
             categoryRepository.saveAll(subCategories);
        }

        return parentCategory;
    }

	public List<String> getAllCategories(String categoryName) {
		Category parent=categoryRepository.findByName(categoryName);
		List<Category> categories=categoryRepository.findAll();
		List<String> subCategory=new ArrayList<>();
		if (parent != null) {
			subCategory = categories.stream()
		                              .filter(category -> category.getParentCategory() != null && category.getParentCategory().getId().equals(parent.getId()))
		                              .map(Category::getName)
		                              .collect(Collectors.toList());
		}
		return subCategory;
		
		
	}
	
	public List<String> getAllCategories() {
		List<String> category=categoryRepository.findAllParentCateory();
		return category;
    }
	
	public List<String> getAllCategoryName(){
		List<String> category=categoryRepository.findAllCatetory();
		return category;
	}
	
	
	
	public List<String> getSubCategoriesByParentName(String parentName) {
	    // Find the parent category by name
	    Category parent = categoryRepository.findByName(parentName);
	    
	    if (parent == null) {
	        return null; // Return an empty list if the parent category is not found
	    }

	    // Retrieve subcategories with a single query
	    return categoryRepository.findByParentCategoryId(parent.getId())
	                             .stream()
	                             .map(Category::getName)
	                             .collect(Collectors.toList());
	}

	
//	public List<CategoryDTO2> getCategoriesWithSubCategories() {
//        List<Category> categories = categoryRepository.findAll();
//        return categories.stream()
//                .map(category -> {
//                    List<String> childNames = getSubCategoriesByParentName(category.getName());
//                    return CategoryMapper.toDTO(category, childNames);
//                })
//                .collect(Collectors.toList());
//    }

	public Set<String> findByNameContaining(String category, String parentCategory) {
		Category cat=categoryRepository.findByName(parentCategory);
		if(cat!=null) {
			Set<String> cats=categoryRepository.findByCategoryAndName(cat,category);
			return cats;
		}
		
		return null;
	}
	
	public Set<String> findByNameContaining(String category) {
		Set<String> categories=categoryRepository.findByCategoryAndName(category);
		return categories;
	}


//	public List<CategoryDTO> getAllCategoriesWithSubCategories() {
//        List<Category> categories = categoryRepository.findAll();
//        
//        return categories.stream()
//                .map(CategoryMapper::toDTO)
//                .collect(Collectors.toList());
//    }






//private CategoryStructure categoryStructure;
//
//public CategoryService() {
//    categoryStructure = new CategoryStructure();
//    initializeCategoryStructure(); // Initialize with sample data if needed
//}
//
//public CategoryStructure getCategoryStructure() {
//    return categoryStructure;
//}
//
//public Map<String, Object> getCategoryStructureAsMap() {
//    return categoryStructure.toMap();
//}
//
//private void initializeCategoryStructure() {
//    // Sample initialization, you can customize this according to your needs
//    Category automotive = new Category("AUTOMOTIVE", 10.0); // 10% margin
//    Category automotiveCareCleaning = new Category("AUTOMOTIVE CARE & CLEANING", 15.0); // 15% margin
//    Category automotiveCleaningTool = new Category("AUTOMOTIVE CLEANING TOOL", 5.0); // 5% margin
//    automotiveCareCleaning.getSubCategories().add(automotiveCleaningTool);
//    
//    Category exteriorCare = new Category("EXTERIOR CARE", 10.0); // 10% margin
//    exteriorCare.getItems().add("AUTOMOTIVE GAS CARE");
//    exteriorCare.getItems().add("AUTOMOTIVE POLISHES");
//    automotiveCareCleaning.getSubCategories().add(exteriorCare);
//
//    Category interiorCare = new Category("INTERIOR CARE", 8.0); // 8% margin
//    interiorCare.getItems().add("AUTOMOTIVE FRESHENER");
//    interiorCare.getItems().add("AUTOMOTIVE LEATHER CARE");
//    automotiveCareCleaning.getSubCategories().add(interiorCare);
//
//    automotive.getSubCategories().add(automotiveCareCleaning);
//
//    Category automotivePartsAccessories = new Category("AUTOMOTIVE PARTS & ACCESSORIES", 12.0); // 12% margin
//    Category atvUtvParts = new Category("ATV UTV PARTS", 7.0); // 7% margin
//    atvUtvParts.getItems().add("ATV & UTV ACCESSORIES");
//    atvUtvParts.getItems().add("ATV & UTV PARTS");
//    automotivePartsAccessories.getSubCategories().add(atvUtvParts);
//
//    Category aviationPartsAccessories = new Category("AVIATION PARTS & ACCESSORIES", 8.0); // 8% margin
//    aviationPartsAccessories.getItems().add("AVIATION ACCESSORIES");
//    aviationPartsAccessories.getItems().add("AVIATION PARTS");
//    automotivePartsAccessories.getSubCategories().add(aviationPartsAccessories);
//
//    Category busPartsAccessories = new Category("BUS PARTS AND ACCESSORIES", 10.0); // 10% margin
//    busPartsAccessories.getItems().add("BUS BODY KIT");
//    busPartsAccessories.getItems().add("BUS BRAKES");
//    automotivePartsAccessories.getSubCategories().add(busPartsAccessories);
//
//    automotive.getSubCategories().add(automotivePartsAccessories);
//
//    categoryStructure.getCategories().add(automotive);
//}
}
