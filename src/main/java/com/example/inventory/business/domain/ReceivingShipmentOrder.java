package com.example.inventory.business.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * 入出荷管理クラス
 */
@Data
public class ReceivingShipmentOrder implements Serializable {

	private Integer no;
	private String orderType;
	private Integer goodsCode;
	private Integer quantity;

}
