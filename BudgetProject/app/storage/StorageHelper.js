import {AsyncStorage} from 'react-native'

export default class StorageHelper {

    constructor() {
    }

    async initArray() {
        try {
            let response = await AsyncStorage.getItem('ITEM_ID_LIST');
            if (response === null) {
                return;
            }
            var elem;
            let parsed = await JSON.parse(response);

            // console.error(parsed+" response "+response);
            for (var i = 0; i < parsed.length; i++) {
                elem = await AsyncStorage.getItem(JSON.stringify(parsed[i]));
                // console.error("elm " + elem + " parsed " + parsed[i]);
                if (elem === null) {
                    console.error("CANT RETRIEVE ALL ITEMS");
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
            id_array.push(elem.id);
        });
        return id_array;
    }

    async addItem(item) {
        try {
            await  AsyncStorage.setItem(JSON.stringify(item.id), JSON.stringify(item));
            await AsyncStorage.setItem("ITEM_ID_LIST", JSON.stringify(this.getIds()));
        } catch (err) {
            console.log(err);
        }
    }

    async deleteItem(id) {
        try {
            await AsyncStorage.removeItem(JSON.stringify(id));
            await AsyncStorage.setItem("ITEM_ID_LIST", JSON.stringify(this.getIds()));
        } catch (err) {
            console.log(err);
        }
    }


}