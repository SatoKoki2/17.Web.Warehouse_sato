package com.example.demo;

import org.springframework.stereotype.Repository;

@Repository
public class ShainRepositoryImpl implements ShainRepository {
	@Override
	//　今回はDBに接続していないが、DBに接続したつもりで
	public String selectByNo(String no) {
		String name;
		switch (no) {
		case "100":
			name = "佐藤";
			break;
		case "101":
			name = "鈴木";
			break;
		default:
			name = "登録されていません";
			break;
		}
		return name;
	}
}