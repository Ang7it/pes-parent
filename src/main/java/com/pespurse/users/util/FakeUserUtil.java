package com.pespurse.users.util;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Locale;
import java.util.Random;

@Component
public class FakeUserUtil {

    private final Faker FAKER = new Faker(new Locale("en-IN"));

    public String getFakeUsername() {
        return FAKER.name().fullName();
    }

    public String getFakeEmailId() {
        return FAKER.bothify("????##@gmail.com");
    }

    public String getFakePhoneNumber() {
        return FAKER.phoneNumber().phoneNumber();
    }

    public LocalDate getFakeDob() {
        return LocalDate.of(Year.now().getValue(), Month.APRIL.getValue(), 3);
    }

    public Double fetFakeUserRating() {
        return new Random().nextDouble() * 1 + (4-1);
    }

    public Integer getFakeUserType() {
        return new Random().nextInt(6-1+1) + 1;
    }

    public String getFakeCountry() {
        return FAKER.country().name();
    }

    public String getFakeAvatar() {
        return FAKER.avatar().image();
    }

    public String getFakeGameId() {
        return FAKER.bothify("####-####").replaceAll("-", "0");
    }

}
