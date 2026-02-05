//package com.college.yi.ecsite.admin.controller;
//
//import jakarta.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.college.yi.ecsite.admin.dto.ProductEditDto;
//import com.college.yi.ecsite.admin.form.ProductEditForm;
//import com.college.yi.ecsite.admin.service.AdminProductEditService;
//import com.college.yi.ecsite.exception.ProductNotFoundException;
//
//@Controller
//@RequestMapping("/admin/products")
//public class AdminProductEditController {
//
//    @Autowired
//    private AdminProductEditService productEditService;
//
//    // 編集画面の表示
//    @GetMapping("/{id}/edit")
//    public String showEditForm(@PathVariable Long id, Model model) {
//        try {
//            ProductEditDto dto = productEditService.getEditData(id);
//            ProductEditForm form = productEditService.convertToForm(dto);
//            model.addAttribute("productEditForm", form);
//            model.addAttribute("productId", id);
//            model.addAttribute("categories", productEditService.getAllCategories());
//            return "admin/product_edit";
//        } catch (ProductNotFoundException e) {
//            model.addAttribute("errorMessage", "該当の商品が見つかりませんでした");
//            return "admin/error";
//        }
//    }
//
//    // 編集保存（更新処理）
//    @PostMapping("/{id}/edit")
//    public String updateProduct(
//            @PathVariable Long id,
//            @ModelAttribute("productEditForm") @Valid ProductEditForm form,
//            BindingResult bindingResult,
//            Model model,
//            RedirectAttributes redirectAttributes) {
//
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("productId", id);
//            model.addAttribute("categories", productEditService.getAllCategories());
//            return "admin/product_edit";
//        }
//
//        try {
//            productEditService.updateProduct(id, form);
//            redirectAttributes.addFlashAttribute("successMessage", "商品を更新しました");
//            return "redirect:/admin/products";
//        } catch (ProductNotFoundException e) {
//            model.addAttribute("errorMessage", "商品が存在しません");
//            return "admin/error";
//        } catch (Exception e) {
//            model.addAttribute("errorMessage", "商品更新時にエラーが発生しました");
//            return "admin/error";
//        }
//    }
//}
