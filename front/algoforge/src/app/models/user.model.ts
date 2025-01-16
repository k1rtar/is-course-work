export interface User {
  id: number;
  email: string;
  username: string;
  bio: string;
  profilePhoto: any;
  creationDate: string;
  rating: number;
  isBlocked: boolean;
  isDeleted: boolean;
  roles: string[];
}
