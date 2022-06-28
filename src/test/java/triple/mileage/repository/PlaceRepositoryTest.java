package triple.mileage.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import triple.mileage.domain.Place;

import java.util.List;

//@SpringBootTest
class PlaceRepositoryTest {

    @Autowired
    PlaceRepository placeRepository;

//    @Test
    @Transactional  // transaction 안에서는 proxy 생성 가능.
    public void testFind() {
        List<Place> all = placeRepository.findAll();
        for (Place place : all) {
//            System.out.println("place = " + place.getReview());
        }
    }

//    @Test
    @Transactional
    public void test() {

        // given
//        Place place1 = new Place("피라미드");
//        Place place2 = new Place("순례길");
//        Place place3 = new Place("성 패트릭 성당");
//        Place place4 = new Place("타이완");
//        Place place5 = new Place("가오슝");
//        placeRepository.save(place1);
//        placeRepository.save(place2);
//        placeRepository.save(place3);
//        placeRepository.save(place4);
//        placeRepository.save(place5);

//         when
        List<Place> all = placeRepository.findAll();
        for (Place place : all) {
            System.out.println(place.getId());
            System.out.println(place.getName());
        }

//        0b31f723-8ce9-4d51-9715-83aa0032a79a
//                피라미드
//        3fec5466-630f-4313-bbea-0fff58819836
//                타이완
//        5b9c0510-5c8f-4817-b387-9df785f13594
//                가오슝
//        a992488a-a4f5-4d85-afa0-7a93c7a65ecc
//        성 패트릭 성당
//        dddf5fe9-6fe4-46d3-8ec7-b7b85851370e
//        순례길
    }

}