package com.example.Plowithme.specification;

import com.example.Plowithme.entity.BoardEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class BoardSpecifications {

    public static Specification<BoardEntity> withCategory(List<Integer> categories) {
        return ((root, query, criteriaBuilder) ->
                root.get("category").in(categories));
    }
}
