package com.example.inventory.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.goods.web.dto.GoodsCode;
import com.example.inventory.business.domain.Stock;
import com.example.inventory.business.exception.NoStockException;
import com.example.inventory.business.service.InventoryService;

import lombok.extern.log4j.Log4j2;

/**
 * 在庫商品単一検索用コントローラー
 */
@Controller
@RequestMapping("/inventory/stock_find")
@Log4j2
public class StockFindController {

	/**
	 * InventoryServiceのDI
	 */
	@Autowired
	InventoryService stockService;

	/**
	 * HTTPリクエストのGet方式検索メソッド
	 * @param goodsCode 商品コード
	 * @return inventory/stock_find_inputに遷移
	 */
	@GetMapping("/input")
	public String input(GoodsCode goodsCode) {
		return "inventory/stock_find_input";
	}

	/**
	 * HTTPリクエストのPost方式検索完了メソッド
	 * @param goodsCode 商品コード
	 * @param errors エラー
	 * @return inventory/stock_find_inputまたはredirectに遷移
	 */
	@PostMapping("/complete")
	public String complete(@Validated GoodsCode goodsCode, Errors errors) {
		if (errors.hasErrors()) {
			log.warn(errors);
			return "inventory/stock_find_input";
		}
		log.info(goodsCode);
		return "redirect:" + goodsCode.getCode();
	}

	/**
	 * HTTPリクエストのGet方式検索完了メソッド
	 * @param goodsCode 商品コード
	 * @param status ステータス
	 * @param model エラーメッセージを設定
	 * @return inventory/stock_find_completeに遷移
	 * @throws NoStockException 在庫商品が存在しない場合の例外
	 */
	@GetMapping("/{goodsCode}")
	public String show(@PathVariable("goodsCode") int goodsCode, SessionStatus status, Model model)
			throws NoStockException {
		Stock stock = stockService.findStock(goodsCode);
		model.addAttribute("stock", stock);
		return "inventory/stock_find_complete";
	}

	/**
	 * 在庫商品が存在しない場合の例外処理
	 * @param model エラーメッセージを設定
	 * @param e 例外
	 * @return redirect:inputに遷移
	 */
	@ExceptionHandler(NoStockException.class)
	public String handleNoStockException(
			RedirectAttributes model, NoStockException e) {
		model.addFlashAttribute("errorCode",
				"error.stock.no.data");
		log.warn(model, e);
		return "redirect:input";
	}
}
