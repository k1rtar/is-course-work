package com.algoforge.common.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TestCase {
    private String inputData;
    private String expectedOutput;
}
