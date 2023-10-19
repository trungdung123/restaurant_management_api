package com.demo.restaurant_management.web.dto.request;

import com.demo.restaurant_management.model.MenuItem;
import com.demo.restaurant_management.web.dto.request.utils.PagingRequest;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class MenuItemCriteria {
    private PagingRequest pagingRequest = new PagingRequest();
    private String keyword;

    public Specification<MenuItem> toSpecification() {
        return (root, query, builder) -> {
            List<Predicate> predicate = new ArrayList<>();

            if (Objects.nonNull(keyword) && StringUtils.isNotBlank(keyword)) {
                var predicate1 = builder.like(root.get("name"), "%" + keyword + "%");
                var predicate2 = builder.like(root.get("description"), "%" + keyword + "%");
                var predicate3 = builder.like(root.get("category").get("name"), "%" + keyword + "%");
                predicate.add(builder.or(predicate1, predicate2, predicate3));
            }

            return builder.and(predicate.toArray(Predicate[]::new));
        };
    }
}
