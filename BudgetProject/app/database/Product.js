export default class Product {
    id: string;
    name: string;
    price: number;
    supermarket: string;
    brand: string;


    constructor(id: string, name: string, price: number, supermarket: string, brand: string) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.supermarket = supermarket;
        this.brand = brand;
    }
}