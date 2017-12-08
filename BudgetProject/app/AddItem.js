import React from 'react';
import {
    View,
    Text,
    TextInput,
    Button,
} from 'react-native';

import ProductOperations from "./database/ProductOperations";

export default class AddItemScreen extends React.Component{
    constructor(props){
        super(props);

        this.productsOp= new ProductOperations();

        this.state = {
            id: 0,
            name: "",
            price: 0.0,
            supermarket: "",
            brand: ""
        };

    }

    add() {
        var item = this.state;
        this.productsOp.save(item.name, item.price, item.supermarket, item.brand);
        this.props.navigation.navigate("Home");
        //To do : navigate back and refresh the main page
    }

    render() {
        return (
            <View>
                <Text>Name: </Text>
                <TextInput onChangeText={(name) => this.setState({name})} value={this.state.name}/>
                <Text>Price: </Text>
                <TextInput keyboardType='numeric' onChangeText={(price) => this.setState({price})}
                           value={this.state.price.toString()}/>
                <Text>Supermarket: </Text>
                <TextInput onChangeText={(supermarket) => this.setState({supermarket})} value={this.state.supermarket}/>
                <Text>Brand: </Text>
                <TextInput onChangeText={(brand) => this.setState({brand})} value={this.state.brand}/>
                <Button title="Add" onPress={() => this.add()}/>
            </View>
        );
    }

}