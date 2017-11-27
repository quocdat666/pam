package repository;

import models.AuthorisedUser;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class AuthorisedRepository extends CurdRepository<AuthorisedUser, Long> {


    public AuthorisedRepository() {
        super(AuthorisedUser.class);
    }
}
