package az.cmammad.security.service;

import az.cmammad.security.dto.UserRegisterRequestDto;

public interface UserService {

    String signUp(UserRegisterRequestDto userRegister);
}
