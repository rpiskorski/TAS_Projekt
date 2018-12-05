import { User } from './user';

export class Comment {

  constructor(
      public id: number,
      public rating: number,
      public comment: string,
      public timestamp: number,
      public user: User
  ) { }
}
