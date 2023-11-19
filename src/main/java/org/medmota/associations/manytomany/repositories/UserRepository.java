package org.medmota.associations.manytomany.repositories;

import org.medmota.associations.manytomany.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

}
