package com.example.inventory.business.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.example.inventory.business.exception.NoStockException;
import com.example.inventory.business.exception.StockDeletedException;
import com.example.inventory.business.exception.StockNotEmptyException;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
public class InventoryServiceTest {

	@Autowired
	InventoryService stockService;

	/**
	 * 在庫商品登録テスト(正常系)
	
	@Test
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	@ExpectedDatabase(value = "../EXPECTED_CREATE_STOCK_DATA.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testCreateStock_正常系() throws Exception {
		Stock stock = new Stock(7, 0);
		stockService.createStock(stock);
	}
	
	/**
	 * 登録可能検証テスト(正常系)
	
	@Test
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	public void testCanCreateStock_正常系() throws Exception {
		stockService.canCreateStock(7);
		assertTrue(true);
		return;
	}
	
	/**
	 * 登録可能検証テスト(異常系)※存在しない商品
	
	@Test(expected = NoGoodsException.class)
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	public void testCanCreateStock_異常系_存在しない商品() throws Exception {
		stockService.canCreateStock(9);
		fail();
	}
	
	/**
	 * 登録可能検証テスト(異常系)※削除済み商品
	
	@Test(expected = NoGoodsException.class)
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	public void testCanCreateStock_異常系_削除済み在庫商品() throws Exception {
		stockService.canCreateStock(3);
		fail();
	}
	
	/**
	 * 登録可能検証テスト(異常系)※登録済み商品
	
	@Test(expected = GoodsCodeDupulicateException.class)
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	public void testCanCreateStock_異常系_登録済み在庫商品() throws Exception {
		stockService.canCreateStock(1);
		fail();
	}
	
	/**
	 * 登録可能検証テスト(異常系)※存在しない商品
	
	@Test(expected = StockDeletedException.class)
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	public void testCanCreateStock_異常系_削除済み在庫商品コード() throws Exception {
		stockService.canCreateStock(2);
		fail();
	}
	
	/**
	 * 単一検索テスト(正常系)
	
	@Test
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	public void testFindStock_正常系() throws Exception {
		Stock stock = stockService.findStock(1);
	
		assertEquals(1, stock.getGoodsCode().intValue());
		assertEquals(35, stock.getQuantity().intValue());
	}
	
	/**
	 * 単一検索テスト(異常系)※削除済み商品コード
	
	@Test(expected = NoStockException.class)
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	public void testFindStock_異常系_削除済み商品コード() throws Exception {
		stockService.findStock(3);
		fail();
	}
	
	/**
	 * 単一検索テスト(異常系)※存在しない商品コード
	
	@Test(expected = NoStockException.class)
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	public void testFindStock_異常系_存在しない商品コード() throws Exception {
		stockService.findStock(777);
		fail();
	}
	
	/**
	 * 全件検索テスト(正常系)
	
	@Test
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	public void testFindAllStock_正常系() throws Exception {
		List<Stock> stockList = stockService.findAllStock();
	
		if (stockList.size() != 3) {
			fail();
		}
	
		Stock stock = null;
		stock = stockList.get(0);
		assertEquals(0, stock.getGoodsCode().intValue());
		assertEquals(10, stock.getQuantity().intValue());
		stock = stockList.get(1);
		assertEquals(1, stock.getGoodsCode().intValue());
		assertEquals(35, stock.getQuantity().intValue());
		stock = stockList.get(2);
		assertEquals(6, stock.getGoodsCode().intValue());
		assertEquals(0, stock.getQuantity().intValue());
	}
	
	/**
	 * 全件検索テスト(異常系)
	
	@Test(expected = NoStockException.class)
	@DatabaseSetup("../INPUT_INVENTORY_EMPTY_DATA.xml")
	public void testFindAllStock_異常系_1件もない() throws Exception {
		List<Stock> stockList = stockService.findAllStock();
		for (Stock stock : stockList) {
			System.out.println(stock);
		}
		fail();
	}
	*/

	/**
	 * 在庫商品削除テスト(正常系)
	 */
	@Test
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	@ExpectedDatabase(value = "../EXPECTED_DELETE_STOCK_DATA.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testDeleteStock_正常系() throws Exception {
		stockService.deleteStock(6);
	}

	/**
	 * 在庫商品削除テスト(異常系)※在庫商品が存在しない
	 */
	@Test(expected = NoStockException.class)
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	public void testDeleteStock_異常系_存在しない商品コード() throws Exception {
		stockService.deleteStock(1001);
		fail();
	}

	/**
	 * 在庫商品削除テスト(異常系)※在庫商品が既に削除済み
	 */
	@Test(expected = StockDeletedException.class)
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	public void testDeleteStock_異常系_削除済みの商品コード() throws Exception {
		stockService.deleteStock(5);
		fail();
	}

	/**
	 * 在庫商品削除テスト(異常系)※在庫商品がまだテーブルに存在する
	 */
	@Test(expected = StockNotEmptyException.class)
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	public void testDeleteStock_異常系_在庫数が０でない商品コード() throws Exception {
		stockService.deleteStock(0);
		fail();
	}

	/**
	 * 在庫商品削除可能検証テスト(正常系)
	 */
	@Test
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	public void testCanDeleteStock_正常系() throws Exception {
		stockService.canDeleteStock(6);
		assertTrue(true);
		return;
	}

	/**
	 * 在庫商品削除可能検証テスト(異常系)※在庫商品が存在しない
	 */
	@Test(expected = NoStockException.class)
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	public void testCanDeleteStock_異常系_存在しない商品コード() throws Exception {
		stockService.canDeleteStock(1001);
		fail();
	}

	/**
	 * 在庫商品削除可能検証テスト(異常系)※在庫商品が既に削除済み
	 */
	@Test(expected = StockDeletedException.class)
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	public void testCanDeleteStock_異常系_削除済みの商品コード() throws Exception {
		stockService.canDeleteStock(5);
		fail();
	}

	/**
	 * 在庫商品削除可能検証テスト(異常系)※在庫商品がまだテーブルに存在する
	 */
	@Test(expected = StockNotEmptyException.class)
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	public void testCanDeleteStock_異常系_在庫数が０でない商品コード() throws Exception {
		stockService.canDeleteStock(0);
		fail();
	}

}