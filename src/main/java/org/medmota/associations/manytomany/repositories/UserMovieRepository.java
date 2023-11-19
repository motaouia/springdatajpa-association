package org.medmota.associations.manytomany.repositories;

import org.medmota.associations.manytomany.entities.UserMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMovieRepository extends JpaRepository<UserMovie, UserMovie.UserMovieId>{

}
