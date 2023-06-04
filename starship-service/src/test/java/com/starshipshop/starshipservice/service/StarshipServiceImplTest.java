package com.starshipshop.starshipservice.service;

import com.starshipshop.starshipservice.adapter.StarshipAdapter;
import com.starshipshop.starshipservice.domain.Starship;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class StarshipServiceImplTest {

    @Test
    void getStarshipByIdIn_withANullList_throwsError() {
        StarshipAdapter starshipAdapter = mock(StarshipAdapter.class);
        when(starshipAdapter.getStarshipByIdIn(null))
                .thenThrow(NullPointerException.class);
        StarshipService instance = new StarshipServiceImpl(starshipAdapter);

        assertThrows(NullPointerException.class, () -> instance.getStarshipByIdIn(null));
    }

    @Test
    void getStarshipByIdIn_withAnEmptyList_givesAnEmptyList() {
        StarshipAdapter starshipAdapter = mock(StarshipAdapter.class);
        when(starshipAdapter.getStarshipByIdIn(new ArrayList<>()))
                .thenReturn(new ArrayList<>());
        StarshipService instance = new StarshipServiceImpl(starshipAdapter);

        List<Starship> result = instance.getStarshipByIdIn(new ArrayList<>());

        assertThat(result).isEmpty();
    }

    @Test
    void getStarshipByIdIn_withAValidList_givesAListOfStarship() {
        StarshipAdapter starshipAdapter = mock(StarshipAdapter.class);
        Starship starship1 = Starship.builder().id(1L).name("starship_name1").build();
        Starship starship2 = Starship.builder().id(2L).name("starship_name2").build();
        when(starshipAdapter.getStarshipByIdIn(Arrays.asList(1L, 2L)))
                .thenReturn(Arrays.asList(starship1, starship2));
        StarshipService instance = new StarshipServiceImpl(starshipAdapter);

        List<Starship> result = instance.getStarshipByIdIn(Arrays.asList(1L, 2L));

        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(1).getId()).isEqualTo(2L);
    }
}
