import React from 'react';
import {
    View,
    TextInput,
    Button,
    Linking
} from 'react-native';

export default class SeeItemScreen extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            id: 0,
            name: "",
            price: 0.0,
            supermarket: "",
            brand: ""
        };

        if (this.props.navigation.state.params.id != undefined) {
            var toEdit = this.props.navigation.state.params;
            this.state.id = toEdit.id;
            this.state.name = toEdit.name;
            this.state.price = toEdit.price;
            this.state.supermarket = toEdit.supermarket;
            this.state.brand = toEdit.brand;
        }
    }

    ok() {
        var item = this.state;
        for (var i = 0; i < global.products.length; i++) {
            if (global.products[i].id === item.id) {
                global.products[i] = item;
            }
        }

        this.props.navigation.navigate("Home");
        //To do : navigate back and refresh the main page
    }

    share() {
        Linking.openURL("mailto:bianca_cioata@yahoo.com?subject=BudgetReactAppMail&body=" + JSON.stringify(this.state));
    }

    render() {
        return (
            <View>
                <TextInput onChangeText={(name) => this.setState({name})} value={this.state.name}/>
                <TextInput keyboardType='numeric' onChangeText={(price) => this.setState({price})}
                           value={this.state.price.toString()}/>
                <TextInput onChangeText={(supermarket) => this.setState({supermarket})} value={this.state.supermarket}/>
                <TextInput onChangeText={(brand) => this.setState({brand})} value={this.state.brand}/>
                <Button title="ok" onPress={() => this.ok()}/>
                <Button title="share" color='purple' onPress={() => this.share()}/>
            </View>
        );
    }

}