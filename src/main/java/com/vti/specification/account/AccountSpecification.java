package com.vti.specification.account;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.vti.entity.Account;
import com.vti.form.AccountFilterForm;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class AccountSpecification {

	@SuppressWarnings("deprecation")
	public static Specification<Account> buildWhere(String search, AccountFilterForm filterForm) {
		
		Specification<Account> where = null;
		
		if (!StringUtils.isEmpty(search)) {
			search = search.trim();
			CustomSpecification username = new CustomSpecification("userName", search);
			CustomSpecification firstName = new CustomSpecification("firstName", search);
			CustomSpecification last_name = new CustomSpecification("lastName", search);
			where = Specification.where(username).or(firstName).or(last_name);
		}
		
		if (filterForm != null && filterForm.getRole() != null) {

			CustomSpecification role = new CustomSpecification("role", filterForm.getRole());

			if (where == null) {
				where = role;
			} else {
				where = where.and(role);
			}
		}
		
		// if there is filter by max id
		if (filterForm != null && filterForm.getDepartmentId() != null) {
			CustomSpecification departmentId = new CustomSpecification("departmentId", filterForm.getDepartmentId());
			if (where == null) {
				where = departmentId;
			} else {
				where = where.and(departmentId);
			}
		}

		return where;
	}
}

@SuppressWarnings("serial")
@RequiredArgsConstructor
class CustomSpecification implements Specification<Account> {

	@NonNull
	private String field;
	@NonNull
	private Object value;

	@Override
	public Predicate toPredicate(
			Root<Account> root, 
			CriteriaQuery<?> query, 
			CriteriaBuilder criteriaBuilder) {

		if (field.equalsIgnoreCase("username")) {
			return criteriaBuilder.like(root.get("userName"), "%" + value.toString() + "%");
		}
		if (field.equalsIgnoreCase("firstName")) {
			return criteriaBuilder.like(root.get("firstName"), "%" + value.toString() + "%");
		}
		if (field.equalsIgnoreCase("last_name")) {
			return criteriaBuilder.like(root.get("lastName"), "%" + value.toString() + "%");
		}
		
		if (field.equalsIgnoreCase("role")) {
			return criteriaBuilder.equal(root.get("role"), value);
		}
		
		if (field.equalsIgnoreCase("departmentId")) {
			return criteriaBuilder.equal(root.get("department").get("id"), value);
		}
		

		return null;
	}
}

