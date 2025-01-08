package com.algoforge.common.auth;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AlgoUserDto {
    
    private Long id;

    private String email;

    private String username;

    private String bio;

    private byte[] profilePhoto;

    private LocalDateTime creationDate;

    private Integer rating = 0;

    private boolean isBlocked = false;

    private boolean isDeleted = false;

    private Set<String> roles = new HashSet<>();

}
