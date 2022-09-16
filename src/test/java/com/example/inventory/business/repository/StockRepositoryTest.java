package com.example.inventory.business.repository;

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
public class StockRepositoryTest {

	@Autowired
	StockRepository stockRepository;

	/**
	 * 在庫商品登録テスト(正常系)
	
	@Test
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	@ExpectedDatabase(value = "../EXPECTED_CREATE_STOCK_DATA.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testCreateStock_正常系() throws Exception {
		Stock stock = new Stock(7, 0);
		stockRepository.createStock(stock);
	}
	
	/**
	 * 在庫商品登録テスト(異常系)※既に登録されている在庫商品
	
	@Test(expected = DuplicateKeyException.class)
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	public void testCreateStock_異常系_既に登録されている在庫商品() {
		Stock stock = new Stock(0, 0);
		stockRepository.createStock(stock);
	}
	
	/**
	 * 在庫商品登録テスト(異常系)※既に削除されている在庫商品
	
	@Test(expected = DuplicateKeyException.class)
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	public void testCreateStock_異常系_既に削除済されている在庫商品() {
		Stock stock = new Stock(4, 0);
		stockRepository.createStock(stock);
	}
	
	/**
	 * 在庫商品の状態チェックテスト(正常系)
	
	@Test
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	public void testIsStockDeactive_正常系_登録されている在庫商品コード() throws Exception {
		boolean isStockDeactive = stockRepository.isStockDeactive(1);
		assertFalse(isStockDeactive);
	}
	
	/**
	 * 在庫商品の状態チェックテスト(異常系)
	
	@Test
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	public void testIsStockDeactive_異常系_未登録の商品コード() throws Exception {
		boolean isStockDeactive = stockRepository.isStockDeactive(999);
		assertFalse(isStockDeactive);
	}
	
	/**
	 * 単一検索テスト(正常系)
	
	@Test
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	public void testFindStock_正常系() throws Exception {
		Stock stock = stockRepository.findStock(1);
	
		assertEquals(1, stock.getGoodsCode().intValue());
		assertEquals(35, stock.getQuantity().intValue());
	}
	
	/**
	 * 単一検索テスト(異常系)
	
	@Test
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	public void testFindStock_異常系_存在しない商品コード() throws Exception {
		Stock stock = stockRepository.findStock(777);
		if (stock == null) {
			assertTrue(true);
			return;
		}
		fail();
	}
	
	/**
	 * 単一検索テスト(異常系)
	
	@Test
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	public void testFindStock_異常系_既に削除済されている商品コード() throws Exception {
		Stock stock = stockRepository.findStock(3);
		if (stock == null) {
			assertTrue(true);
			return;
		}
		fail();
	}
	
	/**
	 * 全件検索テスト(正常系)
	
	@Test
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	public void testFindAllStock_正常系() throws Exception {
		List<Stock> stockList = stockRepository.findAllStock();
	
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
	
	@Test
	@DatabaseSetup("../INPUT_INVENTORY_EMPTY_DATA.xml")
	public void testFindAllStock_異常系_1件もない() throws Exception {
		List<Stock> stockList = stockRepository.findAllStock();
		if (stockList.isEmpty()) {
			assertTrue(true);
			return;
		}
		for (Stock stock : stockList) {
			System.out.println(stock);
		}
		fail();
	}
	
	/**
	 * 在庫商品削除テスト(正常系)
	 */
	@Test
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	@ExpectedDatabase(value = "../EXPECTED_DELETE_STOCK_DATA.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void testDeleteStock_正常系() throws Exception {
		stockRepository.deleteStock(6);
	}

	/**
	 * 在庫商品削除テスト(異常系)※在庫商品が存在しない
	 */
	@Test
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	public void testDeleteStock_異常系_存在しない商品コード() throws Exception {
		int deleteCount = stockRepository.deleteStock(1001);
		if (deleteCount == 0) {
			assertTrue(true);
			return;
		}
		fail();
	}

	/**
	 * 在庫商品削除テスト(異常系)※在庫商品が既に削除済み
	 */
	@Test
	@DatabaseSetup("../INPUT_INVENTORY_DATA.xml")
	public void testDeleteStock_異常系_削除済みの商品コード() throws Exception {
		int deleteCount = stockRepository.deleteStock(3);
		if (deleteCount == 0) {
			assertTrue(true);
			return;
		}
		fail();
	}
}