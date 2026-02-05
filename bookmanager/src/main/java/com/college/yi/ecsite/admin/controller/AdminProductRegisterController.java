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
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.college.yi.ecsite.admin.form.ProductRegisterForm;
//import com.college.yi.ecsite.admin.service.AdminProductRegisterService;
//import com.college.yi.ecsite.exception.ProductRegistrationException;
//
//@Controller
//public class AdminProductRegisterController {
//
//    @Autowired
//    private AdminProductRegisterService registerService;
//
//    /**
//     * 商品登録画面の初期表示
//     */
//    @GetMapping("/admin/products/new")
//    public String showRegisterForm(Model model) {
//        // フォームオブジェクトが無ければ新規作成
//        if (!model.containsAttribute("productRegisterForm")) {
//            model.addAttribute("productRegisterForm", new ProductRegisterForm());
//        }
//        return "admin/product_form";
//    }
//
//    /**
//     * 商品登録処理
//     */
//    @PostMapping("/admin/products/new")
//    public String registerProduct(
//            @Valid @ModelAttribute("productRegisterForm") ProductRegisterForm form,
//            BindingResult bindingResult,
//            Model model,
//            RedirectAttributes redirectAttributes
//    ) {
//        // 1. バリデーションエラーがあればフォーム画面再表示（メッセージも自動表示）
//        if (bindingResult.hasErrors()) {
//            return "admin/product_form";
//        }
//
//        try {
//            // 2. サービス層で商品登録（画像チェックやDB登録も含む）
//            registerService.registerProduct(form);
//
//            // 3. 完了メッセージをリダイレクト先に渡す
//            redirectAttributes.addFlashAttribute("successMessage", "商品を登録しました");
//            return "redirect:/admin/products";
//        } catch (ProductRegistrationException e) {
//            // 画像エラーやDBエラー等：画面上部にメッセージ表示
//            model.addAttribute("errorMessage", e.getMessage());
//            return "admin/product_form";
//        } catch (Exception e) {
//            // 予期せぬエラー
//            model.addAttribute("errorMessage", "システムエラーが発生しました。管理者にご連絡ください。");
//            return "admin/product_form";
//        }
//    }
//}
