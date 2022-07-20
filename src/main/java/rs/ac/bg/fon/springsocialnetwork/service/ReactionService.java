package rs.ac.bg.fon.springsocialnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.springsocialnetwork.dto.ReactionDto;
import rs.ac.bg.fon.springsocialnetwork.mapper.NotificationBuilder;
import rs.ac.bg.fon.springsocialnetwork.mapper.NotificationMapper;
import rs.ac.bg.fon.springsocialnetwork.mapper.ReactionMapper;
import rs.ac.bg.fon.springsocialnetwork.model.Notification;
import rs.ac.bg.fon.springsocialnetwork.model.Reaction;
import rs.ac.bg.fon.springsocialnetwork.repository.NotificationRepository;
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
    private NotificationService notificationService;
    private NotificationBuilder notificationBuilder;

    @Transactional
    public void react(ReactionDto reactionDto){
        Optional<Reaction> reactOpt=reactionRepository.findByPost_idAndUser(reactionDto.getPostId(),authService.getCurrentUser());
        Reaction reactionEntity = reactionMapper.toEntity(reactionDto);
        if(!reactOpt.isPresent()){
            reactionRepository.save(reactionEntity);
            Notification not = notificationBuilder.createNotificationForReaction(reactionEntity);
            notificationService.save(not);
            return;
        }
        Reaction reaction = reactOpt.get();
        if(reaction.getReactionType()==reactionDto.getReactionType()){
            reactionRepository.delete(reaction);
        }else{
            reactionRepository.delete(reaction);
            reactionRepository.save(reactionEntity);
            Notification not = notificationBuilder.createNotificationForReaction(reactionEntity);
            notificationService.save(not);
        }

    }
}
