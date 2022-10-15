package ar.edu.um.isa.bookapp.repository;

import ar.edu.um.isa.bookapp.domain.Profile;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ProfileRepositoryWithBagRelationshipsImpl implements ProfileRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Profile> fetchBagRelationships(Optional<Profile> profile) {
        return profile.map(this::fetchFolowers);
    }

    @Override
    public Page<Profile> fetchBagRelationships(Page<Profile> profiles) {
        return new PageImpl<>(fetchBagRelationships(profiles.getContent()), profiles.getPageable(), profiles.getTotalElements());
    }

    @Override
    public List<Profile> fetchBagRelationships(List<Profile> profiles) {
        return Optional.of(profiles).map(this::fetchFolowers).orElse(Collections.emptyList());
    }

    Profile fetchFolowers(Profile result) {
        return entityManager
            .createQuery("select profile from Profile profile left join fetch profile.folowers where profile is :profile", Profile.class)
            .setParameter("profile", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Profile> fetchFolowers(List<Profile> profiles) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, profiles.size()).forEach(index -> order.put(profiles.get(index).getId(), index));
        List<Profile> result = entityManager
            .createQuery(
                "select distinct profile from Profile profile left join fetch profile.folowers where profile in :profiles",
                Profile.class
            )
            .setParameter("profiles", profiles)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
