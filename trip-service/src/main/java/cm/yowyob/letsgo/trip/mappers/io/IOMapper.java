package cm.yowyob.letsgo.trip.mappers.io;



import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class IOMapper<O, E> {

    public abstract O toObject(E dto);
    public abstract E toDTO(O object);


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
                .map(this::toDTO)
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
                .map(this::toDTO)
                .collect(Collectors.toSet());
    }

}
