package com.cokroktosmok.beersandmealsappfront.data.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreatedUserDto {
   @JsonProperty("firstName")
   private String firstName;
   @JsonProperty("lastName")
   private String lastName;
   @JsonProperty("address")
   private String address;
   @JsonProperty("email")
   private String email;
   @JsonProperty("login")
   private String login;
   @JsonProperty("password")
   private String password;
}
