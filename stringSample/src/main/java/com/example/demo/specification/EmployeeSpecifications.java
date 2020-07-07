package com.example.demo.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.example.demo.entity.User;

public class EmployeeSpecifications {		// 住所検索
    public static Specification<User> empnameContains(final String empname) {
        return StringUtils.isEmpty(empname) ? null : new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("empname"), "%" + empname + "%");
            }


        };
    }

}
