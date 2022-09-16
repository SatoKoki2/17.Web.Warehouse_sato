package com.example.inventory.business.exception;

public class NoStockException extends Exception {
	private static final long serialVersionUID = 1L;

	public NoStockException() {
		super();
	}

	public NoStockException(int goodsCode) {
		super(String.valueOf(goodsCode));
	}

	public NoStockException(String message) {
		super(message);
	}
}
