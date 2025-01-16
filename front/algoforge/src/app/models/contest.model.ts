export interface Contest {
  contestId?: number;
  title: string;
  description: string;
  startTime: string;
  endTime: string;
  isPublic: boolean;
  creatorUserId?: number;
}
