package com.example.demo;

import org.springframework.stereotype.Service;

@Service
public class ShainServiceImpl implements ShainService {

	private final ShainRepository shainRepository;

	//ShainRepositoryのDI
	public ShainServiceImpl(ShainRepository shainRepository) {
		this.shainRepository = shainRepository;
	}

	@Override
	public String findByNo(String number) {
		//リポジトリから社員を選択
		String name = shainRepository.selectByNo(number);
		return name;
	}

}