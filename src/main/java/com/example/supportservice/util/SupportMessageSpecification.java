package com.example.supportservice.util;


import com.example.supportservice.domain.entity.ResultMessage;
import com.example.supportservice.domain.entity.SupportMessage;
import com.example.supportservice.web.dto.SupportMessageFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SupportMessageSpecification {

    public static Specification<SupportMessage> getSpecification(SupportMessageFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getTimestampFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("timestamp"), filter.getTimestampFrom()));
            }

            if (filter.getTimestampTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("timestamp"), filter.getTimestampTo()));
            }

            if (filter.getUserId() != null && !filter.getUserId().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("userId")), "%" + filter.getUserId().toLowerCase() + "%"));
            }

            if (filter.getSupportLevel() != null) {
                predicates.add(cb.equal(root.get("supportLevel"), filter.getSupportLevel()));
            }

            if (filter.getMessageContent() != null && !filter.getMessageContent().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("message")), "%" + filter.getMessageContent().toLowerCase() + "%"));
            }

            if (filter.getEmail() != null && !filter.getEmail().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("email")), "%" + filter.getEmail().toLowerCase() + "%"));
            }

            if (filter.getEndpoint() != null && !filter.getEndpoint().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("endpoint")), "%" + filter.getEndpoint().toLowerCase() + "%"));
            }

            if (filter.getLogin() != null && !filter.getLogin().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("login")), "%" + filter.getLogin().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
    public static Specification<ResultMessage> getSpecificationResult(SupportMessageFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getTimestampFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("timestamp"), filter.getTimestampFrom()));
            }

            if (filter.getTimestampTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("timestamp"), filter.getTimestampTo()));
            }

            if (filter.getUserId() != null && !filter.getUserId().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("userId")), "%" + filter.getUserId().toLowerCase() + "%"));
            }

            if (filter.getSupportLevel() != null) {
                predicates.add(cb.equal(root.get("supportLevel"), filter.getSupportLevel()));
            }

            if (filter.getMessageContent() != null && !filter.getMessageContent().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("message")), "%" + filter.getMessageContent().toLowerCase() + "%"));
            }

            if (filter.getEmail() != null && !filter.getEmail().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("email")), "%" + filter.getEmail().toLowerCase() + "%"));
            }

            if (filter.getEndpoint() != null && !filter.getEndpoint().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("endpoint")), "%" + filter.getEndpoint().toLowerCase() + "%"));
            }

            if (filter.getLogin() != null && !filter.getLogin().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("login")), "%" + filter.getLogin().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
