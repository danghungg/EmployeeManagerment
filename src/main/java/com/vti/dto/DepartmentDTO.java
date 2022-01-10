package com.vti.dto;

import java.util.Date;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vti.entity.Department.Type;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentDTO extends RepresentationModel<DepartmentDTO> {
	
	private int id;

	private String name;
	
	private int totalMember;

	private String type;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createDate;

	private List<AccountDTO> accounts;

	@Data
	@NoArgsConstructor
	static class AccountDTO {

		@JsonProperty("accountId")
		private int id;

		private String username;
	}
}

