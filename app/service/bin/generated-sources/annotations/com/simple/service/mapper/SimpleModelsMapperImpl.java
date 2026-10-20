package com.simple.service.mapper;

import com.simple.models.dto.SimpleAddressDto;
import com.simple.models.dto.SimpleBigResponseDto;
import com.simple.models.dto.SimplePersonDto;
import com.simple.models.enums.Gender;
import com.simple.models.requests.SimpleAddressRequest;
import com.simple.models.requests.SimpleCreateRequest;
import com.simple.persistence.entity.SimpleAddressEntity;
import com.simple.persistence.entity.SimplePersonEntity;
import com.simple.persistence.entity.SimpleTripEntity;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-14T20:44:15+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.37.0.v20240206-1609, environment: Java 17.0.10 (Eclipse Adoptium)"
)
public class SimpleModelsMapperImpl implements SimpleModelsMapper {

    @Override
    public SimplePersonEntity convertToPersonEntity(SimpleCreateRequest createRequest, String addressId) {
        if ( createRequest == null && addressId == null ) {
            return null;
        }

        SimplePersonEntity.SimplePersonEntityBuilder simplePersonEntity = SimplePersonEntity.builder();

        if ( createRequest != null ) {
            if ( createRequest.getEmail() != null ) {
                simplePersonEntity.email( createRequest.getEmail() );
            }
            if ( createRequest.getFirstName() != null ) {
                simplePersonEntity.firstName( createRequest.getFirstName() );
            }
            if ( createRequest.getGender() != null ) {
                simplePersonEntity.gender( Enum.valueOf( Gender.class, createRequest.getGender() ) );
            }
            if ( createRequest.getLastName() != null ) {
                simplePersonEntity.lastName( createRequest.getLastName() );
            }
            if ( createRequest.getPassword() != null ) {
                simplePersonEntity.password( createRequest.getPassword() );
            }
        }
        if ( addressId != null ) {
            simplePersonEntity.addressId( addressId );
        }
        simplePersonEntity.tripIds( Set.of() );
        simplePersonEntity.createdTime( java.time.LocalDateTime.now() );

        return simplePersonEntity.build();
    }

    @Override
    public SimpleAddressEntity convertToAddressEntity(SimpleAddressRequest addressRequest) {
        if ( addressRequest == null ) {
            return null;
        }

        SimpleAddressEntity.SimpleAddressEntityBuilder simpleAddressEntity = SimpleAddressEntity.builder();

        if ( addressRequest.getCity() != null ) {
            simpleAddressEntity.city( addressRequest.getCity() );
        }
        if ( addressRequest.getCountry() != null ) {
            simpleAddressEntity.country( addressRequest.getCountry() );
        }
        if ( addressRequest.getIndex() != null ) {
            simpleAddressEntity.index( addressRequest.getIndex() );
        }
        if ( addressRequest.getName() != null ) {
            simpleAddressEntity.name( addressRequest.getName() );
        }

        return simpleAddressEntity.build();
    }

    @Override
    public SimpleAddressDto convertToAddressDto(SimpleAddressEntity addressEntity) {
        if ( addressEntity == null ) {
            return null;
        }

        String name = null;
        String city = null;
        String country = null;
        String index = null;

        if ( addressEntity.getName() != null ) {
            name = addressEntity.getName();
        }
        if ( addressEntity.getCity() != null ) {
            city = addressEntity.getCity();
        }
        if ( addressEntity.getCountry() != null ) {
            country = addressEntity.getCountry();
        }
        if ( addressEntity.getIndex() != null ) {
            index = addressEntity.getIndex();
        }

        SimpleAddressDto simpleAddressDto = new SimpleAddressDto( name, city, country, index );

        return simpleAddressDto;
    }

    @Override
    public SimplePersonDto convertToPersonDto(SimplePersonEntity personEntity) {
        if ( personEntity == null ) {
            return null;
        }

        String firstName = null;
        String lastName = null;
        String email = null;
        String gender = null;

        if ( personEntity.getFirstName() != null ) {
            firstName = personEntity.getFirstName();
        }
        if ( personEntity.getLastName() != null ) {
            lastName = personEntity.getLastName();
        }
        if ( personEntity.getEmail() != null ) {
            email = personEntity.getEmail();
        }
        if ( personEntity.getGender() != null ) {
            gender = personEntity.getGender().name();
        }

        SimplePersonDto simplePersonDto = new SimplePersonDto( firstName, lastName, email, gender );

        return simplePersonDto;
    }

    @Override
    public SimpleBigResponseDto convertToBigResponse(SimplePersonDto person, SimpleAddressDto address, LocalDateTime createdTime) {
        if ( person == null && address == null && createdTime == null ) {
            return null;
        }

        SimplePersonDto person1 = null;
        if ( person != null ) {
            person1 = person;
        }
        SimpleAddressDto address1 = null;
        if ( address != null ) {
            address1 = address;
        }

        String createdTime1 = createdTime.format(java.time.format.DateTimeFormatter.ISO_DATE_TIME);

        SimpleBigResponseDto simpleBigResponseDto = new SimpleBigResponseDto( person1, address1, createdTime1 );

        return simpleBigResponseDto;
    }

    @Override
    public Set<String> convertTripsToStrings(Set<SimpleTripEntity> trips) {
        if ( trips == null ) {
            return null;
        }

        Set<String> set = new LinkedHashSet<String>( Math.max( (int) ( trips.size() / .75f ) + 1, 16 ) );
        for ( SimpleTripEntity simpleTripEntity : trips ) {
            set.add( convertTripToString( simpleTripEntity ) );
        }

        return set;
    }

    @Override
    public String convertTripToString(SimpleTripEntity trip) {
        if ( trip == null ) {
            return null;
        }

        String string = new String();

        return string;
    }
}
