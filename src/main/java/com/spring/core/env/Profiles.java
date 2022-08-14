package com.spring.core.env;

import java.util.function.Predicate;

@FunctionalInterface
public interface Profiles {

    boolean matches(Predicate<String> activeProfiles);

    static Profiles of(String... profiles){
        return ProfilesParser.parse(profiles);
    }

}
