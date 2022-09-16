package com.example.inventory.business.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.inventory.business.domain.Stock;

/**
 * STOCKテーブルアクセス用リポジトリ
 */
@Mapper
public interface StockRepository {

	/**
	 * 在庫商品ドメインで在庫商品を追加
	 * <br>
	 * @param stock 在庫商品ドメイン
	 */
	@Insert("insert into STOCK(GOODS_CODE, QUANTITY, STATUS) values(#{goodsCode}, #{quantity}, 'ACTIVE')")
	void createStock(Stock stock);

	/**
	 * 商品コードで在庫商品を検索。
	 * <br>
	 * @param goodsCode 商品コード
	 * @return 対象商品コードが論理削除済みである場合(true)/削除済みでない、又は存在しない場合(false)
	 */
	@Select("select count(*) = 1 from STOCK where GOODS_CODE = #{goodsCode} and STATUS = 'DEACTIVE'")
	boolean isStockDeactive(@Param("goodsCode") int goodsCode);

	/**
	 * 商品コードで在庫商品を検索。
	 * <br>
	 * 0件の場合は、nullを返却する。
	 * <br>
	 * @param goodsCode 商品コード
	 * @return 検索結果のレコード情報を保持するSTOCKオブジェクト
	 */
	@Select("select GOODS_CODE, QUANTITY from STOCK where GOODS_CODE = #{goodsCode} and STATUS = 'ACTIVE'")
	Stock findStock(@Param("goodsCode") int goodsCode);

	/**
	 * 在庫の全量を検索。
	 * <br>
	 * 0件の場合は、nullを返却する。
	 * <br>
	 * @return StockオブジェクトのList
	 */
	@Select("select GOODS_CODE, QUANTITY from STOCK where STATUS = 'ACTIVE'")
	List<Stock> findAllStock();

	/**
	 * 商品コードで商品を論理削除
	 * @param goodsCode 商品コード
	 * @return 更新した場合と更新できない場合
	 */
	@Update("update STOCK set STATUS = 'DEACTIVE' where GOODS_CODE = #{goodsCode} and STATUS = 'ACTIVE'")
	int deleteStock(@Param("goodsCode") int goodsCode);

	/**
	 * 商品コードで在庫数を変更
	 * @param stock 在庫商品
	 * @return 更新した場合と更新できない場合
	 */
	@Update("update STOCK set STATUS = 'DEACTIVE' where GOODS_CODE = #{code} and STATUS = 'ACTIVE'")
	int updateStock(Stock stock);
}
