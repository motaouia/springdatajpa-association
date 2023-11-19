package org.medmota.associations.repositories;

import java.util.List;

import org.medmota.associations.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByTutorialId(Long postId);

	@Transactional
	void deleteByTutorialId(long tutorialId);
}
