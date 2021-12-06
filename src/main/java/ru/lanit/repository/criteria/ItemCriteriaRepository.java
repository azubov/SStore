package ru.lanit.repository.criteria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.lanit.model.criteria.ItemSearchCriteria;
import ru.lanit.model.entity.Item;
import ru.lanit.model.criteria.ItemPage;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ItemCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    @Autowired
    public ItemCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Item> findAllWihFilters(ItemPage itemPage, ItemSearchCriteria itemSearchCriteria) {
        CriteriaQuery<Item> criteriaQuery = criteriaBuilder.createQuery(Item.class);
        Root<Item> itemRoot = criteriaQuery.from(Item.class);
        Predicate predicate = getPredicate(itemSearchCriteria, itemRoot);
        criteriaQuery.where(predicate);
        TypedQuery<Item> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult((itemPage.getPageNumber() - 1) * itemPage.getPageSize());
        typedQuery.setMaxResults(itemPage.getPageSize());
        Pageable pageable = getPageable(itemPage);
        long itemCount = getItemCount(predicate);
        setMaxPageNumber(itemPage, itemCount);
        return new PageImpl<>(typedQuery.getResultList(), pageable, itemCount);
    }

    private Predicate getPredicate(ItemSearchCriteria criteria, Root<Item> itemRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(criteria.getPriceLow())) {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(itemRoot.get("price"),
                            criteria.getPriceLow())
            );
        }
        if (Objects.nonNull(criteria.getPriceHigh())) {
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(itemRoot.get("price"),
                    criteria.getPriceHigh())
            );
        }
        if (Objects.nonNull(criteria.getColor())) {
            predicates.add(
                    criteriaBuilder.like(itemRoot.get("color"),
                            "%" + criteria.getColor() + "%")
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Pageable getPageable(ItemPage itemPage) {
        return PageRequest.of(itemPage.getPageNumber() - 1, itemPage.getPageSize());
    }

    private long getItemCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Item> countRoot = countQuery.from(Item.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private void setMaxPageNumber(ItemPage itemPage, long itemCount) {
        int result = (int) itemCount/itemPage.getPageSize();
        if (result > 0) {
            itemPage.setMaxPageNumber(result);
        }
    }
}
