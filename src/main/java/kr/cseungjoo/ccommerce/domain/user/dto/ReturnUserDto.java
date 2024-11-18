package kr.cseungjoo.ccommerce.domain.user.dto;

import kr.cseungjoo.ccommerce.domain.user.User;
import lombok.*;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnUserDto {
    private String username;
    private String email;
    private String role;

    public ReturnUserDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole().toString();
    }
}
