import { Category } from './category';

export class Service {
  // id: number;
  // name: string;
  // owner_name: string;
  // localization: string;
  // raiting: number;
  // num: number;
  // cat: Category;

  constructor(
    public id: number,
    public name: string,
    public owner_name: string,
    public localization: string,
    public raiting: number,
    public num: number,
    public cat: Category
  ) {}
}
