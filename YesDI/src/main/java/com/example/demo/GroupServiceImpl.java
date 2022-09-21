package com.example.demo;

import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {

	private final GroupRepository GROUPREPOSITORY;

	//GroupRepositoryのDI
	public GroupServiceImpl(GroupRepository groupRepository) {
		this.GROUPREPOSITORY = groupRepository;
	}

	@Override
	public String findByNo(String number) {
		//リポジトリからグループを選択
		//(不要)GroupRepository groupRepository = new GroupRepositoryImpl();
		String name = GROUPREPOSITORY.selectByNo(number);
		return name;
	}
}
