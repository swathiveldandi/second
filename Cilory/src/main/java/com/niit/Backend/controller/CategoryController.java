package com.niit.Backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.niit.Backend.dao.CategoryDAO;
import com.niit.Backend.model.Category;
@Controller
public class CategoryController {
	
	
	@Autowired
	private CategoryDAO categoryDAO;

	@RequestMapping(value = { "category"})
	public String CategoryPage(Model model) {
		model.addAttribute("category", new Category());
		model.addAttribute("categoryList", categoryDAO.list());
		model.addAttribute("CategoryPageClicked", "true");
		return "index";
	}

	@RequestMapping(value = { "addcategory", "editcategory/addcategory" }, method = RequestMethod.POST)
	public String addCategory(@ModelAttribute("category") Category category) {
		categoryDAO.saveorUpdate(category);
		return "redirect:/category";
	}

	@RequestMapping("editcategory/{id}")
	public String editCategory(@PathVariable("id") int id, Model model) {
		System.out.println("editCategory");
		model.addAttribute("category", this.categoryDAO.get(id));
		model.addAttribute("categoryList", categoryDAO.list());
		model.addAttribute("CategoryPageClicked", "true");
		model.addAttribute("EditCategory", "true");
		return "index";
	}

	@RequestMapping(value = { "removecategory/{id}", "editcategory/removecategory/{id}" })
	public String removeCategory(@PathVariable("id") int id,RedirectAttributes attributes) throws Exception {
		categoryDAO.delete(id);
		attributes.addFlashAttribute("DeleteMessage", "Category has been deleted Successfully");
		return "redirect:/category";
	}
	
	@RequestMapping(value="/categorygson")
	@ResponseBody
	public String CategoryGson()
	{
		List<Category> list=categoryDAO.list();
		Gson gson=new Gson();
		String data=gson.toJson(list);
		return data;
	}



}
