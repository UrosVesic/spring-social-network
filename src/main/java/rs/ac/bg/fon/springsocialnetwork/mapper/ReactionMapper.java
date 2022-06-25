package rs.ac.bg.fon.springsocialnetwork.mapper;

import lombok.AllArgsConstructor;
import rs.ac.bg.fon.springsocialnetwork.dto.ReactionDto;
import rs.ac.bg.fon.springsocialnetwork.exception.MyRuntimeException;
import rs.ac.bg.fon.springsocialnetwork.model.Reaction;
import rs.ac.bg.fon.springsocialnetwork.repository.PostRepository;
import rs.ac.bg.fon.springsocialnetwork.service.AuthService;
import rs.ac.bg.fon.springsocialnetwork.service.PostService;

/**
 * @author UrosVesic
 */
@AllArgsConstructor
public class ReactionMapper implements GenericMapper<ReactionDto, Reaction> {

    private PostRepository postRepository;
    private AuthService authService;

    @Override
    public Reaction toEntity(ReactionDto dto) {
        Reaction reaction = new Reaction();
        reaction.setPost(postRepository.findById(dto.getPostId()).orElseThrow(()->new MyRuntimeException("Post not found")));
        reaction.setReactionType(dto.getReactionType());
        reaction.setUser(authService.getCurrentUser());
        return reaction;
    }

    @Override
    public ReactionDto toDto(Reaction entity) {
        return null;
    }
}
