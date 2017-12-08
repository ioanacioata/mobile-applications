import {productsRef} from './corefirebase'
import Product from "./Product";

export default class ProductOperations{
    constructor() {
    }

    save(newName, newPrice, newSupermarket, newBrand){
        var newProduct = productsRef.push();
        var key = newProduct.key;
        console.log("adding a new product with key "+key);
        newProduct.set({
            name: newName,
            price: newPrice,
            supermarket: newSupermarket,
            brand: newBrand
        });
    }

    getAll(){
        productsRef.on('value',(data) =>{
            //get children as array
            products =[];
            data.forEach((child)=>{
                products.push({
                    id:child.key,
                    name: child.val().name,
                    price: child.val().price,
                    supermarket: child.val().supermarket,
                    brand: child.val().brand,
                });
            })
        });
    }



    

}


