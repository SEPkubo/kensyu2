package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.dto.UserRequest;
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
	 * ユーザー新規登録
	 * @param userRequest リクエストデータ
	 * @param model Model
	 * @return ユーザー情報一覧画面
	 */
	@RequestMapping(value="/user/create", method=RequestMethod.POST)
    public String create(@ModelAttribute UserRequest userRequest, Model model) {
		// ユーザー情報の登録
		userService.create(userRequest);
		System.out.println(userRequest);
		return "redirect:/user/list";
	}



//    //  入力画面 登録確認画面は一覧画面とは別に値の受け渡しを行う
//    @GetMapping("/input")
//    public String input(@ModelAttribute("userRequestadd") UserRequest userRequestadd) {
//        return "input";
//    }

    //  書籍情報画面
    @PostMapping("/user/addcheck")
    public String toBookInfo(@ModelAttribute("userRequestadd") UserRequest userRequestadd,Model model) {
    	model.addAttribute("userRequest", new UserRequest());
        return "user/addcheck";
    }
}