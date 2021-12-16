package pl.sda.finalapp.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String categoriesPage(@RequestParam(required = false) String searchText, Model model){
        List<CategoryDto> categories = categoryService.findCategories(searchText);
        model.addAttribute("categoriesData",categories);
        model.addAttribute("searchText",searchText);
        model.addAttribute("parent","");
        model.addAttribute("id","");
        return "categoriesPage";
    }

    @PostMapping("/addCategory")
    public String addCategory(@RequestParam String categoryName, @RequestParam Integer parentId){
        categoryService.addCategory(categoryName, parentId);
        return "redirect:/categories?searchText=" + categoryName;
    }

    @PostMapping(value = "/moveCategory", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getValuesFromJsTree(@RequestParam String newParentId, @RequestParam String movedId){
        Integer newParent = null;
        if(!newParentId.equals("#")){
            newParent = Integer.valueOf(newParentId);
        }
        Integer categoryId = Integer.valueOf(movedId);

        categoryService.changeParent(newParent, categoryId);

        return "categoriesPage";
    }

}
