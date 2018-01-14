export class Product {
    constructor(name, brand, price, supermarket) {
        this.id = null;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.supermarket = supermarket;
    }

    setId(id){
        this.id = id;
    }
}