import {AsyncStorage} from 'react-native'

export default class StorageHelper {

    constructor() {
    }

    async initArray() {
        try {
            let response = await AsyncStorage.getItem('productsList');
            if (response === null) {
                return;
            }

            let parsed = JSON.parse(response);
            var elem;
            global.products = [];
            for (var i = 0; i < parsed.length; i++) {
                elem = await AsyncStorage.getItem(JSON.stringify(parsed[i].toString()));
                if (elem === null) {
                    console.error("Can't retrieve all items");
                }
                global.products.push(JSON.parse(elem));
            }
        }
        catch (error) {
            console.error(error);
        }
    }

    /**
     * Used for add and delete pproduct
     * @returns {Array} of ids
     */
    getIds() {
        var id_array = [];

        global.products.forEach((elem) => {
            id_array.push(elem.id.toString());
        });
        return id_array;
    }

    async addItem(item) {
        try {
            //console.error({"item": item, "idList": this.getIds()});
            await  AsyncStorage.setItem(JSON.stringify(item.id.toString()), JSON.stringify(item));
            await AsyncStorage.setItem("productsList", JSON.stringify(this.getIds()));
        } catch (err) {
            console.error(err);
        }
    }

    async deleteItem(id) {
        try {
            await AsyncStorage.removeItem(JSON.stringify(id));
            await AsyncStorage.setItem("productsList", JSON.stringify(this.getIds()));
        } catch (err) {
            console.log(err);
        }
    }
}