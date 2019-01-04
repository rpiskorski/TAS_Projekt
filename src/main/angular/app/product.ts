import { Category } from './category';

export class Product {
  // id: number;
  // name: string;
  // manufacturerName: string;
  // rating: number;
  // numOfVotes: number;
  // category: Category;

  // constructor(
  //   id: number,
  //   name: string,
  //   manufacturerName: string,
  //   rating: number,
  //   numOfVotes: number,
  //   category: Category
  // ) {}

  // id: number;
  // name: string;
  // manufacturer_name: string;
  // raiting: number;
  // num: number;
  // cat: Category;

  constructor(
    public id: number,
    public name: string,
    public manufacturer_name: string,
    public raiting: number,
    public num: number,
    public cat: Category
  ) {}
}
