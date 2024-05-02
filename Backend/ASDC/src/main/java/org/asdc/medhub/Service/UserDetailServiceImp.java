package org.asdc.medhub.Service;

import org.asdc.medhub.Repository.UserRepository;
import org.asdc.medhub.Utility.Model.DatabaseModels.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation of UserDetailsService from spring boot security package
 */
@Service
public class UserDetailServiceImp implements UserDetailsService {

    /**
     * UserRepository instance
     */
    private final UserRepository userRepository;

    /**
     * Parameterized constructor
     * @param userRepository - bean
     */
    @Autowired
    public UserDetailServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //region PUBLIC METHODS
    /**
     * @param username - useraname of user
     * @return UserDetails
     * @throws UsernameNotFoundException if user not found in database
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userRepository.findUserByUsername(username);
        if(null!=user){
            return user;
        }else{
            throw new UsernameNotFoundException("User: "+username+" not found.");
        }
    }
    //endregion
}
