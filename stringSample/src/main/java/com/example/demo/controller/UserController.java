package com.example.demo.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import com.example.demo.pagewrapper.PageWrapper;
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
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public String displayList(@PageableDefault(page = 0, size = 10) Pageable pageable,Model model) throws UnsupportedEncodingException {
		Page<User> userlist = userService.getList(pageable);
		 PageWrapper<User> page = new PageWrapper<User>(userlist, "/user/list");

		 for(int i = 0; i < userlist.getContent().size(); ++i){	// 電話番号のハイフン表示
	            String tel = userlist.getContent().get(i).getPhone();
	            if (tel.getBytes("UTF-8").length == 11) {	// 電話番号が11文字か判定
					tel = new StringBuilder(tel)
							.insert(7, '-')
							.insert(3, '-')
							.toString();
					userlist.getContent().get(i).setPhone(tel);			// ハイフンを付けた電話番号をリストに戻す
				}
	        }


		model.addAttribute("page", page);
		model.addAttribute("userlist",userlist.getContent());
		return "user/list";
	}




	/**
	 * 住所検索の一覧画面を表示
	 * @param model Model
	 * @return 住所検索の一覧画面
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/user/listsearch", method = RequestMethod.GET)
	public String displayListsearch(@PageableDefault(page = 0, size = 10) Pageable pageable,@RequestParam(name = "Serchaddress") String Serchaddress,
			Model model) throws UnsupportedEncodingException {
		System.out.println("a");
		Page<User> userlist = userService.searchAddress(pageable,Serchaddress);
		 PageWrapper<User> page = new PageWrapper<User>(userlist, "/user/listsearch/Serchaddress=" + Serchaddress);




		 for(int i = 0; i < userlist.getContent().size(); ++i){	// 電話番号のハイフン表示
	            String tel = userlist.getContent().get(i).getPhone();
	            if (tel.getBytes("UTF-8").length == 11) {	// 電話番号が11文字か判定
					tel = new StringBuilder(tel)
							.insert(7, '-')
							.insert(3, '-')
							.toString();
					userlist.getContent().get(i).setPhone(tel);			// ハイフンを付けた電話番号をリストに戻す
				}
	        }

		model.addAttribute("page", page);
		model.addAttribute("userlist",userlist.getContent());
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

		userRequest.setPhone(userRequest.getPhone().replace("-", ""));		// 電話番号のハイフンを除外
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
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/user/{id}")
    public String displayView(@PathVariable Long id, Model model) throws UnsupportedEncodingException {
    	User user = userService.findById(id);
    	String tel = user.getPhone();
        if (tel.getBytes("UTF-8").length == 11) {	// 電話番号が11文字か判定
			tel = new StringBuilder(tel)			// 電話番号のハイフン表示
					.insert(7, '-')
					.insert(3, '-')
					.toString();
			user.setPhone(tel);			// ハイフンを付けた電話番号をリストに戻す
		}

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
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/user/{id}/delete")
    public String delete(@PathVariable Long id, Model model) throws UnsupportedEncodingException {
    	User user = userService.findById(id);
    	String tel = user.getPhone();
        if (tel.getBytes("UTF-8").length == 11) {	// 電話番号が11文字か判定
			tel = new StringBuilder(tel)			// 電話番号のハイフン表示
					.insert(7, '-')
					.insert(3, '-')
					.toString();
			user.setPhone(tel);			// ハイフンを付けた電話番号をリストに戻す
		}
        model.addAttribute("userRequest", user);
      return "user/delete";
    }



    // 更新処理
    @RequestMapping(value="/user/update", method=RequestMethod.POST)
    public String update(@Validated @ModelAttribute UserUpdateRequest userUpdateRequest, BindingResult result, Model model) {
    	userUpdateRequest.setPhone(userUpdateRequest.getPhone().replace("-", ""));		// 電話番号のハイフンを除外
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