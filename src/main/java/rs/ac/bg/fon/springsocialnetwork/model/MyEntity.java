package rs.ac.bg.fon.springsocialnetwork.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.springsocialnetwork.repository.MyRepository;

import java.util.List;

/**
 * @author UrosVesic
 */
public interface MyEntity {

    public default List<MyRepository> returnChildRepositories(@Autowired ApplicationContext context){
        throw new UnsupportedOperationException();
    }
}
