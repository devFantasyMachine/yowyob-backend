package cm.yowyob.letsgo.driver.mappers;



import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public abstract class IOMapper<O, E> {

    public abstract O toDomainObject(E entity);

    public abstract E toDTO(O object);


    public List<O> toDomainObjects(Collection<E> entities){

        if (entities == null || entities.isEmpty())
            return List.of();

        return entities.stream()
                .map(this::toDomainObject)
                .collect(Collectors.toList());
    }

    public List<E> toDTOs(Collection<O> objects){

        if (objects == null || objects.isEmpty())
            return List.of();

        return objects.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    public Set<O> toDomainObjectSet(Collection<E> entities){

        if (entities == null || entities.isEmpty())
            return Set.of();

        return entities.stream()
                .map(this::toDomainObject)
                .collect(Collectors.toSet());
    }

    public Set<E> toDTOsSet(Collection<O> objects){

        if (objects == null || objects.isEmpty())
            return Set.of();

        return objects.stream()
                .map(this::toDTO)
                .collect(Collectors.toSet());
    }


}
