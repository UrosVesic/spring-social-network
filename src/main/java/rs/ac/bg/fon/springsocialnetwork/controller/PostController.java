package rs.ac.bg.fon.springsocialnetwork.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.springsocialnetwork.dto.PostRequest;
import rs.ac.bg.fon.springsocialnetwork.dto.PostResponse;
import rs.ac.bg.fon.springsocialnetwork.dto.ReportedPostDto;
import rs.ac.bg.fon.springsocialnetwork.model.User;
import rs.ac.bg.fon.springsocialnetwork.service.AuthService;
import rs.ac.bg.fon.springsocialnetwork.service.PostService;

import java.util.List;
import java.util.Set;

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
        List<PostResponse> allPosts = postService.getAllPosts();
        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

    @GetMapping("/authAll")
    public ResponseEntity<List<PostResponse>> getAllPostsForFollowingUsers(){
        List<PostResponse> allPosts = postService.getAllPostsForFollowingUsers();
        return new ResponseEntity<>(allPosts, HttpStatus.OK);
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

    @GetMapping("/user/{username}")
    public ResponseEntity<List<PostResponse>> getAllPostsForUser(@PathVariable String username){
        List<PostResponse> postResponses = postService.getAllPostsForUser(username);
        return new ResponseEntity<>(postResponses,HttpStatus.OK);
    }

    @PatchMapping("/soft-delete/{id}")
    public ResponseEntity softDeletePost(@PathVariable Long id){
        postService.softDeletePost(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity updatePost(@PathVariable Long id,@RequestBody PostRequest postRequest){
        postService.updatePost(id,postRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/secured/reported")
    public ResponseEntity<Set<ReportedPostDto>> getAllUnsolvedReportedPosts(){
        Set<ReportedPostDto> reportedPosts= postService.getAllUnsolvedReportedPosts();
        return new ResponseEntity<>(reportedPosts,HttpStatus.OK);
    }

    @GetMapping("/secured/reported-solved")
    public ResponseEntity<Set<ReportedPostDto>> getAllSolvedReportedPosts(){
        Set<ReportedPostDto> reportedPosts= postService.getAllSolvedReportedPosts();
        return new ResponseEntity<>(reportedPosts,HttpStatus.OK);
    }

}
