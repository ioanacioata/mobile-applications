import {productsRef} from './corefirebase'
import {Product} from "../model/Product";

export default class ProductOperations {
    constructor() {
    }

    add(newProduct) {
        console.log("In add : ", newProduct);

        //set the default value from the spinner
        if (newProduct.supermarket === "") {
            newProduct.supermarket = global.supermarkets[0].name;
        }

        var newobj = productsRef.push();
        var key = newobj.key;
        newProduct.setId(key);
        console.log(key);
        newobj.set({
            id: key,
            name: newProduct.name,
            brand: newProduct.brand,
            price: newProduct.price,
            supermarket: newProduct.supermarket
        })
    }

    update(newProduct) {
        //set the default value from the spinner
        if (newProduct.supermarket === "") {
            newProduct.supermarket = global.supermarkets[0].name;
        }
        console.log("In update : ", newProduct);
        productsRef.child(newProduct.id).update({
            id: newProduct.id,
            name: newProduct.name,
            brand: newProduct.brand,
            price: newProduct.price,
            supermarket: newProduct.supermarket
        });
    }


    delete(id) {
        console.log("In delete : ", id);
        productsRef.child(id).remove();
    }

    getAll() {
        console.log("In get all : ")
        let items = [];
        productsRef.on('value', (snapshot) => {
            snapshot.forEach((child) => {
               // console.log("item: ", child.val());
                items.push(child.val());
            });
        });
        return items;
    }
}


