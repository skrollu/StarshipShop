package com.starshipshop.starshipservice.adapter;

import com.starshipshop.starshipservice.domain.Starship;
import com.starshipshop.starshipservice.repository.StarshipRepository;
import com.starshipshop.starshipservice.repository.jpa.StarshipJpa;
import com.starshipshop.starshipservice.repository.mapper.StarshipMapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class StarshipAdapterImplTest {

    @Test
    void getStarshipByIdIn_withNullList_throwsError() {
        StarshipRepository starshipRepository = mock(StarshipRepository.class);
        when(starshipRepository.findByIdIn(null)).thenThrow(NullPointerException.class);
        StarshipMapper starshipMapper = mock(StarshipMapper.class);
        StarshipAdapterImpl instance = new StarshipAdapterImpl(starshipRepository, starshipMapper);

        NullPointerException result = assertThrows(NullPointerException.class, () -> instance.getStarshipByIdIn(null));
    }

    @Test
    void getStarshipByIdIn_withAnEmptyList_givesAnEmptyList() {
        StarshipRepository starshipRepository = mock(StarshipRepository.class);
        when(starshipRepository.findByIdIn(new ArrayList<>())).thenReturn(new ArrayList<StarshipJpa>());
        StarshipMapper starshipMapper = mock(StarshipMapper.class);
        StarshipAdapterImpl instance = new StarshipAdapterImpl(starshipRepository, starshipMapper);

        List<Starship> result = instance.getStarshipByIdIn(new ArrayList<>());

        assertThat(result).isNotNull();
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void getStarshipByIdIn_withAValidList_givesAListOfStarships() {
        StarshipRepository starshipRepository = mock(StarshipRepository.class);
        StarshipJpa starshipJpa1 = StarshipJpa.builder().id(1L).name("starship_name1").build();
        StarshipJpa starshipJpa2 = StarshipJpa.builder().id(2L).name("starship_name2").build();
        when(starshipRepository.findByIdIn(Arrays.asList(1L, 2L))).thenReturn(Arrays.asList(starshipJpa1, starshipJpa2));
        StarshipMapper starshipMapper = mock(StarshipMapper.class);
        when(starshipMapper.mapToStarship(starshipJpa1)).thenReturn(Starship.builder().id(1L).name("starship_name1").build());
        when(starshipMapper.mapToStarship(starshipJpa2)).thenReturn(Starship.builder().id(2L).name("starship_name2").build());
        StarshipAdapterImpl instance = new StarshipAdapterImpl(starshipRepository, starshipMapper);

        List<Starship> result = instance.getStarshipByIdIn(Arrays.asList(1L, 2L));

        assertThat(result).isNotNull();
        assertThat(result.isEmpty()).isFalse();
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(1).getId()).isEqualTo(2L);
    }

    @Test
    void getStarshipByIdIn_withAListOfMultipleTimesTheSameId_givesASetOfStarships() {
        StarshipRepository starshipRepository = mock(StarshipRepository.class);
        StarshipJpa starshipJpa1 = StarshipJpa.builder().id(1L).name("starship_name1").build();
        when(starshipRepository.findByIdIn(Arrays.asList(1L, 1L))).thenReturn(Arrays.asList(starshipJpa1));
        StarshipMapper starshipMapper = mock(StarshipMapper.class);
        when(starshipMapper.mapToStarship(starshipJpa1)).thenReturn(Starship.builder().id(1L).name("starship_name1").build());
        StarshipAdapterImpl instance = new StarshipAdapterImpl(starshipRepository, starshipMapper);

        List<Starship> result = instance.getStarshipByIdIn(Arrays.asList(1L, 1L));

        assertThat(result).isNotNull();
        assertThat(result.isEmpty()).isFalse();
        assertThat(result.size()).isOne();
        assertThat(result.get(0).getId()).isEqualTo(1L);
    }
}
