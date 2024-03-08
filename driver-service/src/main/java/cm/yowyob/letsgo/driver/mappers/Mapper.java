package cm.yowyob.letsgo.driver.mappers;



import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Mapper<O, E> {

    public abstract O toObject(E entity);
    public abstract E toEntity(O object);


    public List<O> toObjects(Collection<E> entities){

        if (entities == null || entities.isEmpty())
            return List.of();

        return entities.stream()
                .map(this::toObject)
                .collect(Collectors.toList());
    }

    public List<E> toEntities(Collection<O> objects){

        if (objects == null || objects.isEmpty())
            return List.of();

        return objects.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }


    public Set<O> toObjectSet(Collection<E> entities){

        if (entities == null || entities.isEmpty())
            return Set.of();

        return entities.stream()
                .map(this::toObject)
                .collect(Collectors.toSet());
    }

    public Set<E> toEntitySet(Collection<O> objects){

        if (objects == null || objects.isEmpty())
            return Set.of();

        return objects.stream()
                .map(this::toEntity)
                .collect(Collectors.toSet());
    }

}
