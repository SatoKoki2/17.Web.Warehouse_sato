package com.example.inventory.business.service;

import java.util.List;

import com.example.goods.business.exception.GoodsCodeDupulicateException;
import com.example.goods.business.exception.NoGoodsException;
import com.example.inventory.business.domain.ReceivingShipmentOrder;
import com.example.inventory.business.domain.Stock;
import com.example.inventory.business.exception.NoReceivingShipmentOrderLogException;
import com.example.inventory.business.exception.NoStockException;
import com.example.inventory.business.exception.StockDeletedException;
import com.example.inventory.business.exception.StockNotEmptyException;
import com.example.inventory.business.exception.StockOverException;
import com.example.inventory.business.exception.StockUnderException;

/**
 * 在庫管理サービスのインタフェース
 */
public interface InventoryService {

	/**
	 * 在庫商品の追加
	 * @param stock 在庫商品
	 * @throws GoodsCodeDupulicateException 商品コードが重複した場合の例外処理
	 * @throws NoGoodsException 商品が存在しない場合の例外処理
	 * @throws StockDeletedException 在庫商品が削除されていた場合の例外処理
	 */
	void createStock(Stock stock) throws GoodsCodeDupulicateException, NoGoodsException, StockDeletedException;

	/**
	 * 在庫商品を追加可能か検証
	 * @param goodsCode 商品コード
	 * @throws GoodsCodeDupulicateException 商品コードが重複した場合の例外処理
	 * @throws NoGoodsException 商品が存在しない場合の例外処理
	 * @throws StockDeletedException 在庫商品が削除されていた場合の例外処理
	 */
	void canCreateStock(int goodsCode) throws GoodsCodeDupulicateException, NoGoodsException, StockDeletedException;

	/**
	 * 全ての在庫商品を検索
	 * @return Stockオブジェクトのリスト
	 * @throws NoStockException 在庫商品が存在しない場合の例外処理
	 */
	List<Stock> findAllStock() throws NoStockException;

	/**
	 * 在庫商品を検索
	 * @param goodsCode 商品コード
	 * @return 検索結果を保持するStockオブジェクト
	 * @throws NoStockException 在庫商品が存在しない場合の例外処理
	 */
	Stock findStock(int goodsCode) throws NoStockException;

	/**
	 * 在庫商品の削除
	 * @param goodsCode 商品コード
	 * @throws NoStockException 在庫商品が存在しない場合の例外処理
	 * @throws StockDeletedException 在庫商品が削除されていた場合の例外処理
	 * @throws StockNotEmptyException 在庫商品がまだ存在する場合の例外処理
	 */
	void deleteStock(int goodsCode) throws NoStockException, StockDeletedException, StockNotEmptyException;

	/**
	 * 在庫商品を削除可能か検証
	 * @param goodsCode 商品コード
	 * @throws NoStockException 在庫商品が存在しない場合の例外処理
	 * @throws StockDeletedException 在庫商品が削除されていた場合の例外処理
	 * @throws StockNotEmptyException 在庫商品がまだ存在する場合の例外処理
	 */
	void canDeleteStock(int goodsCode) throws NoStockException, StockDeletedException, StockNotEmptyException;

	Stock receiveStock(ReceivingShipmentOrder receivingOrder) throws NoStockException, StockOverException;

	Stock shipStock(ReceivingShipmentOrder shipmentOrder) throws NoStockException, StockUnderException;

	List<ReceivingShipmentOrder> findReceivingShipmentOrderLog() throws NoReceivingShipmentOrderLogException;
}
