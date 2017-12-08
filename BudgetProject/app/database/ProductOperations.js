import {productsRef} from './corefirebase'

export default class ProductOperations{
    constructor() {
        var newobj = productsRef.push();
        var key = newobj.key;
        console.log(key);
        newobj.set({
            cocnut: "csf"
        });
    }
}


