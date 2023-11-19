package org.medmota.associations.controllers;

import java.util.List;

import org.medmota.associations.entities.Comment;
import org.medmota.associations.exceptions.ResourceNotFoundException;
import org.medmota.associations.repositories.CommentRepository;
import org.medmota.associations.repositories.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "{http://localhost:8089}")
@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private TutorialRepository tutorialRepository;

	@Autowired
	private CommentRepository commentRepository;

	@GetMapping("/tutorials/{tutorialId}/comments")
	public ResponseEntity<List<Comment>> getAllCommentsByTutorialId(
			@PathVariable(value = "tutorialId") Long tutorialId) {

		if (!tutorialRepository.existsById(tutorialId)) {
			throw new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId);
		}
		List<Comment> comments = commentRepository.findByTutorialId(tutorialId);
		return new ResponseEntity<>(comments, HttpStatus.OK);
	}

	@GetMapping("/comments/{id}")
	public ResponseEntity<Comment> getCommentById(@PathVariable("id") Long id) {

		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Comment with id = " + id));
		return new ResponseEntity<>(comment, HttpStatus.OK);
	}

	@PostMapping("/tutorials/{tutorialId}/comments")
	public ResponseEntity<Comment> createComment(@PathVariable("tutorialId") Long tutorialId,
			@RequestBody Comment comment) {
		if (!tutorialRepository.existsById(tutorialId)) {
			throw new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId);
		} else {
			comment.setTutorial(tutorialRepository.findById(tutorialId).get());
			Comment _comment = commentRepository.save(comment);
			return new ResponseEntity<>(_comment, HttpStatus.CREATED);
		}

	}

	@PutMapping("/comments/{id}")
	public ResponseEntity<Comment> updateComment(@PathVariable("id") long id, @RequestBody Comment commentRequest) {
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("CommentId " + id + "not found"));

		comment.setContent(commentRequest.getContent());

		return new ResponseEntity<>(commentRepository.save(comment), HttpStatus.OK);
	}

	@DeleteMapping("/comments/{id}")
	public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") long id) {
		commentRepository.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/tutorials/{tutorialId}/comments")
	public ResponseEntity<List<Comment>> deleteAllCommentsOfTutorial(
			@PathVariable(value = "tutorialId") Long tutorialId) {
		if (!tutorialRepository.existsById(tutorialId)) {
			throw new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId);
		}

		commentRepository.deleteByTutorialId(tutorialId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
