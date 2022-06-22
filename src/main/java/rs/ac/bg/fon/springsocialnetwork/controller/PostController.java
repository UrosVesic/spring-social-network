package rs.ac.bg.fon.springsocialnetwork.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.springsocialnetwork.dto.PostRequest;
import rs.ac.bg.fon.springsocialnetwork.dto.PostResponse;
import rs.ac.bg.fon.springsocialnetwork.model.User;
import rs.ac.bg.fon.springsocialnetwork.service.AuthService;
import rs.ac.bg.fon.springsocialnetwork.service.PostService;

import java.util.List;

/**
 * @author UrosVesic
 */
@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
public class PostController {

    private PostService postService;
    private AuthService authService;

    @GetMapping("/all")
    public ResponseEntity<List<PostResponse>> getAllPosts(){
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest){
        User currentUser = authService.getCurrentUser();
        PostResponse postResponse = postService.createPost(postRequest,currentUser);
        return new ResponseEntity(postResponse,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id){
        PostResponse postResponse = postService.getPost(id);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

}
