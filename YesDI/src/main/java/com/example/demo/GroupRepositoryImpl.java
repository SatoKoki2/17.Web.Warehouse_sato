package com.example.demo;

import org.springframework.stereotype.Repository;

@Repository
public class GroupRepositoryImpl implements GroupRepository {

	@Override
	//　今回はDBに接続していないが、DBに接続したつもりで
	public String selectByNo(String number) {
		String name;
		switch (number) {
		case "10000":
			name = "SS1部";
			break;
		case "10001":
			name = "SS2部";
			break;
		default:
			name = "登録されていません";
			break;
		}
		return name;
	}
}
