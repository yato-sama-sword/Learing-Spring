package edu.neu.spring.test.entity;

import edu.neu.spring.test.entity.Pet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author yato
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class User {
    private int age;
    private String name;
    private Pet pet;
}
