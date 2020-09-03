package de.bermuda.hero.backend;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface HeroMapper {
    HeroMapper INSTANCE = Mappers.getMapper(HeroMapper.class);

    Hero map(de.bermuda.hero.model.Hero hero);

    de.bermuda.hero.model.Hero reverseMap(Hero hero);
}
