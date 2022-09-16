package com.example.inventory.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.goods.business.exception.GoodsCodeDupulicateException;
import com.example.goods.business.exception.NoGoodsException;
import com.example.goods.business.repository.GoodsRepository;
import com.example.inventory.business.domain.ReceivingShipmentOrder;
import com.example.inventory.business.domain.Stock;
import com.example.inventory.business.exception.NoReceivingShipmentOrderLogException;
import com.example.inventory.business.exception.NoStockException;
import com.example.inventory.business.exception.StockDeletedException;
import com.example.inventory.business.exception.StockNotEmptyException;
import com.example.inventory.business.exception.StockOverException;
import com.example.inventory.business.exception.StockUnderException;
import com.example.inventory.business.repository.StockRepository;

/** 在庫管理サービスクラス */
@Service
@Transactional(rollbackFor = Exception.class)
public class InventoryServiceImpl implements InventoryService {

	/*
	 * StockRepositoryのDI
	 */
	@Autowired
	private StockRepository stockRepository;

	/*
	 * GoodsRepositoryのDI
	 */
	@Autowired
	private GoodsRepository goodsRepository;

	/**
	 * 在庫商品の追加
	 */
	@Override
	public void createStock(Stock stock) throws GoodsCodeDupulicateException, NoGoodsException, StockDeletedException {
		canCreateStock(stock.getGoodsCode());
		stockRepository.createStock(stock);
	}

	/**
	 * 在庫商品を追加可能か検証
	 */
	@Transactional(readOnly = true)
	@Override
	public void canCreateStock(int goodsCode)
			throws GoodsCodeDupulicateException, NoGoodsException, StockDeletedException {
		//商品が商品テーブルに存在しない場合の例外処理
		if (goodsRepository.findGoods(goodsCode) == null) {
			throw new NoGoodsException();
		}
		//在庫が在庫テーブルに存在する場合の例外処理
		if (stockRepository.findStock(goodsCode) != null) {
			throw new GoodsCodeDupulicateException();
		}
		//在庫商品が削除済みの場合の例外処理
		if (stockRepository.isStockDeactive(goodsCode)) {
			throw new StockDeletedException();
		}
	}

	/**
	 * 全ての在庫商品を検索
	 */
	@Transactional(readOnly = true)
	@Override
	public List<Stock> findAllStock() throws NoStockException {
		List<Stock> stockList = stockRepository.findAllStock();
		if (stockList.isEmpty()) {
			throw new NoStockException();
		}
		return stockList;
	}

	/**
	 * 在庫商品を検索
	 */
	@Transactional(readOnly = true)
	@Override
	public Stock findStock(int goodsCode) throws NoStockException {
		Stock stock = stockRepository.findStock(goodsCode);
		if (stock == null) {
			throw new NoStockException(goodsCode);
		}
		return stock;
	}

	/**
	 *	在庫商品を削除
	 */
	@Override
	public void deleteStock(int goodsCode) throws NoStockException, StockDeletedException, StockNotEmptyException {
		canDeleteStock(goodsCode);
		int count = stockRepository.deleteStock(goodsCode);
		if (count == 0) {
			throw new NoStockException(goodsCode);
		}
	}

	/**
	 * 在庫商品を削除可能か検証
	 */
	@Transactional(readOnly = true)
	@Override
	public void canDeleteStock(int goodsCode) throws NoStockException, StockDeletedException, StockNotEmptyException {
		if (stockRepository.isStockDeactive(goodsCode)) {
			throw new StockDeletedException(goodsCode);
		}
		if (stockRepository.findStock(goodsCode) == null) {
			throw new NoStockException(goodsCode);
		}
		if (stockRepository.findStock(goodsCode).getQuantity() != 0) {
			throw new StockNotEmptyException(goodsCode);
		}
	}

	@Override
	public Stock receiveStock(ReceivingShipmentOrder receivingOrder) throws NoStockException, StockOverException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Stock shipStock(ReceivingShipmentOrder shipmentOrder) throws NoStockException, StockUnderException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<ReceivingShipmentOrder> findReceivingShipmentOrderLog() throws NoReceivingShipmentOrderLogException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
