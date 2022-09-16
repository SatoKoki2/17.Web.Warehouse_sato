package com.example.inventory.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.goods.web.dto.GoodsCode;
import com.example.inventory.business.domain.Stock;
import com.example.inventory.business.exception.NoStockException;
import com.example.inventory.business.exception.StockDeletedException;
import com.example.inventory.business.exception.StockNotEmptyException;
import com.example.inventory.business.service.InventoryService;

import lombok.extern.log4j.Log4j2;

/**
 * 在庫商品削除用コントローラー
 */
@Controller
@RequestMapping("/inventory/stock_delete")
@SessionAttributes({ "goodsCode", "stock" })
@Log4j2
public class StockDeleteController {

	/**
	 * InventoryServiceのDI
	 */
	@Autowired
	InventoryService stockService;

	/**
	 * HTTPリクエストのPost方式削除メソッド
	 * @param status ステータス
	 * @return redirect:inputに遷移
	 */
	@PostMapping("/input")
	public String input(SessionStatus status) {
		status.setComplete();
		return "redirect:input";
	}

	/**
	 * HTTPリクエストのGet方式削除メソッド
	 * @param goodsCode 商品コード
	 * @return inventory/stock_delete_inputに遷移
	 */
	@GetMapping("/input")
	public String input(GoodsCode goodsCode) {
		return "inventory/stock_delete_input";
	}

	/**
	 * HTTPリクエストのPost方式削除確認メソッド
	 * @param goodsCode 商品コード
	 * @param errors エラー
	 * @return inventory/stock_delete_inputまたはinventory/stock_delete_confirmに遷移
	 * @throws NoStockException 在庫商品が存在しない時の例外
	 * @throws StockDeletedException 在庫商品が削除済みの時の例外
	 * @throws StockNotEmptyException 在庫商品がまだ存在する時の例外
	 */
	@PostMapping("/confirm")
	public String confirm(@Validated GoodsCode goodsCode, Errors errors, Model model)
			throws NoStockException, StockDeletedException, StockNotEmptyException {
		if (errors.hasErrors()) {
			log.warn(errors);
			return "inventory/stock_delete_input";
		}
		stockService.canDeleteStock(goodsCode.getCode());
		Stock stock = stockService.findStock(goodsCode.getCode());
		model.addAttribute("stock", stock);
		log.info(stock);
		return "inventory/stock_delete_confirm";
	}

	/**
	 * HTTPリクエストのPost方式削除完了メソッド
	 * @param goodsCode 商品コード
	 * @return redirect:completeに遷移
	 * @throws NoStockException 在庫商品が存在しない時の例外
	 * @throws StockDeletedException 在庫商品が削除済みの時の例外
	 * @throws StockNotEmptyException 在庫商品がまだ存在する時の例外
	 */
	@PostMapping("/complete")
	public String complete(@Validated GoodsCode goodsCode)
			throws NoStockException, StockDeletedException, StockNotEmptyException {
		stockService.deleteStock(goodsCode.getCode());
		log.info(goodsCode);
		return "redirect:complete";
	}

	/**
	 * HTTPリクエストのGet方式削除完了メソッド
	 * @return inventory/stock_delete_completeに遷移
	 */
	@GetMapping("/complete")
	public String complete() {
		return "inventory/stock_delete_complete";
	}

	/**
	 * 商品テーブルに存在しない商品の例外処理メソッド
	 * @param model エラーメッセージを設定
	 * @param e 存在しない商品の例外
	 * @return redirect:inputに遷移
	 */
	@ExceptionHandler(NoStockException.class)
	public String handleNoStockException(
			RedirectAttributes model, NoStockException e) {
		model.addFlashAttribute("errorCode", "error.stock.no.data");
		log.warn(model, e);
		return "redirect:input";
	}

	/**
	 * 論理削除済み在庫商品の例外処理メソッド
	 * @param model エラーメッセージを設定
	 * @param e 削除した在庫商品の例外
	 * @return redirect:inputに遷移
	 */
	@ExceptionHandler(StockDeletedException.class)
	public String handleStockDeletedException(
			RedirectAttributes model, StockDeletedException e) {
		model.addFlashAttribute("errorCode",
				"error.inventory.code.delete");
		log.warn(model, e);
		return "redirect:input";
	}

	/**
	 * 在庫数が0ではない場合の例外処理メソッド
	 * @param model エラーメッセージを設定
	 * @param e 在庫商品がまだ存在する場合の例外
	 * @return redirect:inputに遷移
	 */
	@ExceptionHandler(StockNotEmptyException.class)
	public String handleStockNotEmptyException(
			RedirectAttributes model, StockNotEmptyException e) {
		model.addFlashAttribute("errorCode",
				"error.inventory.code.notempty");
		log.warn(model, e);
		return "redirect:input";
	}
}
