package com.example.demo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class ShainForm {

	@NotEmpty(message = " 社員Noを入れてくださいね")
	@Pattern(regexp = "\\d{3}", message = "半角数字、3桁でお願いいたします")
	private String number;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

}