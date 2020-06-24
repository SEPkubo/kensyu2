package com.example.demo.dto;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * ユーザー情報更新リクエストデータ
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)

public class UserDeleteRequest extends UserUpdateRequest implements Serializable {
	  /**
	   * 削除フラグ
	   */
	  private String delete_flg;

}
