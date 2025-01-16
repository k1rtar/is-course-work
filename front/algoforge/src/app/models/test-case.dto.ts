// src/app/core/models/test-case.dto.ts

export interface TestCaseDto {
    testCaseId: number;
    inputData: string;
    expectedOutputData: string;
    isSample: boolean;
    id: number;
  }