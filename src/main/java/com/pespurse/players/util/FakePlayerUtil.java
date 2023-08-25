package com.pespurse.players.util;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Random;

@Component
public class FakePlayerUtil {
    private final Faker FAKER = new Faker(new Locale("en-IN"));

    public String getFakePlayerName() {
        return FAKER.name().fullName();
    }

    public String getFakeNationality() {
        return FAKER.country().name();
    }

    public String getFakePlayerClub() { return FAKER.country().capital(); }

    public Integer getFakePlayerRating() {
        return new Random().nextInt() * 70 + (100-70);
    }

    public Integer getFakePlayerPosition() {
        return new Random().nextInt(13-1+1) + 1;
    }

    public Integer getFakePlayerCardType() {
        return new Random().nextInt(6-1+1) + 1;
    }

    public String getFakePlayerTag() {
        return FAKER.bothify("v1-??##");
    }

    public String getFakePlayerVersion() {
        return "v1";
    }

    public String getFakeAvatar() { return FAKER.avatar().image(); }
}
