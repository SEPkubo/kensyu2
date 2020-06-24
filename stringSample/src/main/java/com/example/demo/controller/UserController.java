package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

/**
 * ユーザー情報 Controller
 */
@Controller
public class UserController {

	/**
	 * ユーザー情報 Service
	 */
	@Autowired
	UserService userService;

	/**
	 * ユーザー情報一覧画面を表示
	 * @param model Model
	 * @return ユーザー情報一覧画面
	 */
	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public String displayList(Model model) {
		List<User> userlist = userService.searchAll();
		model.addAttribute("userlist", userlist);
		return "user/list";
	}

	/**
	 * 登録画面を表示
	 * @param model Model
	 * @return 一覧画面
	 */


	@RequestMapping(value = "/user/add", method = RequestMethod.GET)
	public String displayAdd(Model model) {
		model.addAttribute("userRequest", new UserRequest());
		return "user/add";
	}



//	/**
//	 * 登録画面確認画面を表示
//	 * @param model Model
//	 * @return ユーザー情報一覧画面
//	 */
//
//
//	@RequestMapping(value = "/user/addcheck", method=RequestMethod.POST)
//	public String Add(@ModelAttribute UserRequest userRequestadd, Model model) {
//		System.out.println("通った");
//		userService.create(userRequestadd);
//		model.addAttribute("userRequestadd", new UserRequest());
//		System.out.println(userRequestadd);
//		return "user/addcheck";
//	}


	/**
	 * 新規登録
	 * @param userRequest リクエストデータ
	 * @param model Model
	 * @return 一覧画面
	 */
	@RequestMapping(value="/user/create", method=RequestMethod.POST)
    public String create(@ModelAttribute UserRequest userRequest, Model model) {
		// 登録処理
		userService.create(userRequest);
		return "redirect:/user/list";
	}


    //  登録確認画面
    @PostMapping("/user/addcheck")
    public String toBookInfo(@ModelAttribute("userRequestadd") UserRequest userRequestadd,Model model) {
    	model.addAttribute("userRequest", new UserRequest());
        return "user/addcheck";
    }

    // 編集確認画面
    @PostMapping("/user/editcheck")
    public String editcheck(@ModelAttribute("userUpdateRequest") UserUpdateRequest userUpdateRequest,Model model) {
    	model.addAttribute("userUpdateRequest", userUpdateRequest);
        return "user/editcheck";
    }




    /**
     * 編集画面を表示
     * @param id 表示するユーザーID
     * @param model Model
     * @return 編集画面
     */
    @GetMapping("/user/{id}")
    public String displayView(@PathVariable Long id, Model model) {
    	User user = userService.findById(id);
        model.addAttribute("userRequest", user);
      return "user/edit";
    }


    /**
     * 削除画面を表示
     * @param id 表示するユーザーID
     * @param model Model
     * @return 削除画面
     */
    @GetMapping("/user/{id}/delete")
    public String delete(@PathVariable Long id, Model model) {
    	User user = userService.findById(id);
        model.addAttribute("userRequest", user);
      return "user/delete";
    }



    // 更新処理
    @RequestMapping(value="/user/update", method=RequestMethod.POST)
    public String update(@Validated @ModelAttribute UserUpdateRequest userUpdateRequest, BindingResult result, Model model) {

        // ユーザー情報の更新
        userService.update(userUpdateRequest);
        return "redirect:/user/list";
    }






}