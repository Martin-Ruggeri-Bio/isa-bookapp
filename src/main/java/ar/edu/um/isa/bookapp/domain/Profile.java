package ar.edu.um.isa.bookapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Profile.
 */
@Entity
@Table(name = "profile")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToMany
    @JoinTable(
        name = "rel_profile__folowers",
        joinColumns = @JoinColumn(name = "profile_id"),
        inverseJoinColumns = @JoinColumn(name = "folowers_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "folowers", "posts", "likes", "folowings" }, allowSetters = true)
    private Set<Profile> folowers = new HashSet<>();

    @OneToMany(mappedBy = "author")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "author", "tags", "likes" }, allowSetters = true)
    private Set<Post> posts = new HashSet<>();

    @ManyToMany(mappedBy = "likes")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "author", "tags", "likes" }, allowSetters = true)
    private Set<Post> likes = new HashSet<>();

    @ManyToMany(mappedBy = "folowers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "folowers", "posts", "likes", "folowings" }, allowSetters = true)
    private Set<Profile> folowings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Profile id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Profile name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Profile user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Profile> getFolowers() {
        return this.folowers;
    }

    public void setFolowers(Set<Profile> profiles) {
        this.folowers = profiles;
    }

    public Profile folowers(Set<Profile> profiles) {
        this.setFolowers(profiles);
        return this;
    }

    public Profile addFolowers(Profile profile) {
        this.folowers.add(profile);
        profile.getFolowings().add(this);
        return this;
    }

    public Profile removeFolowers(Profile profile) {
        this.folowers.remove(profile);
        profile.getFolowings().remove(this);
        return this;
    }

    public Set<Post> getPosts() {
        return this.posts;
    }

    public void setPosts(Set<Post> posts) {
        if (this.posts != null) {
            this.posts.forEach(i -> i.setAuthor(null));
        }
        if (posts != null) {
            posts.forEach(i -> i.setAuthor(this));
        }
        this.posts = posts;
    }

    public Profile posts(Set<Post> posts) {
        this.setPosts(posts);
        return this;
    }

    public Profile addPost(Post post) {
        this.posts.add(post);
        post.setAuthor(this);
        return this;
    }

    public Profile removePost(Post post) {
        this.posts.remove(post);
        post.setAuthor(null);
        return this;
    }

    public Set<Post> getLikes() {
        return this.likes;
    }

    public void setLikes(Set<Post> posts) {
        if (this.likes != null) {
            this.likes.forEach(i -> i.removeLike(this));
        }
        if (posts != null) {
            posts.forEach(i -> i.addLike(this));
        }
        this.likes = posts;
    }

    public Profile likes(Set<Post> posts) {
        this.setLikes(posts);
        return this;
    }

    public Profile addLike(Post post) {
        this.likes.add(post);
        post.getLikes().add(this);
        return this;
    }

    public Profile removeLike(Post post) {
        this.likes.remove(post);
        post.getLikes().remove(this);
        return this;
    }

    public Set<Profile> getFolowings() {
        return this.folowings;
    }

    public void setFolowings(Set<Profile> profiles) {
        if (this.folowings != null) {
            this.folowings.forEach(i -> i.removeFolowers(this));
        }
        if (profiles != null) {
            profiles.forEach(i -> i.addFolowers(this));
        }
        this.folowings = profiles;
    }

    public Profile folowings(Set<Profile> profiles) {
        this.setFolowings(profiles);
        return this;
    }

    public Profile addFolowing(Profile profile) {
        this.folowings.add(profile);
        profile.getFolowers().add(this);
        return this;
    }

    public Profile removeFolowing(Profile profile) {
        this.folowings.remove(profile);
        profile.getFolowers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profile)) {
            return false;
        }
        return id != null && id.equals(((Profile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Profile{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
