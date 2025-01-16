// src/app/models/task.model.ts
import { Category } from './category.model';

export interface Task {
  taskId?: number;
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
}
