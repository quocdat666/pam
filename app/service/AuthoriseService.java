package service;

import io.ebean.PagedList;
import models.filter.DataPage;
import models.AuthorisedUser;
import models.filter.AuthorisedUserFilter;
import repository.AuthorisedRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class AuthoriseService {

    private AuthorisedRepository authorisedRepository;

    @Inject
    public AuthoriseService(AuthorisedRepository authorisedRepository) {
        this.authorisedRepository = authorisedRepository;
    }

    public List<AuthorisedUser> list() {
        return authorisedRepository.findAll();
    }

    public PagedList<AuthorisedUser> filter(AuthorisedUserFilter userFilter) {
        try {
            return authorisedRepository.filter(userFilter);
        } catch (Exception e) {
            e.printStackTrace();
            //catch ex here
        }
        return null;
    }
}
