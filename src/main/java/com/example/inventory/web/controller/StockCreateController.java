package com.example.inventory.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.goods.business.exception.GoodsCodeDupulicateException;
import com.example.goods.business.exception.NoGoodsException;
import com.example.goods.web.dto.GoodsCode;
import com.example.inventory.business.domain.Stock;
import com.example.inventory.business.exception.StockDeletedException;
import com.example.inventory.business.service.InventoryService;

import lombok.extern.log4j.Log4j2;

/**
 * 在庫商品登録用コントローラー
 */
@Controller
@RequestMapping("/inventory/stock_create")
@SessionAttributes("goodsCode")
@Log4j2
public class StockCreateController {

	/**
	 * InventoryServiceのDI
	 */
	@Autowired
	InventoryService stockService;

	/**
	 * HTTPリクエストのPost方式登録メソッド
	 * @param status ステータス
	 * @return redirect:inputに遷移
	 */
	@PostMapping("/input")
	public String input(SessionStatus status) {
		return "redirect:input";
	}

	/**
	 * HTTPリクエストのGet方式登録メソッド
	 * @param goodsCode 商品コード
	 * @return inventory/stock_create_inputに遷移
	 */
	@GetMapping("/input")
	public String input(GoodsCode goodsCode) {
		return "inventory/stock_create_input";
	}

	/**
	 * HTTPリクエストのPost方式登録確認メソッド
	 * @param goodsCode 商品コード
	 * @param errors 例外
	 * @return inventory/stock_create_inputまたはinventory/stock_create_confirmに遷移
	 * @throws NoGoodsException 商品が存在しない時の例外
	 * @throws GoodsCodeDupulicateException 商品コード重複時の例外
	 * @throws StockDeletedException 在庫商品が削除済みの時の例外
	 */
	@PostMapping("/confirm")
	public String confirm(@Validated GoodsCode goodsCode, Errors errors)
			throws NoGoodsException, GoodsCodeDupulicateException, StockDeletedException {
		if (errors.hasErrors()) {
			log.warn(errors);
			return "inventory/stock_create_input";
		}
		stockService.canCreateStock(goodsCode.getCode());
		log.info(goodsCode);
		return "inventory/stock_create_confirm";
	}

	/**
	 * HTTPリクエストのPost方式登録完了メソッド
	 * @param goodsCode 商品コード
	 * @return redirect:completeに遷移
	 * @throws NoGoodsException 商品が存在しない時の例外
	 * @throws GoodsCodeDupulicateException 商品コード重複時の例外
	 * @throws StockDeletedException 在庫商品が削除済みの時の例外
	 */
	@PostMapping("/complete")
	public String complete(@Validated GoodsCode goodsCode)
			throws NoGoodsException, GoodsCodeDupulicateException, StockDeletedException {
		//Stock型の変数インスタンスの生成
		Stock stock = new Stock(goodsCode.getCode(), 0);
		stockService.createStock(stock);
		log.info(stock);
		return "redirect:complete";
	}

	/**
	 * HTTPリクエストのGet方式登録完了メソッド
	 * @return inventory/stock_create_completeに遷移
	 */
	@GetMapping("/complete")
	public String complete() {
		return "inventory/stock_create_complete";
	}

	/**
	 * 商品テーブルに存在しない商品の例外処理メソッド
	 * @param model エラーメッセージを設定
	 * @param e 存在しない商品の例外
	 * @return redirect:inputに遷移
	 */
	@ExceptionHandler(NoGoodsException.class)
	public String handleNoGoodsException(
			RedirectAttributes model, NoGoodsException e) {
		model.addFlashAttribute("errorCode", "error.goods.no.data");
		log.warn(model, e);
		return "redirect:input";
	}

	/**
	 * 在庫商品が重複した場合の例外処理メソッド
	 * @param model エラーメッセージを設定
	 * @param e 商品重複時の例外
	 * @return redirect:inputに遷移
	 */
	@ExceptionHandler(GoodsCodeDupulicateException.class)
	public String handleGoodsCodeDupulicateException(
			RedirectAttributes model, GoodsCodeDupulicateException e) {
		model.addFlashAttribute("errorCode",
				"error.inventory.goodscode.dupulicate");
		log.warn(model, e);
		return "redirect:input";
	}

	/**
	 * 論理削除済み在庫商品の例外処理メソッド
	 * @param model エラーメッセージを設定
	 * @param e 在庫商品を削除した場合の例外
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
}
