package rs.ac.bg.fon.springsocialnetwork.mapper;

import rs.ac.bg.fon.springsocialnetwork.dto.Dto;
import rs.ac.bg.fon.springsocialnetwork.model.MyEntity;

/**
 * @author UrosVesic
 */

public interface GenericMapper<D extends Dto, E extends MyEntity> {

    E toEntity(D dto);

    D toDto(E entity);
}
