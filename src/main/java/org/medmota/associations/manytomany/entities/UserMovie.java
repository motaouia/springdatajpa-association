package org.medmota.associations.manytomany.entities;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "users_movies")
public class UserMovie {

	@EmbeddedId
	private UserMovieId id = new UserMovieId();

	@ManyToOne
	@MapsId("userId")
	private User user;

	@ManyToOne
	@MapsId("movieId")
	private Movie movie;

	@Column(nullable = false)
	private int rate;

	@Lob
	private String review;

	@CreationTimestamp
	@Column(name = "added_at", nullable = false)
	private Date addedAt;

	public UserMovie() {
	}

	public UserMovie(UserMovieId id, int rate, String review) {
		this.id = id;
		this.rate = rate;
		this.review = review;
	}

	public UserMovieId getId() {
		return id;
	}

	public void setId(UserMovieId id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public Date getAddedAt() {
		return addedAt;
	}

	public void setAddedAt(Date addedAt) {
		this.addedAt = addedAt;
	}

	@Embeddable
	public static class UserMovieId implements Serializable {

		private static final long serialVersionUID = 1L;

		private Integer userId;
		private Integer movieId;

		public UserMovieId() {
		}

		public UserMovieId(Integer userId, Integer movieId) {
			super();
			this.userId = userId;
			this.movieId = movieId;
		}

		public Integer getUserId() {
			return userId;
		}

		public Integer getMovieId() {
			return movieId;
		}

		public void setUserId(Integer userId) {
			this.userId = userId;
		}

		public void setMovieId(Integer movieId) {
			this.movieId = movieId;
		}
	}
}
