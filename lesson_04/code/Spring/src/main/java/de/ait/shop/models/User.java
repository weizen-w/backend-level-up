package de.ait.shop.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 8/23/2023
 * Introduction
 *
 * @author Marsel Sidikov (AIT TR)
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User {

    private Long id;
    private String email;
    private String password;
}
