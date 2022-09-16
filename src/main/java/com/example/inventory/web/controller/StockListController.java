package com.example.inventory.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.inventory.business.domain.Stock;
import com.example.inventory.business.exception.NoStockException;
import com.example.inventory.business.service.InventoryService;

import lombok.extern.log4j.Log4j2;

/**
 * 在庫商品全件検索用コントローラー
 */
@Controller
@RequestMapping("/inventory")
@Log4j2
public class StockListController {

	/**
	 * InventoryServiceのDI
	 */
	@Autowired
	InventoryService stockService;

	/**
	 * 在庫商品の全件検索
	 * @param model エラーメッセージを設定
	 * @return inventory/stock_listに遷移
	 * @throws NoStockException  在庫商品が存在しない時の例外
	 */
	@GetMapping("/stock_list")
	public String showList(Model model) throws NoStockException {
		List<Stock> stockList = stockService.findAllStock();
		model.addAttribute("stockList", stockList);
		log.info(stockList);
		return "inventory/stock_list";
	}

	/**
	 * 在庫テーブルに存在しない在庫商品の例外処理
	 * @param model エラーメッセージを設定
	 * @param e 例外
	 * @return inventory/stock_listに遷移
	 */
	@ExceptionHandler(NoStockException.class)
	public String handleNoStockException(Model model, NoStockException e) {
		model.addAttribute("errorCode",
				"error.stock.no.data");
		log.warn(model, e);
		return "inventory/stock_list";
	}
}
