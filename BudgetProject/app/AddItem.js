import React from 'react';
import {
    View,
    TextInput,
    Button,
    Text,
    Picker,
    Alert
} from 'react-native';
import StorageHelper from "./storage/StorageHelper";

export default class AddItemScreen extends React.Component {

    constructor(props) {
        super(props);
        this.storageHelper = new StorageHelper();
        this.state = {
            id: 0,
            name: "",
            price: 0.0,
            supermarket: "",
            brand: ""
        };
    }

    ok() {
        //setting the id
        if (global.products.length > 0) {
            var id = global.products[global.products.length - 1].id + 1;
            this.state.id = id;
        }
        else {
            var id = 1;
        }
        this.state.id = id;

        //adding the element
        var elem = this.state;
        //verify if exists already
        var found = false;
        for (var i = 0; i < global.products.length; i++) {
            if (global.products[i].name === elem.name && elem.supermarket === global.products[i].supermarket) {
                found = true;
            }
        }
        if (found === false) {
            global.products.push(elem);
            this.storageHelper.addItem(elem);
            // console.error(this.props.navigation.params);

            this.props.navigation.state.params.refreshFunction();
            this.props.navigation.goBack();
        }
        else {
            Alert.alert("The product with the same name from the same supermarket already exists! Can't be added", "", [{
                text: "ok", onPress: () => {
                    this.props.navigation.state.params.refreshFunction();
                    this.props.navigation.goBack();
                }
            }], {cancelable: true});
        }

    }

    render() {
        return (
            <View style={{flex: 1, padding: 10, justifyContent: 'space-between'}}>
                <Text>Name:</Text>
                <TextInput onChangeText={(name) => this.setState({name})} value={this.state.name}/>
                <Text>Price:</Text>
                <TextInput keyboardType='numeric' onChangeText={(price) => this.setState({price})}
                           value={this.state.price.toString()}/>
                <Text>Supermarket:</Text>
                <Picker
                    selectedValue={this.state.supermarket}
                    onValueChange={(itemValue, itemIndex) => this.setState({supermarket: itemValue})}>
                    {global.supermarkets.map((t, i) => {
                        return <Picker.Item label={t.name} value={t.name} key={t.name}/>
                    })}
                </Picker>
                <Text>Brand:</Text>
                <TextInput onChangeText={(brand) => this.setState({brand})} value={this.state.brand}/>

                <Button title="ok" onPress={() => this.ok()}/>
            </View>
        );
    }

}