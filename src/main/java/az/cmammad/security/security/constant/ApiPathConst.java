package az.cmammad.security.security.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiPathConst {

    @Getter
    @AllArgsConstructor
    public enum PostApiPath {
        SIGN_UP("/register"),
        SIGN_IN("/auth/login");

        private final String path;
    }
}
