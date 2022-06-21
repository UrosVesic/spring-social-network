package rs.ac.bg.fon.springsocialnetwork.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import lombok.AllArgsConstructor;
import rs.ac.bg.fon.springsocialnetwork.dto.PostResponse;
import rs.ac.bg.fon.springsocialnetwork.model.Post;
import rs.ac.bg.fon.springsocialnetwork.model.ReactionType;
import rs.ac.bg.fon.springsocialnetwork.repository.CommentRepository;
import rs.ac.bg.fon.springsocialnetwork.repository.PostRepository;
import rs.ac.bg.fon.springsocialnetwork.repository.ReactionRepository;
@AllArgsConstructor
public class PostMapper implements GenericMapper<PostResponse, Post> {

    private CommentRepository commentRepository;
    private ReactionRepository reactionRepository;


    @Override
    public Post toEntity(PostResponse dto) {
        return null;
    }

    @Override
    public PostResponse toDto(Post post) {
        PostResponse postResponse = new PostResponse();
        postResponse.setId(post.getId());
        postResponse.setPostName(post.getPostName());
        postResponse.setContent(post.getContent());
        postResponse.setTopicName(post.getTopic().getName());
        postResponse.setUserName(post.getUser().getUsername());
        postResponse.setCommentCount(getCommentCount(post));
        postResponse.setDislikes(getLikesCount(post));
        postResponse.setLikes(getDislikesCount(post));
        //TimeAgo.using(post.getCreatedDate().toEpochMilli())
        postResponse.setDuration("0");
        postResponse.setLikeCount(postResponse.getLikes()-postResponse.getDislikes());
        return postResponse;
    }

    private Integer getDislikesCount(Post post) {
        return reactionRepository.findByPostAndReactionType(post, ReactionType.DISLIKE).size();
    }

    private Integer getLikesCount(Post post) {
        return reactionRepository.findByPostAndReactionType(post, ReactionType.LIKE).size();
    }

    private Integer getCommentCount(Post post) {
       return commentRepository.findAllByPost(post).size();
    }
}
