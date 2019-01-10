export class ProductUser {
  constructor(
    public id: number,
    public comment: string,
    public rating: number,
    public timestamp: number,
    public name: string
  ) {}
}
