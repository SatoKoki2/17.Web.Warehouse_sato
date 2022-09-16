package com.example.inventory.business.domain;

import static com.example.common.constant.RuleConstant.*;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 在庫商品ドメイン
 */
@Data
public class Stock implements Serializable {

	/**
	 * 商品コード
	 * 在庫数
	 */
	@NotNull
	@Min(MIN_GOODS_CODE)
	@Max(MAX_GOODS_CODE)
	private Integer goodsCode;
	private Integer quantity;

	/**
	 * デフォルトコンストラクタ
	 */
	public Stock() {
	}

	/**
	 * コンストラクタ
	 * @param goodsCode 商品コード
	 * @param quantity 在庫数
	 */
	public Stock(Integer goodsCode, Integer quantity) {
		this.goodsCode = goodsCode;
		this.quantity = quantity;
	}
}
