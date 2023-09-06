package com.example.Plowithme.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrentUserDto {

	private Long id;

	private String email;

	private String name;
}
