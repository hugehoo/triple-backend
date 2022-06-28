package triple.mileage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import triple.mileage.domain.Place;

import java.util.UUID;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    Place findPlaceById(UUID id);
    Place findPlace2ById(String id);

}
