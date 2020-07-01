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
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.common.Common;
import com.example.demo.dto.UserDeleteRequest;
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

	String message = "";		// エラーメッセージ

	/**
	 * 一覧画面を表示
	 * @param model Model
	 * @return 一覧画面
	 */
	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public String displayList(Model model) {
		List<User> userlist = userService.searchAll();

		System.out.println(userlist.get(0));

		model.addAttribute("userlist", userlist);
		return "user/list";
	}


	/**
	 * 住所検索の一覧画面を表示
	 * @param model Model
	 * @return 住所検索の一覧画面
	 */
	@RequestMapping(value = "/user/listsearch", method = RequestMethod.GET)
	public String displayListsearch(@RequestParam(name = "Serchname") String Serchname,
			Model model) {
		List<User> userlist = userService.searchList(Serchname);


		model.addAttribute("userlist", userlist);
		return "user/list";
	}

	/**
	 * 登録画面を表示
	 * @param model Model
	 * @return 一覧画面
	 */


	@RequestMapping(value = "/user/add", method = RequestMethod.GET)
	public String displayAdd(@ModelAttribute("errmsg") String errmsg,Model model) {

		if (errmsg != null) {
			model.addAttribute("errmsg",errmsg);
		}
		model.addAttribute("userRequest", new UserRequest());
		return "user/add";
	}



	/**
	 * 新規登録
	 * @param userRequest リクエストデータ
	 * @param model Model
	 * @return 一覧画面
	 */
	@RequestMapping(value="/user/create", method=RequestMethod.POST)
    public String create(@ModelAttribute UserRequest userRequest, Model model) {
		// 登録処理
		System.out.println(userRequest);
		userService.create(userRequest);
		return "redirect:/user/list";
	}


    //  登録確認画面
    @PostMapping("/user/addcheck")
    public String toBookInfo(@ModelAttribute("userRequest") UserRequest userRequest,Model model) {
    	String errmsg = Common.getErr(userRequest.getName(),userRequest.getAddress(),userRequest.getPhone());
		if(errmsg != "") {
			model.addAttribute("errmsg",errmsg);
			return "/user/add";
		}
    	model.addAttribute("userRequest", userRequest);
        return "user/addcheck";
    }

    // 編集確認画面
    @PostMapping("/user/editcheck")
    public String editcheck(@ModelAttribute("userUpdateRequest") UserUpdateRequest userUpdateRequest,Model model) {
    	String errmsg = Common.getErr(userUpdateRequest.getName(),userUpdateRequest.getAddress(),userUpdateRequest.getPhone());
    	model.addAttribute("userUpdateRequest", userUpdateRequest);
		if(errmsg != "") {
			message = errmsg;	// エラーメッセージを代入
			return "forward:displayedit";		// エラー表示用の編集画面
		}
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
        model.addAttribute("userUpdateRequest", user);
      return "user/edit";
    }

    /**
     * エラーの編集画面を表示
     * @param errmsg 表示するエラーメッセージ
     * @param model Model
     * @return 編集画面
     */
    @RequestMapping(value="/user/displayedit", method=RequestMethod.POST)
    public String displayedit(@ModelAttribute("userUpdateRequest") UserUpdateRequest userUpdateRequest, Model model) {
		model.addAttribute("errmsg",message);	// 事前に代入したエラーメッセージを取得
        model.addAttribute("userUpdateRequest", userUpdateRequest);
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


    // 削除処理
    @RequestMapping(value="/user/delete", method=RequestMethod.POST)
    public String delete(@Validated @ModelAttribute UserDeleteRequest userDeleteRequest, BindingResult result, Model model) {

        // ユーザー情報の更新
        userService.delete(userDeleteRequest);
        return "redirect:/user/list";
    }






}