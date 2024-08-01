package enigma.to_do_list.security;

import enigma.to_do_list.model.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UserSecurity {
    public boolean isUser(Authentication authentication, int userId) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof UserEntity)) {
            return false;
        }

        UserEntity user = (UserEntity) principal;
//        return user.getId() == userId;
        return user.getId() .equals(userId) ;
    }
}
