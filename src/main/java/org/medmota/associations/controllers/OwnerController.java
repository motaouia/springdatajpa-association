package org.medmota.associations.controllers;

import java.util.ArrayList;
import java.util.List;

import org.medmota.associations.entities.Blog;
import org.medmota.associations.entities.Owner;
import org.medmota.associations.exceptions.ResourceNotFoundException;
import org.medmota.associations.repositories.BlogRepository;
import org.medmota.associations.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class OwnerController {
	
	@Autowired
	private OwnerRepository ownerRepository;
	@Autowired
	private BlogRepository blogRepository;
	
	@PostMapping("/owners")
	public ResponseEntity<HttpStatus> saveOwner(@RequestBody Owner ownerRequest) {
		Owner owner = new Owner(ownerRequest.getName(), ownerRequest.getEmail());
		
		List<Blog> blogs = new ArrayList<>();
        for (Blog blogIn : owner.getBlogs()) {
            Blog blog = new Blog(blogIn.getTitle(), blogIn.getCategory(), blogIn.getContent());
            blog.setOwner(owner);
            blogs.add(blog);
        }
        
        owner.setBlogs(blogs);
        ownerRepository.save(owner);
        
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	public ResponseEntity<HttpStatus> saveBlog(@RequestBody Blog blogRequest) {
		blogRepository.save(blogRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/owners/{id}")
	public ResponseEntity<Owner> getOwnerById(@PathVariable("id") Long id){
		Owner owner = ownerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found owner with id = " + id));

		return new ResponseEntity<>(owner, HttpStatus.OK);
	}
	
	@GetMapping("/blogs/{id}")
	public ResponseEntity<Blog> getBlogById(@PathVariable("id") Long id){
		Blog blog = blogRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found blog with id = " + id));

		return new ResponseEntity<>(blog, HttpStatus.OK);
	}

}
 