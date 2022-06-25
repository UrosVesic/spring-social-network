package rs.ac.bg.fon.springsocialnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.springsocialnetwork.dto.ReactionDto;
import rs.ac.bg.fon.springsocialnetwork.mapper.ReactionMapper;
import rs.ac.bg.fon.springsocialnetwork.model.Reaction;
import rs.ac.bg.fon.springsocialnetwork.repository.ReactionRepository;

import java.util.Optional;

/**
 * @author UrosVesic
 */
@Service
@AllArgsConstructor
public class ReactionService {

    private ReactionRepository reactionRepository;
    private ReactionMapper reactionMapper;
    private AuthService authService;

    public void react(ReactionDto reactionDto){
        Optional<Reaction> reactOpt=reactionRepository.findByPost_idAndUser(reactionDto.getPostId(),authService.getCurrentUser());
        if(!reactOpt.isPresent()){
            reactionRepository.save(reactionMapper.toEntity(reactionDto));
            return;
        }
        Reaction reaction = reactOpt.get();
        if(reaction.getReactionType()==reactionDto.getReactionType()){
            reactionRepository.delete(reaction);
        }else{
            reactionRepository.delete(reaction);
            reactionRepository.save(reactionMapper.toEntity(reactionDto));
        }

    }
}
