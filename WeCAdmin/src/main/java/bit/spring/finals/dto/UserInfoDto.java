package bit.spring.finals.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDto {
  private String email;
  private String password;

  private String auth;
}
