// src/app/models/task.model.ts
import { Category } from './category.model';
import { TestCaseDto } from './test-case.dto';

export interface Task {
  id?: number;
  title: string;
  description: string;
  difficultyLevel: number;
  creationDate?: string;
  isPublic: boolean;
  status: string;
  maxExecutionTime?: number;
  maxMemoryUsage?: number;
  creatorUserId?: number;
  categories?: Category[];
  testCases?: TestCaseDto[];
}
