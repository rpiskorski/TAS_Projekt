import { InMemoryDbService } from 'angular-in-memory-web-api';

export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    const products = [
      { id: 1, name: 'T-Shirt XXL', manufacturerName: 'ABC', avgRating: 4.3, numOfVotes: 54, category: 1 },
      { id: 2, name: 'Car DEF', manufacturerName: 'EFG', avgRating: 5, numOfVotes: 1, category: 2 },
      { id: 3, name: 'Smartphone 550', manufacturerName: 'HIJ', avgRating: 0, numOfVotes: 0, category: 3 },
      { id: 4, name: 'CAR ZML', manufacturerName: 'EFG', avgRating: 2.5, numOfVotes: 123, category: 2 },
    ];
    return {products};
  }

}
