package kr.cseungjoo.ccommerce.domain.user.dto;

import kr.cseungjoo.ccommerce.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {
    private String username;

    public UpdateUserDto(User user) {
        this.username = user.getUsername();
    }
}
