package com.example.demo.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDeleteRequest;
import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;


/**
 * ユーザー情報 Service
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class UserService {

	/**
	 * ユーザー情報 Repository
	 */
	@Autowired
	UserRepository userRepository;

	/**
	 * ユーザー情報 全検索
	 * @return 検索結果
	 */
	public List<User> searchAll() {
		return userRepository.findAll();
	}

	// 一覧取得(検索条件がある場合)
	 public Page<User> searchAddress(Pageable pageable,String address) {

		 return userRepository.findAddress(address,pageable);

	 }




	// 一覧取得(検索条件がない場合)
	public Page<User> getList(Pageable pageable) {
        return userRepository.findAll(pageable);
    }



	/**
	 * ユーザー情報新規登録
	 * @param user ユーザー情報
	 */
	public void create(UserRequest userRequest) {
		userRepository.save(CreateUser(userRequest));
	}

	/**
	 * ユーザーTBLエンティティの生成
	 * @param userRequest ユーザー情報リクエストデータ
	 * @return ユーザーTBLエンティティ
	 */
	private User CreateUser(UserRequest userRequest) {
		User user = new User();
		user.setName(userRequest.getName());
		user.setAddress(userRequest.getAddress());
		user.setPhone(userRequest.getPhone());
		user.setDelete_flg(0);

		return user;
	}

	/**
     * ユーザー情報 主キー検索
     * @return 検索結果
     */
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }



	 /**
     * ユーザー情報 更新
     * @param user ユーザー情報
     */
    public void update(UserUpdateRequest userUpdateRequest) {
        User user = findById(userUpdateRequest.getId());
        user.setAddress(userUpdateRequest.getAddress());
        user.setName(userUpdateRequest.getName());
        user.setPhone(userUpdateRequest.getPhone());
        user.setDelete_flg(0);
        userRepository.save(user);
    }

	 /**
     * ユーザー情報 更新
     * @param user ユーザー情報
     */
    public void delete(UserDeleteRequest userDeleteRequest) {
        User user = findById(userDeleteRequest.getId());
        user.setDelete_flg(1);
        userRepository.save(user);
    }

	public List<Map<String, Object>> queryForList(String string) {
		return null;
	}


}
