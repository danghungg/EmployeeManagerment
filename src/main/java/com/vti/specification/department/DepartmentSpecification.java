package com.vti.specification.department;


import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.vti.entity.Department;
import com.vti.form.DepartmentFilterForm;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class DepartmentSpecification {

	@SuppressWarnings("deprecation")
	public static Specification<Department> buildWhere(String search, DepartmentFilterForm filterForm) {

		Specification<Department> where = null;

		if (!StringUtils.isEmpty(search)) {
			search = search.trim();
//			CustomSpecification username = new CustomSpecification("username", search);
			CustomSpecification departmentName = new CustomSpecification("departmentName", search);
			where = Specification.where(departmentName);
		}

		if (filterForm != null && filterForm.getMinCreateDate() != null) {

			CustomSpecification minCreateDate = new CustomSpecification("minCreateDate", filterForm.getMinCreateDate());

			if (where == null) {
				where = minCreateDate;
			} else {
				where = where.and(minCreateDate);
			}
		}

		if (filterForm != null && filterForm.getMaxCreateDate() != null) {

			CustomSpecification maxCreateDate = new CustomSpecification("maxCreateDate", filterForm.getMaxCreateDate());

			if (where == null) {
				where = maxCreateDate;
			} else {
				where = where.and(maxCreateDate);
			}
		}

		if (filterForm != null && filterForm.getType() != null) {

			CustomSpecification type = new CustomSpecification("type", filterForm.getType());

			if (where == null) {
				where = type;
			} else {
				where = where.and(type);
			}
		}

		return where;
	}
}

@SuppressWarnings("serial")
@RequiredArgsConstructor
class CustomSpecification implements Specification<Department> {

	@NonNull
	private String field;
	@NonNull
	private Object value;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

		if (field.equalsIgnoreCase("username")) {
			Join join = root.join("accounts", JoinType.LEFT);
			return criteriaBuilder.like(join.get("username"), "%" + value.toString() + "%");
		}
		if (field.equalsIgnoreCase("departmentName")) {
//			Join join = root.join("accounts", JoinType.LEFT);
			return criteriaBuilder.like(root.get("name"), "%" + value.toString() + "%");
		}
		if (field.equalsIgnoreCase("minCreateDate")) {
			return criteriaBuilder.greaterThanOrEqualTo(root.get("createDate").as(java.sql.Date.class), (Date)value);
		}

		if (field.equalsIgnoreCase("maxCreateDate")) {
			return criteriaBuilder.lessThanOrEqualTo(root.get("createDate").as(java.sql.Date.class), (Date)value);
		}

		if (field.equalsIgnoreCase("type")) {
			return criteriaBuilder.equal(root.get("type"), value);
		}
		return null;
	}
}
