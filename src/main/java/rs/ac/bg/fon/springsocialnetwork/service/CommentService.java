package rs.ac.bg.fon.springsocialnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.springsocialnetwork.dto.CommentDto;
import rs.ac.bg.fon.springsocialnetwork.mapper.CommentMapper;
import rs.ac.bg.fon.springsocialnetwork.model.Comment;
import rs.ac.bg.fon.springsocialnetwork.repository.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author UrosVesic
 */
@Service
@AllArgsConstructor
public class CommentService {
    private CommentRepository commentRepository;
    private CommentMapper commentMapper;

    public void comment(CommentDto commentDto){
        Comment comment = commentMapper.toEntity(commentDto);
        commentRepository.save(comment);
    }

    public List<CommentDto> getAllCommentsForPost(Long postId) {
        List<Comment> comments = commentRepository.findByPost_id(postId);
        return comments.stream().map((comment)->commentMapper.toDto(comment)).collect(Collectors.toList());
    }
}
