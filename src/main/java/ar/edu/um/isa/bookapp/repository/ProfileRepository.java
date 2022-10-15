package ar.edu.um.isa.bookapp.repository;

import ar.edu.um.isa.bookapp.domain.Profile;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Profile entity.
 *
 * When extending this class, extend ProfileRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface ProfileRepository extends ProfileRepositoryWithBagRelationships, JpaRepository<Profile, Long> {
    default Optional<Profile> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Profile> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Profile> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct profile from Profile profile left join fetch profile.user",
        countQuery = "select count(distinct profile) from Profile profile"
    )
    Page<Profile> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct profile from Profile profile left join fetch profile.user")
    List<Profile> findAllWithToOneRelationships();

    @Query("select profile from Profile profile left join fetch profile.user where profile.id =:id")
    Optional<Profile> findOneWithToOneRelationships(@Param("id") Long id);
}
