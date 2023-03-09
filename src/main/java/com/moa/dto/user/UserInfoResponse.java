package com.moa.dto.user;

import com.moa.domain.user.User;
import lombok.Getter;

@Getter
public class UserInfoResponse {
    private final String email;
    private final String password;
    private final String name;
    private final String nickname;
    private final double locationLatitude;
    private final double locationLongitude;
    private final int popularity;
    private final String details;

    public UserInfoResponse(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.locationLatitude = user.getLocationLatitude();
        this.locationLongitude = user.getLocationLongitude();
        this.popularity = user.getPopularity();
        this.details = user.getDetails();
    }
}