import React from 'react';
import {
    View,
    TextInput,
    Button,
    Linking,
    Text,
    Picker,
    Alert
} from 'react-native';
import StorageHelper from "./storage/StorageHelper";

export default class SeeItemScreen extends React.Component {

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

        if (this.props.navigation.state.params.item != undefined) {
            var toEdit = this.props.navigation.state.params.item;
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
        this.storageHelper.addItem(item);
        this.props.navigation.state.params.refreshFunction();
        this.props.navigation.goBack();

    }

    askDelete(){
        Alert.alert("Are you sure?", "", [{
            text: "No", onPress: () => {
            }, style: 'cancel'
        }, {text: "yes", onPress: () => this.delete()}], {cancelable: true});
    }

    delete() {

        var item = this.state;
        for (var i = 0; i < global.products.length; i++) {
            if (global.products[i].id === item.id) {
                global.products.splice(i, 1);
            }
        }
        this.storageHelper.deleteItem(this.state.id.toString());
        this.props.navigation.state.params.refreshFunction();
        this.props.navigation.goBack();
    }

    share() {
        Linking.openURL("mailto:bianca_cioata@yahoo.com?subject=BudgetReactAppMail&body=" + JSON.stringify(this.state));
    }

    render() {
        return (
            <View style={{justifyContent: 'space-between', flex: 1, padding: 10}}>
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
                <Button title="delete" color='purple' onPress={() => this.askDelete()}/>
                <Button title="share" color='red' onPress={() => this.share()}/>
            </View>
        );
    }

}
