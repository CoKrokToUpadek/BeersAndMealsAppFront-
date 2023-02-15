package com.cokroktosmok.beersandmealsappfront.data.dto.user;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatedUserDto {
   private String firstName;
   private String lastName;
   private String address;
   private String email;
   private String login;
   private String password;
}
