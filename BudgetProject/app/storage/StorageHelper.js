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

            let parsed = await JSON.parse(response);
            var elem;
            alert(parsed+" response "+response);
            for (var i = 0; i < parsed.length; i++) {
                elem = parsed[i];
                alert(elem.name);
                if (elem === null) {
                    console.error("Can't retrieve all items");
                }
                global.products.push(elem);
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
            id_array.push(elem.id);
        });
        return id_array;
    }

    async addItem(item) {
        try {
            await  AsyncStorage.setItem(JSON.stringify(item.id), JSON.stringify(item));
            await AsyncStorage.setItem("productsList", JSON.stringify(this.getIds()));
        } catch (err) {
            console.log(err);
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