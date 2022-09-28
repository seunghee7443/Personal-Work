package com.furence.modo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DataBase와 Mapping하기 위한 Class
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Userinfo {
	private String id;
	private String pwd;
	private String name;
	private String level;
	private String desc;
	private String reg_date;
}
