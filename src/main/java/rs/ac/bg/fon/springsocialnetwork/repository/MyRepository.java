package rs.ac.bg.fon.springsocialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.springsocialnetwork.model.MyEntity;
import rs.ac.bg.fon.springsocialnetwork.model.Post;

/**
 * @author UrosVesic
 */
@Repository
public interface MyRepository{


    public default void deleteByParent(MyEntity parent){}


}
