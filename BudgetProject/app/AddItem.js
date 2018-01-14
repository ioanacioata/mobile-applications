import React from 'react';
import {
    View,
    TextInput,
    Button,
    Text,
    Picker,
    Alert
} from 'react-native';
import ProductOperations from "./database/ProductOperations";
import {Product} from "./model/Product";

export default class AddItemScreen extends React.Component {

    constructor(props) {
        super(props);
        this.productOperations = new ProductOperations();
        this.state = {
            id: 0,
            name: "",
            price: 0.0,
            supermarket: "",
            brand: ""
        };
    }

    ok() {
        //adding the element
        var elem = this.state;
        var product = new Product(this.state.name, this.state.brand, this.state.price, this.state.supermarket);
        //verify if exists already
        var found = false;
        var products = this.productOperations.getAll();
        for (var i = 0; i < products.length; i++) {
            if (products[i].name === elem.name && elem.supermarket === products[i].supermarket) {
                found = true;
            }
        }
        if (found === false) {
            this.productOperations.add(product);

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